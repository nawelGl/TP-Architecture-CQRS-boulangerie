package fr.upec.episen.grid.synchro_worker;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import fr.upec.episen.grid.synchro_worker.model.CommandeCreeeEvent;
import java.util.List;

@Service
public class CommandeSyncConsumer {

    private final JdbcTemplate jdbcWrite;
    private final JdbcTemplate jdbcRead;

    public CommandeSyncConsumer(@Qualifier("jdbcWrite") JdbcTemplate jdbcWrite,
            @Qualifier("jdbcRead") JdbcTemplate jdbcRead) {
        this.jdbcWrite = jdbcWrite;
        this.jdbcRead = jdbcRead;
    }

    @KafkaListener(topics = "flux_commandes", groupId = "boulangerie-sync-group")
    public void consume(CommandeCreeeEvent event) {
        System.out.println("Réception commande Kafka : " + event.idCommande());

        try {
            List<Integer> resultats = jdbcRead.queryForList(
                    "SELECT stock_actuel FROM vue_stock_boulangerie WHERE type_pain = ?",
                    Integer.class, event.typePain());

            Integer stockActuel = resultats.isEmpty() ? 0 : resultats.get(0);

            if (stockActuel >= event.quantite()) {
                jdbcRead.update("INSERT INTO mouvements_stock (type_pain, quantite_modifiee) VALUES (?, ?)",
                        event.typePain(), -event.quantite());

                jdbcWrite.update("UPDATE historique_commandes SET statut = 'VALIDEE' WHERE id = ?", event.idCommande());

                System.out.println("Commande VALIDÉE pour : " + event.idCommande());
            } else {
                jdbcWrite.update("UPDATE historique_commandes SET statut = 'REFUSEE_STOCK_INSUFFISANT' WHERE id = ?",
                        event.idCommande());

                System.out.println("Commande REFUSÉE (Stock insuffisant) : " + event.idCommande());
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la synchro : " + e.getMessage());
            e.printStackTrace();
        }
    }
}