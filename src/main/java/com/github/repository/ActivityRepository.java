package com.github.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.model.Activity;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.List;

@Repository
public class ActivityRepository {

    private List<Activity> data;

    @PostConstruct
    public void init() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            InputStream is = getClass().getResourceAsStream("/data/activities.json");
            data = mapper.readValue(is, new TypeReference<List<Activity>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to load activities.json", e);
        }
    }

    public List<Activity> getAll() {
        return data;
    }

    public Activity getById(Long id) {
        return data.stream().filter(a -> a.getId().equals(id)).findFirst().orElse(null);
    }
}

