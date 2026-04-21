package org.example.agroptima.Controller;

import org.example.agroptima.Modele.Collectivite;
import org.example.agroptima.Repository.CollectiviteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/collectivites") // Plus de /v1 ici
public class CollectiviteController {

    private final CollectiviteRepository repo;

    public CollectiviteController(CollectiviteRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public ResponseEntity<String> ouvrir(@RequestBody Collectivite c, @RequestParam List<Integer> membresFondateursIds) throws SQLException {
        if (!repo.verifierMembresFondateurs(membresFondateursIds)) {
            return ResponseEntity.status(403).body("Erreur : 10 membres fondateurs avec 6 mois d'ancienneté requis.");
        }

        if (!c.isAutorisationFederation()) {
            return ResponseEntity.status(400).body("L'autorisation de la fédération est obligatoire.");
        }

        repo.save(c);
        return ResponseEntity.status(201).body("Collectivité créée.");
    }

    @GetMapping
    public ResponseEntity<List<Collectivite>> lister(@RequestParam(required = false) String ville,
                                                     @RequestParam(required = false) String specialite) throws SQLException {
        return ResponseEntity.ok(repo.findAll(ville, specialite));
    }
}