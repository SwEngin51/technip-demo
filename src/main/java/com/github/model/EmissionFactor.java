package com.github.model;

import lombok.Data;

@Data
public class EmissionFactor {

    private Long id;
    private String activityId; // e.g., TRAVEL, ENERGY_CONSUMPTION
    private Double factor;       // e.g., kg CO2 per unit
    private String unit;         // e.g., kgCO2/km
    private String description;
}
