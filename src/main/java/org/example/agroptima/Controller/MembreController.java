package org.example.agroptima.Controller;

import org.example.agroptima.Modele.Membre;
import org.example.agroptima.Repository.MembreRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;

@RestController
@RequestMapping // On enlève le /v1 global
public class MembreController {

    private final MembreRepository repo;

    public MembreController(MembreRepository repo) {
        this.repo = repo;
    }

    // URL : /collectivites/{id}/membres
    @PostMapping("/collectivites/{id}/membres")
    public ResponseEntity<String> admettre(@PathVariable int id, @RequestBody Membre m, @RequestParam int compteId) throws SQLException {
        if (!repo.parrainEstValide(m.getParrainId())) {
            return ResponseEntity.status(400).body("Parrain invalide (> 90 jours requis).");
        }

        m.setIdCollectivite(id);
        repo.admettre(m, compteId);
        return ResponseEntity.status(201).body("Membre admis.");
    }

    // URL : /membres/{id}/transfert
    @PatchMapping("/membres/{id}/transfert")
    public ResponseEntity<String> transferer(@PathVariable int id, @RequestBody Map<String, Integer> body) throws SQLException {
        int nouvelleCollId = body.get("nouvelleCollectiviteId");
        repo.transferer(id, nouvelleCollId);
        return ResponseEntity.ok("Transfert réussi.");
    }

    // URL : /membres/{id}
    @DeleteMapping("/membres/{id}")
    public ResponseEntity<String> demissionner(@PathVariable int id) throws SQLException {
        repo.demissionner(id);
        return ResponseEntity.ok("Démission enregistrée.");
    }
}