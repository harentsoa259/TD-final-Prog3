package com.Prog3.agroptima.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCollectivityStructure {
    private String president;
    private String vicePresident;
    private String treasurer;
    private String secretary;
}