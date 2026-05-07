package org.example.agroptima.Modele.Controller;

import org.example.agroptima.Modele.Collectivity.CreateCollectivity;
import org.example.agroptima.Repository.CollectivityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.*;

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
}