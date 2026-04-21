package org.example.agroptima.Controller;

import org.example.agroptima.Repository.TresorerieRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.Map;

import org.example.agroptima.Repository.TresorerieRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
public class TresorerieController {
    private final TresorerieRepository repo;

    public TresorerieController(TresorerieRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/collectivites/{id}/comptes")
    public ResponseEntity<String> creerCompte(@PathVariable int id, @RequestBody Map<String, String> body) throws SQLException {
        String type = body.get("type"); // CAISSE, BANQUE, MOBILE_MONEY
        String numero = body.get("numero");
        String service = body.get("service");

        if ("CAISSE".equals(type) && repo.existeCaisse(id)) {
            return ResponseEntity.badRequest().body("Erreur : Une seule CAISSE autorisée par collectivité.");
        }

        if ("BANQUE".equals(type) && (numero == null || !numero.matches("\\d{23}"))) {
            return ResponseEntity.badRequest().body("Erreur : Le RIB doit contenir exactement 23 chiffres.");
        }

        if ("MOBILE_MONEY".equals(type) && (service == null || service.isEmpty())) {
            return ResponseEntity.badRequest().body("Erreur : Un service (Mvola, Orange, Airtel) est requis pour MOBILE_MONEY.");
        }

        repo.creerCompte(id, type, service, numero);
        return ResponseEntity.status(201).body("Compte créé avec succès.");
    }

    @PostMapping("/cotisations")
    public ResponseEntity<String> enregistrerCotisation(@RequestBody Map<String, Object> body) throws SQLException {
        repo.enregistrerCotisation(
                (int) body.get("membreId"),
                (int) body.get("compteId"),
                Double.parseDouble(body.get("montant").toString()),
                (String) body.get("date"),
                (String) body.get("type"),
                (String) body.get("modePaiement")
        );
        return ResponseEntity.ok("Cotisation enregistrée et solde mis à jour.");
    }

    @GetMapping("/collectivites/{id}/tresorerie")
    public ResponseEntity<Map<String, Object>> voirTresorerie(@PathVariable int id) throws SQLException {
        List<Map<String, Object>> comptes = repo.getSituationParCompte(id);
        double total = comptes.stream().mapToDouble(c -> (double) c.get("solde")).sum();

        return ResponseEntity.ok(Map.of(
                "comptes", comptes,
                "soldeGlobalMGA", total
        ));
    }
}