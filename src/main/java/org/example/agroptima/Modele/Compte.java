package org.example.agroptima.Modele;

import java.math.BigDecimal;

public class Compte {
    private Integer id;
    private Integer idCollectivite;
    private CompteType typeCompte;
    private String nomTitulaire;
    private String banqueNom;
    private String rib;
    private String serviceMm;
    private String numeroTel;
    private BigDecimal soldeMga;

    public String getRib() { return rib; }
    public void setRib(String rib) { this.rib = rib; }
    public BigDecimal getSoldeMga() { return soldeMga; }
    public void setSoldeMga(BigDecimal soldeMga) { this.soldeMga = soldeMga; }
    public CompteType getTypeCompte() { return typeCompte; }
    public void setTypeCompte(CompteType typeCompte) { this.typeCompte = typeCompte; }
}