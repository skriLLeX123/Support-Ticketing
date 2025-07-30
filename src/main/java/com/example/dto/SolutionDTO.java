package com.example.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import com.example.entity.Status;
import com.example.entity.Environment;

public class SolutionDTO {
    private UUID solutionId;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
    
    // Account information
    private UUID accountId;
    private String accountName;
    private Status accountStatus;
    
    // Partner information
    private UUID partnerId;
    private String partnerName;
    private String partnerLogoUrl;
    private Integer partnerMemberCount;
    private Integer partnerApiCount;
    private Status partnerStatus;
    
    // Solution status
    private Status status;
    
    // Environment information
    private List<EnvironmentInfo> environments;

    // Inner class for environment information
    public static class EnvironmentInfo {
        private String name;
        private String code;
        private Status status;
        private String color;
        
        public EnvironmentInfo() {}
        
        public EnvironmentInfo(String name, String code, Status status, String color) {
            this.name = name;
            this.code = code;
            this.status = status;
            this.color = color;
        }
        
        // Getters and Setters
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getCode() {
            return code;
        }
        
        public void setCode(String code) {
            this.code = code;
        }
        
        public Status getStatus() {
            return status;
        }
        
        public void setStatus(Status status) {
            this.status = status;
        }
        
        public String getColor() {
            return color;
        }
        
        public void setColor(String color) {
            this.color = color;
        }
    }

    // Constructors
    public SolutionDTO() {}

    public SolutionDTO(UUID solutionId, String name, String description, LocalDateTime createdAt, LocalDateTime lastUpdated,
                      UUID accountId, String accountName, Status accountStatus, UUID partnerId, String partnerName, 
                      String partnerLogoUrl, Integer partnerMemberCount, Integer partnerApiCount, Status partnerStatus, Status status) {
        this.solutionId = solutionId;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
        this.accountId = accountId;
        this.accountName = accountName;
        this.accountStatus = accountStatus;
        this.partnerId = partnerId;
        this.partnerName = partnerName;
        this.partnerLogoUrl = partnerLogoUrl;
        this.partnerMemberCount = partnerMemberCount;
        this.partnerApiCount = partnerApiCount;
        this.partnerStatus = partnerStatus;
        this.status = status;
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

    public Status getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Status accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Status getPartnerStatus() {
        return partnerStatus;
    }

    public void setPartnerStatus(Status partnerStatus) {
        this.partnerStatus = partnerStatus;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<EnvironmentInfo> getEnvironments() {
        return environments;
    }

    public void setEnvironments(List<EnvironmentInfo> environments) {
        this.environments = environments;
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
                ", accountStatus=" + accountStatus +
                ", partnerId=" + partnerId +
                ", partnerName='" + partnerName + '\'' +
                ", partnerLogoUrl='" + partnerLogoUrl + '\'' +
                ", partnerMemberCount=" + partnerMemberCount +
                ", partnerApiCount=" + partnerApiCount +
                ", partnerStatus=" + partnerStatus +
                ", status=" + status +
                ", environments=" + environments +
                '}';
    }
} 