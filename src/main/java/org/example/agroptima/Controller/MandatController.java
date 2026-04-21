package org.example.agroptima.Controller;

import org.example.agroptima.Repository.MandatRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.Map;

@RestController
public class MandatController {
    private final MandatRepository repo;

    public MandatController(MandatRepository repo) {
        this.repo = repo;
    }

    // POST /collectivites/{id}/mandats
    @PostMapping("/collectivites/{id}/mandats")
    public ResponseEntity<String> assignerMandatCollectivite(@PathVariable int id, @RequestBody Map<String, Object> body) throws SQLException {
        int membreId = (int) body.get("membreId");
        String poste = (String) body.get("poste");

        // 1. Limite de 2 mandats par poste
        if (repo.compterAnciensMandats(membreId, poste) >= 2) {
            return ResponseEntity.status(403).body("Erreur : Ce membre a déjà effectué 2 mandats pour le poste de " + poste);
        }

        // 2. Unicité du poste (sauf JUNIOR/CONFIRME)
        if (!poste.equalsIgnoreCase("JUNIOR") && !poste.equalsIgnoreCase("CONFIRME")) {
            if (repo.estPosteOccupe(id, poste)) {
                return ResponseEntity.status(400).body("Erreur : Le poste de " + poste + " est déjà pourvu pour ce mandat.");
            }
        }

        repo.enregistrerMandat(membreId, id, poste, 1); // Durée 1 an
        return ResponseEntity.ok("Mandat de 1 an enregistré pour la collectivité.");
    }

    // POST /federation/mandats
    @PostMapping("/federation/mandats")
    public ResponseEntity<String> elireBureauFederation(@RequestBody Map<String, Object> body) throws SQLException {
        int membreId = (int) body.get("membreId");
        String poste = (String) body.get("poste");

        // 3. Condition Président Fédération
        if (poste.equalsIgnoreCase("PRESIDENT")) {
            if (!repo.futPresidentCollectivite(membreId)) {
                return ResponseEntity.status(403).body("Inéligibilité : Le président de la Fédération doit être ou avoir été président d'une collectivité.");
            }
        }

        repo.enregistrerMandat(membreId, null, poste, 2); // Durée 2 ans (id_collectivite = null pour la fédération)
        return ResponseEntity.ok("Élection à la Fédération enregistrée pour 2 ans.");
    }
}