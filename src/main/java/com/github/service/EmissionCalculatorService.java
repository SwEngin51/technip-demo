package com.github.service;


import com.github.model.Activity;
import com.github.model.EmissionRecord;
import com.github.model.Project;
import com.github.repository.ActivityRepository;
import com.github.repository.EmissionRecordRepository;
import com.github.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmissionCalculatorService {

    private final List<Activity> activities;
    private final List<Project> projects;
    private final Map<Long, EmissionRecord> emissionRecordMap;

    public EmissionCalculatorService(
            ActivityRepository activityRepository,
            ProjectRepository projectRepository,
            EmissionRecordRepository emissionRecordRepository) {
        this.activities = activityRepository.getAll();
        this.projects = projectRepository.getAll();
        this.emissionRecordMap = emissionRecordRepository.getAll().stream()
                .collect(Collectors.toMap(EmissionRecord::getActivityId, r -> r));
    }

    public List<Map<String, Object>> getTotalEmissionsByProject() {
        List<Map<String, Object>> result = new ArrayList<>();

        for (Project project : projects) {
            double total = activities.stream()
                    .filter(a -> a.getProjectId().equals(project.getId()))
                    .mapToDouble(a -> {
                        EmissionRecord record = emissionRecordMap.get(a.getId());
                        return record != null ? record.getCalculatedEmission() : 0;
                    })
                    .sum();

            Map<String, Object> map = new HashMap<>();
            map.put("projectId", project.getId());
            map.put("projectName", project.getName());
            map.put("totalEmissions", Math.round(total * 100.0) / 100.0);
            result.add(map);
        }

        return result;
    }

    public List<Map<String, Object>> getEmissionsByCategoryAllProjects() {
        return activities.stream()
                .collect(Collectors.groupingBy(
                        Activity::getType,
                        Collectors.summingDouble(a -> {
                            EmissionRecord record = emissionRecordMap.get(a.getId());
                            return record != null ? record.getCalculatedEmission() : 0;
                        })
                ))
                .entrySet().stream()
                .map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("category", e.getKey());
                    map.put("total", Math.round(e.getValue() * 100.0) / 100.0);
                    return map;
                })
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getEmissionsOverTime() {
        return activities.stream()
                .collect(Collectors.groupingBy(
                        Activity::getActivityDate,
                        TreeMap::new,
                        Collectors.summingDouble(a -> {
                            EmissionRecord record = emissionRecordMap.get(a.getId());
                            return record != null ? record.getCalculatedEmission() : 0;
                        })
                ))
                .entrySet().stream()
                .map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("date", e.getKey());
                    map.put("total", Math.round(e.getValue() * 100.0) / 100.0);
                    return map;
                })
                .collect(Collectors.toList());
    }
}

