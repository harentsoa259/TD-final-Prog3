package org.example.agroptima.Repository;

import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.*;


import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TresorerieRepository {
    private final DataSource dataSource;

    public TresorerieRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // 1. Vérifier si une collectivité a déjà une caisse
    public boolean existeCaisse(int collId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM compte WHERE id_collectivite = ? AND type_compte = 'CAISSE'";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, collId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    // 2. Créer un compte
    public void creerCompte(int collId, String type, String service, String numero) throws SQLException {
        String sql = "INSERT INTO compte (id_collectivite, type_compte, details_service, numero_rib_tel, solde_mga) VALUES (?, ?, ?, ?, 0.0)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, collId);
            stmt.setString(2, type);
            stmt.setString(3, service);
            stmt.setString(4, numero);
            stmt.executeUpdate();
        }
    }

    // 3. Enregistrer une cotisation et mettre à jour le solde (Transactionnel)
    public void enregistrerCotisation(int membreId, int compteId, double montant, String date, String type, String mode) throws SQLException {
        String sqlCotis = "INSERT INTO cotisation (id_membre, id_compte, montant, date_paiement, type_cotisation, mode_paiement) VALUES (?, ?, ?, ?::date, ?, ?)";
        String sqlUpdateSolde = "UPDATE compte SET solde_mga = solde_mga + ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement ps1 = conn.prepareStatement(sqlCotis)) {
                    ps1.setInt(1, membreId);
                    ps1.setInt(2, compteId);
                    ps1.setDouble(3, montant);
                    ps1.setString(4, date);
                    ps1.setString(5, type);
                    ps1.setString(6, mode);
                    ps1.executeUpdate();
                }
                try (PreparedStatement ps2 = conn.prepareStatement(sqlUpdateSolde)) {
                    ps2.setDouble(1, montant);
                    ps2.setInt(2, compteId);
                    ps2.executeUpdate();
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    // 4. Obtenir la situation de trésorerie
    public List<Map<String, Object>> getSituationParCompte(int collId) throws SQLException {
        String sql = "SELECT type_compte, details_service, solde_mga FROM compte WHERE id_collectivite = ?";
        List<Map<String, Object>> result = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, collId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(Map.of(
                            "type", rs.getString("type_compte"),
                            "details", rs.getString("details_service") != null ? rs.getString("details_service") : "N/A",
                            "solde", rs.getDouble("solde_mga")
                    ));
                }
            }
        }
        return result;
    }
}