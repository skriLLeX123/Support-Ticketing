package com.example.dto;

import com.example.entity.Status;
import java.util.List;
import java.util.UUID;

public class HierarchicalAccountDTO {
    private UUID accountId;
    private String name;
    private String description;
    private Status status;
    private int solutionCount;
    private List<HierarchicalSolutionDTO> solutions;

    // Constructors
    public HierarchicalAccountDTO() {}

    public HierarchicalAccountDTO(UUID accountId, String name, String description, Status status) {
        this.accountId = accountId;
        this.name = name;
        this.description = description;
        this.status = status != null ? status : Status.ACTIVE; // Default to ACTIVE if null
    }

    // Getters and Setters
    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
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

    public int getSolutionCount() {
        return solutionCount;
    }

    public void setSolutionCount(int solutionCount) {
        this.solutionCount = solutionCount;
    }

    public List<HierarchicalSolutionDTO> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<HierarchicalSolutionDTO> solutions) {
        this.solutions = solutions;
    }
} 