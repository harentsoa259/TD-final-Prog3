package com.Prog3.agroptima.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyRecurrenceRule {
    private Integer weekOrdinal; 
    private String dayOfWeek; 
}