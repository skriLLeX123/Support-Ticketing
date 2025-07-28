package com.example.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class SolutionDTO {
    private UUID solutionId;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
    
    // Account information
    private UUID accountId;
    private String accountName;
    
    // Partner information
    private UUID partnerId;
    private String partnerName;
    private String partnerLogoUrl;
    private Integer partnerMemberCount;
    private Integer partnerApiCount;

    // Constructors
    public SolutionDTO() {}

    public SolutionDTO(UUID solutionId, String name, String description, LocalDateTime createdAt, LocalDateTime lastUpdated,
                      UUID accountId, String accountName, UUID partnerId, String partnerName, String partnerLogoUrl,
                      Integer partnerMemberCount, Integer partnerApiCount) {
        this.solutionId = solutionId;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
        this.accountId = accountId;
        this.accountName = accountName;
        this.partnerId = partnerId;
        this.partnerName = partnerName;
        this.partnerLogoUrl = partnerLogoUrl;
        this.partnerMemberCount = partnerMemberCount;
        this.partnerApiCount = partnerApiCount;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public UUID getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(UUID partnerId) {
        this.partnerId = partnerId;
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

    public Integer getPartnerMemberCount() {
        return partnerMemberCount;
    }

    public void setPartnerMemberCount(Integer partnerMemberCount) {
        this.partnerMemberCount = partnerMemberCount;
    }

    public Integer getPartnerApiCount() {
        return partnerApiCount;
    }

    public void setPartnerApiCount(Integer partnerApiCount) {
        this.partnerApiCount = partnerApiCount;
    }

    @Override
    public String toString() {
        return "SolutionDTO{" +
                "solutionId=" + solutionId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", lastUpdated=" + lastUpdated +
                ", accountId=" + accountId +
                ", accountName='" + accountName + '\'' +
                ", partnerId=" + partnerId +
                ", partnerName='" + partnerName + '\'' +
                ", partnerLogoUrl='" + partnerLogoUrl + '\'' +
                ", partnerMemberCount=" + partnerMemberCount +
                ", partnerApiCount=" + partnerApiCount +
                '}';
    }
} 