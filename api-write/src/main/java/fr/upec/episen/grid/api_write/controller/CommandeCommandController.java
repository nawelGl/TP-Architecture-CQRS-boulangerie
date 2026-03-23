package fr.upec.episen.grid.api_write.controller;

import fr.upec.episen.grid.api_write.model.CommandeCreeeEvent; // Ton Record
import fr.upec.episen.grid.api_write.service.CommandeProducer; // Ton Service Kafka
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/commandes")
public class CommandeCommandController {

    private final CommandeProducer producer;
    private final JdbcTemplate jdbcTemplate; // Utilisation simple pour le TP

    public CommandeCommandController(CommandeProducer producer, JdbcTemplate jdbcTemplate) {
        this.producer = producer;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping
    @Transactional // Important : Si Kafka ou la DB crash, on annule tout
    public ResponseEntity<Map<String, String>> creerCommande(@RequestBody Map<String, Object> request) {
        String idCommande = UUID.randomUUID().toString();
        String typePain = request.get("typePain").toString();
        int quantite = Integer.parseInt(request.get("quantite").toString());

        // 1. Écriture immédiate en DB_WRITE (Source de vérité de l'ordre)
        jdbcTemplate.update(
                "INSERT INTO historique_commandes (id, type_pain, quantite, statut) VALUES (?, ?, ?, ?)",
                idCommande, typePain, quantite, "EN_ATTENTE");

        // 2. Envoi à Kafka pour traitement asynchrone
        CommandeCreeeEvent event = new CommandeCreeeEvent(idCommande, typePain, quantite, Instant.now());
        producer.envoyerCommande(event);

        return ResponseEntity.accepted().body(Map.of(
                "idCommande", idCommande,
                "statut", "EN_ATTENTE"));
    }
}