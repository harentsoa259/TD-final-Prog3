package org.example.agroptima.Modele.Contribution;


public class MembershipFee extends CreateMembershipFee {
    private String id;
    private String status;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}