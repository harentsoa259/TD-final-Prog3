package org.example.agroptima.Repository;


import org.example.agroptima.Modele.Collectivity.Collectivity;
import org.example.agroptima.Modele.Collectivity.CollectivityStructure;
import org.example.agroptima.Modele.Collectivity.CreateCollectivity;
import org.springframework.stereotype.Repository;

import java.sql.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

@Repository
public class CollectivityRepository {
    private final Connection connection;

    public CollectivityRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(CreateCollectivity col) throws SQLException {
        List<String> memberIds = col.getMembers();
        if (memberIds == null || memberIds.size() < 10) {
            throw new SQLException("Ouverture refusée : Il faut au moins 10 membres.");
        }

        int ancientMembers = 0;
        for (String id : memberIds) {
            if (hasSeniority(id, 6)) {
                ancientMembers++;
            }
        }
        if (ancientMembers < 5) {
            throw new SQLException("Ouverture refusée : Il faut au moins 5 membres avec 6 mois d'ancienneté.");
        }

        String sql = "INSERT INTO collectivity (location, speciality, federation_approval, " +
                "president_id, vice_president_id, treasurer_id, secretary_id, creation_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, col.getLocation());
            pstmt.setString(2, col.getSpeciality());
            pstmt.setBoolean(3, col.isFederationApproval());
            pstmt.setInt(4, Integer.parseInt(col.getStructure().getPresident()));
            pstmt.setInt(5, Integer.parseInt(col.getStructure().getVicePresident()));
            pstmt.setInt(6, Integer.parseInt(col.getStructure().getTreasurer()));
            pstmt.setInt(7, Integer.parseInt(col.getStructure().getSecretary()));
            pstmt.setDate(8, Date.valueOf(LocalDate.now()));

            pstmt.executeUpdate();
        }
    }

    private boolean hasSeniority(String memberId, int months) throws SQLException {
        String sql = "SELECT membership_date FROM member WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(memberId));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Date joinDate = rs.getDate("membership_date");
                if (joinDate == null) return false;
                LocalDate ld = joinDate.toLocalDate();
                return ld.isBefore(LocalDate.now().minusMonths(months));
            }
        }
        return false;
    }


    public boolean isAlreadyIdentified(String id) throws SQLException {
        String sql = "SELECT name, number FROM collectivity WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name") != null || rs.getString("number") != null;
            }
        }
        return false;
    }

    public boolean existsByNameOrNumber(String name, String number) throws SQLException {
        String sql = "SELECT COUNT(*) FROM collectivity WHERE name = ? OR number = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, number);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public void updateIdentity(String id, String name, String number) throws SQLException {
        String sql = "UPDATE collectivity SET name = ?, number = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, number);
            pstmt.setInt(3, Integer.parseInt(id));
            pstmt.executeUpdate();
        }
    }

    public Collectivity findById(String id) throws SQLException {
        String sql = "SELECT * FROM collectivity WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Integer.parseInt(id));

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Collectivity c = new Collectivity();
                    c.setId(String.valueOf(rs.getInt("id")));
                    c.setName(rs.getString("name"));
                    c.setNumber(rs.getString("number"));
                    c.setLocation(rs.getString("location"));

                    c.setStructure(new CollectivityStructure());
                    c.setMembers(new java.util.ArrayList<>());


                    return c;
                }
            }
        }
        return null;
    }
}