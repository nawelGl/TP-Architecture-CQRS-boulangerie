# Architecture CQRS & Event-Sourcing : Gestion de Commandes en Boulangerie

Ce projet met en place une architecture distribuée basée sur le pattern **CQRS (Command Query Responsibility Segregation)** et l'**Event-Sourcing**, dans le cadre du module "Outils pour le GRID". 

Le cas métier modélise le système de commandes d'une boulangerie industrielle devant encaisser un fort pic de charge matinal tout en garantissant une haute disponibilité.

## 📌 Le Cas Métier (La Boulangerie)
Lors du pic matinal, la boulangerie reçoit massivement des commandes de restaurateurs. 
Pour ne pas saturer les bases de données et garantir un temps de réponse minimal :
1. **L'API d'écriture (API Write)** reçoit la commande (`POST`) et la publie instantanément dans un cluster Kafka. Elle rend la main au client immédiatement (`HTTP 202 Accepted`).
2. **Kafka** agit comme le registre central et immuable (Source de Vérité).
3. **Le Composant de Synchronisation (Worker)** dépile les commandes de Kafka de manière asynchrone. Il met à jour l'historique brut (DB Write) et recalcule les stocks dans une vue dé-normalisée (DB Read).
4. **L'API de lecture (API Read)** permet au boulanger de consulter son tableau de bord (`GET`) de manière ultra-rapide, en interrogeant uniquement la vue pré-calculée.

## 🏗️ Architecture et Flux

Voir les fichiers architecture-schema (png & md).