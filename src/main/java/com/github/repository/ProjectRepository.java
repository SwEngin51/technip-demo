package com.github.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.model.Project;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.List;

@Repository
public class ProjectRepository {

    private List<Project> data;

    @PostConstruct
    public void init() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            InputStream is = getClass().getResourceAsStream("/data/projects.json");
            data = mapper.readValue(is, new TypeReference<List<Project>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to load projects.json", e);
        }
    }

    public List<Project> getAll() {
        return data;
    }

    public Project getById(Long id) {
        return data.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
    }
}
