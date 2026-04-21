package org.example.agroptima.Modele;

import org.example.agroptima.Modele.GenreType;
import java.time.LocalDate;

public class Membre {
    private Integer id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private GenreType genre;
    private String adresse;
    private String metier;
    private String telephone;
    private String email;
    private LocalDate dateAdhesion;
    private Integer idCollectivite;
    private Integer parrainId;
    private boolean estActif;

    public Membre() {}


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public GenreType getGenre() {
        return genre;
    }

    public void setGenre(GenreType genre) {
        this.genre = genre;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getMetier() {
        return metier;
    }

    public void setMetier(String metier) {
        this.metier = metier;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateAdhesion() {
        return dateAdhesion;
    }

    public void setDateAdhesion(LocalDate dateAdhesion) {
        this.dateAdhesion = dateAdhesion;
    }

    public Integer getIdCollectivite() {
        return idCollectivite;
    }

    public void setIdCollectivite(Integer idCollectivite) {
        this.idCollectivite = idCollectivite;
    }

    public Integer getParrainId() {
        return parrainId;
    }

    public void setParrainId(Integer parrainId) {
        this.parrainId = parrainId;
    }

    public boolean isEstActif() {
        return estActif;
    }

    public void setEstActif(boolean estActif) {
        this.estActif = estActif;
    }
}