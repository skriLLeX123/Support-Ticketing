package com.example.dto;

import com.example.entity.Status;
import java.util.UUID;

public class HierarchicalSolutionDTO {
    private UUID solutionId;
    private String name;
    private String description;
    private Status status;
    private int environmentCount;

    // Constructors
    public HierarchicalSolutionDTO() {}

    public HierarchicalSolutionDTO(UUID solutionId, String name, String description, Status status) {
        this.solutionId = solutionId;
        this.name = name;
        this.description = description;
        this.status = status != null ? status : Status.ACTIVE; // Default to ACTIVE if null
    }

    // Getters and Setters
    public UUID getSolutionId() {
        return solutionId;
    }

    public void setSolutionId(UUID solutionId) {
        this.solutionId = solutionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getEnvironmentCount() {
        return environmentCount;
    }

    public void setEnvironmentCount(int environmentCount) {
        this.environmentCount = environmentCount;
    }
} 