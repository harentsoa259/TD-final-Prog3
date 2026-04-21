package org.example.agroptima.Repository;

import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.*;

@Repository
public class MandatRepository {
    private final DataSource dataSource;

    public MandatRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Vérifie le nombre de mandats passés pour un membre à un poste précis
    public int compterAnciensMandats(int membreId, String poste) throws SQLException {
        String sql = "SELECT COUNT(*) FROM mandat WHERE id_membre = ? AND poste = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, membreId);
            stmt.setString(2, poste);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    // Vérifie si un poste est déjà occupé par un mandat en cours dans la collectivité
    public boolean estPosteOccupe(int collId, String poste) throws SQLException {
        String sql = "SELECT COUNT(*) FROM mandat WHERE id_collectivite = ? AND poste = ? AND date_fin > CURRENT_DATE";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, collId);
            stmt.setString(2, poste);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    // Vérifie si le membre est/a été président d'une collectivité (pour la Fédération)
    public boolean futPresidentCollectivite(int membreId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM mandat WHERE id_membre = ? AND poste = 'PRESIDENT' AND id_collectivite IS NOT NULL";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, membreId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    public void enregistrerMandat(int membreId, Integer collId, String poste, int dureeAns) throws SQLException {
        String sql = "INSERT INTO mandat (id_membre, id_collectivite, poste, date_debut, date_fin) " +
                "VALUES (?, ?, ?, CURRENT_DATE, CURRENT_DATE + (? || ' years')::interval)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, membreId);
            if (collId == null) stmt.setNull(2, Types.INTEGER); else stmt.setInt(2, collId);
            stmt.setString(3, poste);
            stmt.setInt(4, dureeAns);
            stmt.executeUpdate();
        }
    }
}