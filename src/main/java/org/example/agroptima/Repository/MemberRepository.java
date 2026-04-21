package org.example.agroptima.Repository;

import org.example.agroptima.Modele.CreateMemberDTO;
import org.example.agroptima.Modele.Member;
import org.example.agroptima.Modele.MemberOccupation;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberRepository {

    private final DataSource dataSource;

    public MemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Member save(CreateMemberDTO dto) throws SQLException {
        // Changement : On ne met plus de 'RETURNING id' ici, on utilise getGeneratedKeys
        String sql = "INSERT INTO membre (first_name, last_name, birth_date, gender, address, profession, phone, email, occupation, collectivite_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection()) {
            // RETURN_GENERATED_KEYS permet de récupérer l'ID généré par SERIAL
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, dto.getFirstName());
                stmt.setString(2, dto.getLastName());
                stmt.setDate(3, Date.valueOf(dto.getBirthDate()));
                stmt.setString(4, dto.getGender());
                stmt.setString(5, dto.getAddress());
                stmt.setString(6, dto.getProfession());
                stmt.setInt(7, dto.getPhoneNumber());
                stmt.setString(8, dto.getEmail());
                stmt.setString(9, dto.getOccupation() != null ? dto.getOccupation().toString() : null);

                if (dto.getCollectivityIdentifier() != null && !dto.getCollectivityIdentifier().isEmpty()) {
                    stmt.setInt(10, Integer.parseInt(dto.getCollectivityIdentifier()));
                } else {
                    stmt.setNull(10, Types.INTEGER);
                }

                stmt.executeUpdate();

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int newId = rs.getInt(1); // On récupère l'ID numérique
                        if (dto.getReferees() != null && !dto.getReferees().isEmpty()) {
                            saveReferees(conn, newId, dto.getReferees());
                        }
                        return findById(String.valueOf(newId));
                    }
                }
            }
        }
        return null;
    }

    private void saveReferees(Connection conn, int memberId, List<String> refereeIds) throws SQLException {
        String sql = "INSERT INTO parrainage (membre_id, parrain_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (String refId : refereeIds) {
                stmt.setInt(1, memberId);
                stmt.setInt(2, Integer.parseInt(refId));
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    public Member findById(String id) throws SQLException {
        String sql = "SELECT * FROM membre WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(id)); // Conversion String vers int
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMember(rs);
                }
            }
        }
        return null;
    }

    private Member mapResultSetToMember(ResultSet rs) throws SQLException {
        Member m = new Member();
        m.setId(String.valueOf(rs.getInt("id")));
        m.setFirstName(rs.getString("first_name"));
        m.setLastName(rs.getString("last_name"));
        m.setBirthDate(rs.getDate("birth_date").toLocalDate());
        m.setGender(rs.getString("gender"));
        m.setAddress(rs.getString("address"));
        m.setProfession(rs.getString("profession"));
        m.setPhoneNumber(rs.getInt("phone"));
        m.setEmail(rs.getString("email"));

        String occValue = rs.getString("occupation");
        if (occValue != null) {
            m.setOccupation(MemberOccupation.valueOf(occValue));
        }

        return m;
    }
}