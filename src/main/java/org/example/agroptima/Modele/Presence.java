package org.example.agroptima.Modele;


public class Presence {
    private Integer id;
    private Integer idActivite;
    private Integer idMembre;
    private StatutPresence statut;
    private String motifAbsence;

    public Presence() {}

    public Presence(Integer id, Integer idActivite, Integer idMembre,
                    StatutPresence statut, String motifAbsence) {
        this.id = id;
        this.idActivite = idActivite;
        this.idMembre = idMembre;
        this.statut = statut;
        this.motifAbsence = motifAbsence;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getIdActivite() { return idActivite; }
    public void setIdActivite(Integer idActivite) { this.idActivite = idActivite; }

    public Integer getIdMembre() { return idMembre; }
    public void setIdMembre(Integer idMembre) { this.idMembre = idMembre; }

    public StatutPresence getStatut() { return statut; }
    public void setStatut(StatutPresence statut) { this.statut = statut; }

    public String getMotifAbsence() { return motifAbsence; }
    public void setMotifAbsence(String motifAbsence) { this.motifAbsence = motifAbsence; }
}