package com.Prog3.agroptima.entity.dto;

import com.Prog3.agroptima.entity.enums.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectivityTransactionResponse {
    private String id;
    private LocalDate creationDate;
    private Double amount;
    private PaymentMode paymentMode;
    private FinancialAccountResponse accountCredited;
    private MemberResponse memberDebited;
}