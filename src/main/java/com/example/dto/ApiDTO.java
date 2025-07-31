package com.example.dto;

import com.example.entity.Api;
import com.example.entity.Status;
import java.time.LocalDateTime;
import java.util.List;

public class ApiDTO {
    private Long id;
    private String name;
    private String endpoint;
    private Api.HttpMethod method;
    private String description;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String environmentName;
    private String environmentCode;
    private String solutionName;
    private String partnerName;
    private String accountName;
    private List<String> partnerNames; // List of partners using this API
    private Integer partnerCount;

    public ApiDTO() {}

    public ApiDTO(Long id, String name, String endpoint, Api.HttpMethod method, String description, 
                 boolean active, LocalDateTime createdAt, LocalDateTime updatedAt, 
                 String environmentName, String environmentCode, String solutionName, 
                 String partnerName, String accountName, List<String> partnerNames, Integer partnerCount) {
        this.id = id;
        this.name = name;
        this.endpoint = endpoint;
        this.method = method;
        this.description = description;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.environmentName = environmentName;
        this.environmentCode = environmentCode;
        this.solutionName = solutionName;
        this.partnerName = partnerName;
        this.accountName = accountName;
        this.partnerNames = partnerNames;
        this.partnerCount = partnerCount;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public Api.HttpMethod getMethod() {
        return method;
    }

    public void setMethod(Api.HttpMethod method) {
        this.method = method;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    public String getEnvironmentCode() {
        return environmentCode;
    }

    public void setEnvironmentCode(String environmentCode) {
        this.environmentCode = environmentCode;
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

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public List<String> getPartnerNames() {
        return partnerNames;
    }

    public void setPartnerNames(List<String> partnerNames) {
        this.partnerNames = partnerNames;
    }

    public Integer getPartnerCount() {
        return partnerCount;
    }

    public void setPartnerCount(Integer partnerCount) {
        this.partnerCount = partnerCount;
    }

    @Override
    public String toString() {
        return "ApiDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", endpoint='" + endpoint + '\'' +
                ", method=" + method +
                ", description='" + description + '\'' +
                ", active=" + active +
                ", environmentName='" + environmentName + '\'' +
                ", solutionName='" + solutionName + '\'' +
                ", partnerName='" + partnerName + '\'' +
                ", partnerCount=" + partnerCount +
                '}';
    }
} 