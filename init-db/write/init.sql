CREATE TABLE historique_commandes (
    id VARCHAR(50) PRIMARY KEY,
    type_pain VARCHAR(50),
    quantite INT,
    statut VARCHAR(50), -- EN_ATTENTE, VALIDEE, REFUSEE
    date_creation TIMESTAMP
);