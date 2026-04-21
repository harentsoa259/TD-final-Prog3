package org.example.agroptima.Modele;


public class Mandat {
    private Integer id;
    private Integer idMembre;
    private Integer idCollectivite;
    private PostType poste;
    private Integer anneeCivile;

    public Integer getIdMembre() { return idMembre; }
    public void setIdMembre(Integer idMembre) { this.idMembre = idMembre; }
    public PostType getPoste() { return poste; }
    public void setPoste(PostType poste) { this.poste = poste; }
    public Integer getAnneeCivile() { return anneeCivile; }
    public void setAnneeCivile(Integer anneeCivile) { this.anneeCivile = anneeCivile; }
}