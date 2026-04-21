package org.example.agroptima.Controller;

import org.example.agroptima.Modele.CreateMemberDTO;
import org.example.agroptima.Modele.Member;
import org.example.agroptima.Repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @PostMapping
    public ResponseEntity<List<Member>> createMembers(@RequestBody List<CreateMemberDTO> memberDTOs) {
        List<Member> createdMembers = new ArrayList<>();

        try {
            for (CreateMemberDTO dto : memberDTOs) {
                if (!dto.isRegistrationFeePaid() || !dto.isMembershipDuesPaid()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }

                
                Member savedMember = memberRepository.save(dto);
                if (savedMember != null) {
                    createdMembers.add(savedMember);
                }
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMembers);

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}