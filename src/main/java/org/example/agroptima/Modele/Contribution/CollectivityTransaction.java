package org.example.agroptima.Modele.Contribution;

import org.example.agroptima.Modele.Member.Member;
import java.time.LocalDate;

public class CollectivityTransaction {
    private String id;
    private LocalDate creationDate;
    private double amount;
    private String paymentMode;
    private Member memberDebited;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public LocalDate getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDate creationDate) { this.creationDate = creationDate; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }

    public Member getMemberDebited() { return memberDebited; }
    public void setMemberDebited(Member memberDebited) { this.memberDebited = memberDebited; }
}