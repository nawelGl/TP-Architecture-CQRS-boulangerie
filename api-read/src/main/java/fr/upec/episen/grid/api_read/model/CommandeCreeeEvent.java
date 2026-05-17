package fr.upec.episen.grid.api_read.model;

import java.time.Instant;

public record CommandeCreeeEvent(
        String idCommande,
        String typePain,
        int quantite,
        Instant dateCreation) {
}