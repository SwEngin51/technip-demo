package com.github.controller;

import com.github.service.EmissionCalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EmissionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmissionCalculatorService emissionService;

    @InjectMocks
    private EmissionController emissionController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(emissionController).build();
    }

    @Test
    void getTotalByProject_ShouldReturnProjectEmissions() throws Exception {
        // Prepare test data that matches CSV export format
        Map<String, Object> project1 = new HashMap<>();
        project1.put("projectId", "P1");
        project1.put("projectName", "Project 1");
        project1.put("totalEmissions", 100.0);

        Map<String, Object> project2 = new HashMap<>();
        project2.put("projectId", "P2");
        project2.put("projectName", "Project 2");
        project2.put("totalEmissions", 200.0);

        List<Map<String, Object>> mockData = Arrays.asList(project1, project2);

        // Set up mock service response
        when(emissionService.getTotalEmissionsByProject()).thenReturn(mockData);

        // Perform request and verify response
        mockMvc.perform(get("/api/emissions/projects/total"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].projectId").value("P1"))
                .andExpect(jsonPath("$[0].projectName").value("Project 1"))
                .andExpect(jsonPath("$[0].totalEmissions").value(100.0))
                .andExpect(jsonPath("$[1].projectId").value("P2"))
                .andExpect(jsonPath("$[1].projectName").value("Project 2"))
                .andExpect(jsonPath("$[1].totalEmissions").value(200.0));
    }

    @Test
    void getTotalByProject_ShouldHandleEmptyList() throws Exception {
        // Set up mock service to return empty list
        when(emissionService.getTotalEmissionsByProject()).thenReturn(List.of());

        // Perform request and verify response
        mockMvc.perform(get("/api/emissions/projects/total"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}