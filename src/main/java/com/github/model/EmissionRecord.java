package com.github.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmissionRecord {

    private Long id;

    private Long activityId;

    private Double calculatedEmission;

    private String unit;

    private LocalDateTime calculatedAt;
}
