package com.github.controller;

import com.github.service.EmissionCalculatorService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/emissions")
@CrossOrigin(origins = "*")
public class EmissionController {

    private final EmissionCalculatorService emissionService;

    public EmissionController(EmissionCalculatorService emissionService) {
        this.emissionService = emissionService;
    }

    @GetMapping("/projects/total")
    public List<Map<String, Object>> getTotalByProject() {
        return emissionService.getTotalEmissionsByProject();
    }

    @GetMapping("/by-category")
    public List<Map<String, Object>> getAllByCategory() {
        return emissionService.getEmissionsByCategoryAllProjects();
    }

    @GetMapping("/time-series")
    public List<Map<String, Object>> getTimeSeries() {
        return emissionService.getEmissionsOverTime();
    }

    @GetMapping("/project/{projectId}/by-category")
    public List<Map<String, Object>> getCategoryByProject(@PathVariable Long projectId) {
        return emissionService.getEmissionsByCategoryForProject(projectId);
    }
}

