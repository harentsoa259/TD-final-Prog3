package com.Prog3.agroptima.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CashAccountResponse extends FinancialAccountResponse {
    private String type = "CASH";
}