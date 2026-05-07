package com.Prog3.agroptima.entity;

import com.Prog3.agroptima.entity.enums.Bank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {
    private String id;
    private Account account;
    private String holderName;
    private Bank bankName;
    private String bankCode;
    private String branchCode;
    private String accountNumber;
    private String ribKey;
}