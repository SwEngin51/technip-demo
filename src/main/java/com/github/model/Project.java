package com.github.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Project {
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
}
