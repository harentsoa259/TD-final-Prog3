package org.example.agroptima.Modele;

import java.time.LocalDateTime;


import java.time.LocalDateTime;

public class Activite {
    private Integer id;
    private String nom;
    private LocalDateTime dateActivite;
    private String typeActivite;
    private boolean estObligatoire;
    private String cible;
    private Integer idCollectivite;

    public Activite() {}

    public Activite(Integer id, String nom, LocalDateTime dateActivite, String typeActivite,
                    boolean estObligatoire, String cible, Integer idCollectivite) {
        this.id = id;
        this.nom = nom;
        this.dateActivite = dateActivite;
        this.typeActivite = typeActivite;
        this.estObligatoire = estObligatoire;
        this.cible = cible;
        this.idCollectivite = idCollectivite;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public LocalDateTime getDateActivite() { return dateActivite; }
    public void setDateActivite(LocalDateTime dateActivite) { this.dateActivite = dateActivite; }

    public String getTypeActivite() { return typeActivite; }
    public void setTypeActivite(String typeActivite) { this.typeActivite = typeActivite; }

    public boolean isEstObligatoire() { return estObligatoire; }
    public void setEstObligatoire(boolean estObligatoire) { this.estObligatoire = estObligatoire; }

    public String getCible() { return cible; }
    public void setCible(String cible) { this.cible = cible; }

    public Integer getIdCollectivite() { return idCollectivite; }
    public void setIdCollectivite(Integer idCollectivite) { this.idCollectivite = idCollectivite; }
}