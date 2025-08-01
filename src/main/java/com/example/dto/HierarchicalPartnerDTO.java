package com.example.dto;

import com.example.entity.Status;
import java.util.List;
import java.util.UUID;

public class HierarchicalPartnerDTO {
    private UUID partnerId;
    private String name;
    private String description;
    private String logoUrl;
    private Status status;
    private int accountCount;
    private int solutionCount;
    private List<HierarchicalAccountDTO> accounts;

    // Constructors
    public HierarchicalPartnerDTO() {}

    public HierarchicalPartnerDTO(UUID partnerId, String name, String description, String logoUrl, Status status) {
        this.partnerId = partnerId;
        this.name = name;
        this.description = description;
        this.logoUrl = logoUrl;
        this.status = status != null ? status : Status.ACTIVE; // Default to ACTIVE if null
    }

    // Getters and Setters
    public UUID getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(UUID partnerId) {
        this.partnerId = partnerId;
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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getAccountCount() {
        return accountCount;
    }

    public void setAccountCount(int accountCount) {
        this.accountCount = accountCount;
    }

    public int getSolutionCount() {
        return solutionCount;
    }

    public void setSolutionCount(int solutionCount) {
        this.solutionCount = solutionCount;
    }

    public List<HierarchicalAccountDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<HierarchicalAccountDTO> accounts) {
        this.accounts = accounts;
    }
} 