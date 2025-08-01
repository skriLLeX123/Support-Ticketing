package com.example.dto;

import java.util.UUID;

public class SolutionDashboardDTO {
    private UUID solutionId;
    private String name;
    private String description;
    private String partnerName;
    private String partnerLogoUrl;
    private String accountName;

    public SolutionDashboardDTO(UUID solutionId, String name, String description, 
                               String partnerName, String partnerLogoUrl, String accountName) {
        this.solutionId = solutionId;
        this.name = name;
        this.description = description;
        this.partnerName = partnerName;
        this.partnerLogoUrl = partnerLogoUrl;
        this.accountName = accountName;
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

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerLogoUrl() {
        return partnerLogoUrl;
    }

    public void setPartnerLogoUrl(String partnerLogoUrl) {
        this.partnerLogoUrl = partnerLogoUrl;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
} 