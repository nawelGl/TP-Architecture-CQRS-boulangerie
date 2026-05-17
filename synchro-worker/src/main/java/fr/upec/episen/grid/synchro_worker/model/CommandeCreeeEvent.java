package fr.upec.episen.grid.synchro_worker.model;

import java.time.Instant;

public record CommandeCreeeEvent(
        String idCommande,
        String typePain,
        int quantite,
        Instant dateCreation) {
}