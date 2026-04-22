package org.example.agroptima.Controller;

import org.example.agroptima.Modele.Contribution.CreateMemberPayment;
import org.example.agroptima.Modele.Contribution.CreateMembershipFee;
import org.example.agroptima.Modele.Contribution.MembershipFee;
import org.example.agroptima.Repository.ContributionRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ContributionController {

    private final ContributionRepository contributionRepository;
    private final Connection connection;

    public ContributionController(ContributionRepository contributionRepository, Connection connection) {
        this.contributionRepository = contributionRepository;
        this.connection = connection;
    }

    @PostMapping("/collectivities/{id}/membershipFees")
    public ResponseEntity<?> createMembershipFees(
            @PathVariable String id,
            @RequestBody List<CreateMembershipFee> fees) {
        try {
            int collectivityId = Integer.parseInt(id);
            for (CreateMembershipFee fee : fees) {
                if (fee.getAmount() <= 0) {
                    return ResponseEntity.badRequest().body("Le montant doit être supérieur à 0.");
                }
                contributionRepository.saveMembershipFee(collectivityId, fee);
            }
            return ResponseEntity.status(HttpStatus.OK).body(fees);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur SQL : " + e.getMessage());
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Format d'ID collectivité invalide.");
        }
    }

    @PostMapping("/members/{id}/payments")
    public ResponseEntity<?> createMemberPayments(
            @PathVariable String id,
            @RequestBody List<CreateMemberPayment> payments) {
        try {
            int memberId = Integer.parseInt(id);
            for (CreateMemberPayment payment : payments) {
                contributionRepository.processMemberPayment(memberId, payment);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(payments);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Échec de la transaction (vérifiez les IDs de frais ou de compte) : " + e.getMessage());
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Format d'ID membre invalide.");
        }
    }

    @GetMapping("/collectivities/{id}/transactions")
    public ResponseEntity<?> getTransactions(
            @PathVariable int id,
            @RequestParam String from,
            @RequestParam String to) {
        try {
            LocalDate startDate = LocalDate.parse(from);
            LocalDate endDate = LocalDate.parse(to);

            List<CreateMemberPayment> results = contributionRepository.getTransactionsByPeriod(id, startDate, endDate);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur: Format date attendu YYYY-MM-DD");
        }
    }

    @GetMapping("/collectivities/{id}/membershipFees")
    public ResponseEntity<?> getMembershipFees(@PathVariable String id) {
        try {
            int collectivityId = Integer.parseInt(id);

            List<MembershipFee> fees = contributionRepository.getMembershipFeesByCollectivity(collectivityId);

            if (fees.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Aucun frais de cotisation trouvé pour la collectivité n°" + id);
            }

            return ResponseEntity.ok(fees);

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("L'identifiant de la collectivité doit être un nombre.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Une erreur est survenue lors de la récupération des frais.");
        }
    }
}