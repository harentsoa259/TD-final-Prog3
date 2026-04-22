package org.example.agroptima.Modele.Controller;

import org.example.agroptima.Modele.Member.CreateMember;
import org.example.agroptima.Repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @PostMapping
    public ResponseEntity<?> createMembers(@RequestBody List<CreateMember> members) {
        try {
            for (CreateMember m : members) {
                if (!m.isRegistrationFeePaid() || !m.isMembershipDuesPaid()) {
                    return ResponseEntity.badRequest().body("Paiements (frais + cotisations) incomplets.");
                }
                memberRepository.save(m);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(members);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur interne : " + e.getMessage());
        }
    }
}