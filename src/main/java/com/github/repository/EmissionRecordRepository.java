package com.github.repository;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.model.EmissionRecord;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.List;

@Repository
public class EmissionRecordRepository {

    private List<EmissionRecord> data;

    @PostConstruct
    public void init() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            InputStream is = getClass().getResourceAsStream("/data/emission-records.json");
            data = mapper.readValue(is, new TypeReference<List<EmissionRecord>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to load emission-records.json", e);
        }
    }

    public List<EmissionRecord> getAll() {
        return data;
    }

    public EmissionRecord getByActivityId(Long activityId) {
        return data.stream().filter(r -> r.getActivityId().equals(activityId)).findFirst().orElse(null);
    }
}

