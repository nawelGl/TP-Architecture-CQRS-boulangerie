-- 1. On garde une table physique pour les mouvements de stock
CREATE TABLE mouvements_stock (
    id SERIAL PRIMARY KEY,
    type_pain VARCHAR(50),
    quantite_modifiee INT, -- Négatif pour une vente, positif pour une fournée
    date_mouvement TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. On insère le stock initial
INSERT INTO mouvements_stock (type_pain, quantite_modifiee) VALUES ('baguette', 100), ('croissant', 50);

-- 3. LA VUE
-- Elle calcule le stock actuel en temps réel en sommant les mouvements
CREATE VIEW vue_stock_boulangerie AS
SELECT 
    type_pain,
    SUM(quantite_modifiee) AS stock_actuel
FROM mouvements_stock
GROUP BY type_pain;

-- 4. Table pour le polling (historique des commandes traitées)
CREATE TABLE historique_commandes (
    id VARCHAR(50) PRIMARY KEY,
    type_pain VARCHAR(50),
    quantite INT,
    statut VARCHAR(50)
);