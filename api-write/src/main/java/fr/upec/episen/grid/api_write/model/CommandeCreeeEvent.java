package fr.upec.episen.grid.api_write.model;

import java.time.Instant;

/**
 * Ce record représente l'événement métier qui transite dans Kafka.
 * Il contient TOUTES les infos nécessaires pour que le Worker
 * puisse mettre à jour les bases de données.
 */
public record CommandeCreeeEvent(
        String idCommande,
        String typePain,
        int quantite,
        Instant dateCreation) {
}