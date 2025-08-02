package com.example.dto;

import java.util.UUID;

public class ApiDashboardDTO {
    private UUID apiId;
    private String name;
    private String description;
    private String version;
    private String status;
    private String solutionName;
    private String partnerName;

    public ApiDashboardDTO(UUID apiId, String name, String description, String version, String status) {
        this.apiId = apiId;
        this.name = name;
        this.description = description;
        this.version = version;
        this.status = status;
    }

    public ApiDashboardDTO(UUID apiId, String name, String description, String solutionName, String partnerName, String status) {
        this.apiId = apiId;
        this.name = name;
        this.description = description;
        this.solutionName = solutionName;
        this.partnerName = partnerName;
        this.status = status;
    }

    // Getters and Setters
    public UUID getApiId() {
        return apiId;
    }

    public void setApiId(UUID apiId) {
        this.apiId = apiId;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSolutionName() {
        return solutionName;
    }

    public void setSolutionName(String solutionName) {
        this.solutionName = solutionName;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }
} 