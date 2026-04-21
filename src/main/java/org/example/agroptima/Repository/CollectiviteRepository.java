package org.example.agroptima.Repository;

import org.example.agroptima.Modele.Collectivite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

import org.example.agroptima.Modele.Collectivite;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CollectiviteRepository {
    private final DataSource dataSource;

    public CollectiviteRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // POST /collectivites : Vérification des 10 membres (6 mois d'ancienneté)
    public boolean verifierMembresFondateurs(List<Integer> ids) throws SQLException {
        String sql = "SELECT COUNT(*) FROM membre WHERE id = ANY(?) AND date_adhesion <= CURRENT_DATE - INTERVAL '6 months'";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            Array array = conn.createArrayOf("INTEGER", ids.toArray());
            stmt.setArray(1, array);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) >= 10;
            }
        }
    }

    // POST /collectivites : Création
    public void save(Collectivite c) throws SQLException {
        String sql = "INSERT INTO collectivite (nom, numero, ville, specialite, autorisation_federation) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getNom());
            stmt.setString(2, c.getNumero());
            stmt.setString(3, c.getVille());
            stmt.setString(4, c.getSpecialite());
            stmt.setBoolean(5, c.isAutorisationFederation());
            stmt.executeUpdate();
        }
    }

    // GET /collectivites : Liste avec filtres
    public List<Collectivite> findAll(String ville, String specialite) throws SQLException {
        List<Collectivite> results = new ArrayList<>();
        String sql = "SELECT * FROM collectivite WHERE ville LIKE ? AND specialite LIKE ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + (ville == null ? "" : ville) + "%");
            stmt.setString(2, "%" + (specialite == null ? "" : specialite) + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Collectivite c = new Collectivite();
                    c.setId(rs.getInt("id"));
                    c.setNom(rs.getString("nom"));
                    c.setNumero(rs.getString("numero"));
                    c.setVille(rs.getString("ville"));
                    c.setSpecialite(rs.getString("specialite"));
                    results.add(c);
                }
            }
        }
        return results;
    }
}