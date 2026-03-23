package fr.upec.episen.grid.api_write.service;

import fr.upec.episen.grid.api_write.model.CommandeCreeeEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CommandeProducer {

    // KafkaTemplate est l'outil de Spring pour envoyer des messages
    private final KafkaTemplate<String, CommandeCreeeEvent> kafkaTemplate;

    // Le nom du topic doit être identique à celui que le Worker écoutera
    private static final String TOPIC = "flux_commandes";

    public CommandeProducer(KafkaTemplate<String, CommandeCreeeEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void envoyerCommande(CommandeCreeeEvent event) {
        /*
         * * On envoie l'événement avec une CLÉ (ici le type de pain).
         * Pourquoi ? Kafka garantit que tous les messages avec la même clé
         * atterrissent dans la même PARTITION et sont lus dans l'ordre.
         */
        kafkaTemplate.send(TOPIC, event.typePain(), event);

        System.out.println("Événement envoyé à Kafka : " + event.idCommande());
    }
}