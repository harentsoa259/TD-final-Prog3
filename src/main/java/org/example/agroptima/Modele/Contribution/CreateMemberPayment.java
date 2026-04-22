package org.example.agroptima.Modele.Contribution;

public class CreateMemberPayment {
    private int amount;
    private String membershipFeeIdentifier;
    private String accountCreditedIdentifier;
    private String paymentMode;
    public CreateMemberPayment() {

    }
    public CreateMemberPayment(int amount, String membershipFeeIdentifier, String accountCreditedIdentifier, String paymentMode) {
        this.amount = amount;
        this.membershipFeeIdentifier = membershipFeeIdentifier;
        this.accountCreditedIdentifier = accountCreditedIdentifier;
        this.paymentMode = paymentMode;
    }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }

    public String getMembershipFeeIdentifier() { return membershipFeeIdentifier; }
    public void setMembershipFeeIdentifier(String membershipFeeIdentifier) { this.membershipFeeIdentifier = membershipFeeIdentifier; }

    public String getAccountCreditedIdentifier() { return accountCreditedIdentifier; }
    public void setAccountCreditedIdentifier(String accountCreditedIdentifier) { this.accountCreditedIdentifier = accountCreditedIdentifier; }

    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }
}