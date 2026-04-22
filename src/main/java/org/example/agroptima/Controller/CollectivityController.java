package org.example.agroptima.Controller;

import org.example.agroptima.Modele.Collectivity.CreateCollectivity;
import org.example.agroptima.Repository.CollectivityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/collectivities")
public class CollectivityController {

    private final CollectivityRepository collectivityRepository;

    public CollectivityController(CollectivityRepository collectivityRepository) {
        this.collectivityRepository = collectivityRepository;
    }

    @PostMapping
    public ResponseEntity<?> createCollectivities(@RequestBody List<CreateCollectivity> collectivities) {
        try {
            for (CreateCollectivity col : collectivities) {
                if (!col.isFederationApproval()) {
                    return ResponseEntity.badRequest()
                            .body("Erreur 400 : Collectivité sans autorisation formelle d'ouverture de la part de la fédération.");
                }

                if (col.getStructure() == null || col.getStructure().getPresident() == null) {
                    return ResponseEntity.badRequest()
                            .body("Erreur 400 : Les postes spécifiques (Président, etc.) doivent être occupés.");
                }

                collectivityRepository.save(col);
            }
            return new ResponseEntity<>(collectivities, HttpStatus.CREATED);

        } catch (Exception e) {
            if (e.getMessage().contains("Ouverture refusée")) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @PutMapping("/{id}/informations")
    public ResponseEntity<?> identifyCollectivity(
            @PathVariable String id,
            @RequestBody java.util.Map<String, Object> body) {
        try {
            String name = (String) body.get("name");

            Object numberObj = body.get("number");
            if (name == null || numberObj == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Erreur 400 : Le nom et le numéro sont obligatoires.");
            }

            String number = String.valueOf(numberObj);

            if (collectivityRepository.isAlreadyIdentified(id)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Erreur 400 : Cette collectivité possède déjà une identité qui ne peut plus être changée.");
            }

            if (collectivityRepository.existsByNameOrNumber(name, number)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Erreur 400 : Ce nom ou ce numéro est déjà utilisé par une autre collectivité.");
            }

            collectivityRepository.updateIdentity(id, name, number);

            var updatedCollectivity = collectivityRepository.findById(id);
            if (updatedCollectivity == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Collectivité non trouvée.");
            }

            return ResponseEntity.ok(updatedCollectivity);

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur SQL : " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Données invalides.");
        }
    }

}