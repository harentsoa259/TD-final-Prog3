package org.example.agroptima.Repository;

import org.example.agroptima.Modele.Member.CreateMember;
import org.springframework.stereotype.Repository;

import java.sql.*;

import java.sql.*;


import java.sql.*;
import java.time.LocalDate;
import java.util.List;

@Repository
public class MemberRepository {
    private final Connection connection;

    public MemberRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(CreateMember member) throws SQLException {
        List<String> refereeIds = member.getReferees();

        if (refereeIds == null || refereeIds.size() < 2) {
            throw new IllegalArgumentException("Il faut au moins 2 parrains.");
        }

        int localCount = 0;
        int externalCount = 0;
        String targetCol = member.getCollectivityIdentifier();

        for (String refId : refereeIds) {
            String colOfReferee = getMemberCollectivity(refId);
            if (targetCol.equals(colOfReferee)) {
                localCount++;
            } else {
                externalCount++;
            }
        }

        if (localCount < externalCount) {
            throw new IllegalArgumentException("Le nombre de parrains locaux (" + localCount +
                    ") doit être supérieur ou égal aux parrains externes (" + externalCount + ").");
        }

        String sql = "INSERT INTO member (first_name, last_name, birth_date, gender, phone_number, occupation, " +
                "registration_fee_paid, membership_dues_paid, collectivity_id, membership_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, member.getFirstName());
            pstmt.setString(2, member.getLastName());
            pstmt.setDate(3, Date.valueOf(member.getBirthDate()));
            pstmt.setObject(4, member.getGender().name(), Types.OTHER);
            pstmt.setInt(5, member.getPhoneNumber());
            pstmt.setObject(6, member.getOccupation().name(), Types.OTHER);
            pstmt.setBoolean(7, member.isRegistrationFeePaid());
            pstmt.setBoolean(8, member.isMembershipDuesPaid());
            pstmt.setInt(9, Integer.parseInt(targetCol));
            pstmt.setDate(10, Date.valueOf(LocalDate.now())); // Date d'adhésion
            pstmt.executeUpdate();
        }
    }

    private String getMemberCollectivity(String memberId) throws SQLException {
        String sql = "SELECT collectivity_id FROM member WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(memberId));
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? String.valueOf(rs.getInt(1)) : null;
        }
    }
}