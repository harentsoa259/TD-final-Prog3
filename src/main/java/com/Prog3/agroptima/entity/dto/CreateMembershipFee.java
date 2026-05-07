package com.Prog3.agroptima.entity.dto;

import com.Prog3.agroptima.entity.enums.Frequency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMembershipFee {
    private LocalDate eligibleFrom;
    private Frequency frequency;
    private Double amount;
    private String label;
}