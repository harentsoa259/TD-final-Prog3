package org.example.agroptima.Modele;

import java.time.LocalDate;

public class Collectivite {
    private Integer id;
    private String nom;
    private String numero;
    private String ville;
    private String specialite;
    private LocalDate dateCreation;
    private boolean autorisationFederation;

    public Collectivite() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }
    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }
    public LocalDate getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDate dateCreation) { this.dateCreation = dateCreation; }
    public boolean isAutorisationFederation() { return autorisationFederation; }
    public void setAutorisationFederation(boolean autorisationFederation) { this.autorisationFederation = autorisationFederation; }
}