package com.github.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Activity {

    private Long id;
    private String name;
    private Long projectId;
    private String type;
    private LocalDate activityDate;
    private Double quantity;
    private String unit;

    private Project project;

}
