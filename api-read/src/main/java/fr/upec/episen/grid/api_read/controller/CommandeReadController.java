package fr.upec.episen.grid.api_read.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/consultation")
public class CommandeReadController {

    private final JdbcTemplate jdbcTemplate;

    public CommandeReadController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 1. Voir l'état des stocks (Tableau de bord du boulanger)
    @GetMapping("/stocks")
    public List<Map<String, Object>> getStocks() {
        return jdbcTemplate.queryForList("SELECT * FROM vue_stock_boulangerie");
    }

    // 2. POLLING : Vérifier si la commande a été validée ou refusée par le Worker
    @GetMapping("/statut/{id}")
    public ResponseEntity<Map<String, Object>> getStatutCommande(@PathVariable String id) {
        try {
            // Le Worker mettra à jour cette table dans db_read
            Map<String, Object> commande = jdbcTemplate.queryForMap(
                    "SELECT id, type_pain, quantite, statut FROM historique_commandes WHERE id = ?",
                    id);
            return ResponseEntity.ok(commande);
        } catch (EmptyResultDataAccessException e) {
            // Si la commande n'est pas encore arrivée dans db_read (latence)
            return ResponseEntity.notFound().build();
        }
    }
}