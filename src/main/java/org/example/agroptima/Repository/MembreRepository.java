package org.example.agroptima.Repository;

import org.example.agroptima.Modele.Membre;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.*;

@Repository
public class MembreRepository {
    private final DataSource dataSource;

    public MembreRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // POST .../membres : Vérifier parrain (90 jours)
    public boolean parrainEstValide(int parrainId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM membre WHERE id = ? AND date_adhesion <= CURRENT_DATE - INTERVAL '90 days'";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, parrainId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    // POST .../membres : Admission avec paiement (Transactionnelle)
    // Dans MembreRepository.java
    public void admettre(Membre m, int compteId) throws SQLException {
        // AJOUTE date_naissance dans le INSERT
        String sqlMembre = "INSERT INTO membre (nom, prenom, date_naissance, genre, id_collectivite, parrain_id, date_adhesion) VALUES (?, ?, ?, ?::genre_type, ?, ?, CURRENT_DATE)";
        String sqlPaiement = "INSERT INTO cotisation (id_membre, id_compte, montant, type_cotisation) VALUES (LASTVAL(), ?, 50000, 'ADHESION')";

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement st1 = conn.prepareStatement(sqlMembre)) {
                    st1.setString(1, m.getNom());
                    st1.setString(2, m.getPrenom());
                    // NE PAS OUBLIER CETTE LIGNE :
                    st1.setDate(3, java.sql.Date.valueOf(m.getDateNaissance()));
                    st1.setString(4, m.getGenre().name());
                    st1.setInt(5, m.getIdCollectivite());
                    st1.setInt(6, m.getParrainId());
                    st1.executeUpdate();
                }
                // ... reste du code (sqlPaiement)
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    // PATCH /membres/{id}/transfert
    public void transferer(int membreId, int nouvelleCollId) throws SQLException {
        String sql = "UPDATE membre SET id_collectivite = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nouvelleCollId);
            stmt.setInt(2, membreId);
            stmt.executeUpdate();
        }
    }

    // DELETE /membres/{id} (Suppression logique/Démission)
    public void demissionner(int membreId) throws SQLException {
        String sql = "UPDATE membre SET est_actif = false WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, membreId);
            stmt.executeUpdate();
        }
    }
}