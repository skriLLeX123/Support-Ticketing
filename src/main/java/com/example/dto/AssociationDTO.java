package com.example.dto;

import java.util.UUID;

public class AssociationDTO {
    private UUID apiId;
    private String apiName;
    private String apiDescription;
    private String partnerName;
    private String partnerLogoUrl;
    private String accountName;
    private String solutionName;
    private String solutionDescription;
    private String environmentName;
    private String environmentType;

    public AssociationDTO() {}

    public AssociationDTO(UUID apiId, String apiName, String apiDescription, 
                         String partnerName, String partnerLogoUrl, String accountName,
                         String solutionName, String solutionDescription,
                         String environmentName, String environmentType) {
        this.apiId = apiId;
        this.apiName = apiName;
        this.apiDescription = apiDescription;
        this.partnerName = partnerName;
        this.partnerLogoUrl = partnerLogoUrl;
        this.accountName = accountName;
        this.solutionName = solutionName;
        this.solutionDescription = solutionDescription;
        this.environmentName = environmentName;
        this.environmentType = environmentType;
    }

    // Getters and Setters
    public UUID getApiId() {
        return apiId;
    }

    public void setApiId(UUID apiId) {
        this.apiId = apiId;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getApiDescription() {
        return apiDescription;
    }

    public void setApiDescription(String apiDescription) {
        this.apiDescription = apiDescription;
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

    public String getSolutionName() {
        return solutionName;
    }

    public void setSolutionName(String solutionName) {
        this.solutionName = solutionName;
    }

    public String getSolutionDescription() {
        return solutionDescription;
    }

    public void setSolutionDescription(String solutionDescription) {
        this.solutionDescription = solutionDescription;
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    public String getEnvironmentType() {
        return environmentType;
    }

    public void setEnvironmentType(String environmentType) {
        this.environmentType = environmentType;
    }

    @Override
    public String toString() {
        return "AssociationDTO{" +
                "apiId=" + apiId +
                ", apiName='" + apiName + '\'' +
                ", apiDescription='" + apiDescription + '\'' +
                ", partnerName='" + partnerName + '\'' +
                ", partnerLogoUrl='" + partnerLogoUrl + '\'' +
                ", accountName='" + accountName + '\'' +
                ", solutionName='" + solutionName + '\'' +
                ", solutionDescription='" + solutionDescription + '\'' +
                ", environmentName='" + environmentName + '\'' +
                ", environmentType='" + environmentType + '\'' +
                '}';
    }
} 