package com.example.dto;

import com.example.entity.Status;
import java.util.UUID;

public class PartnerDTO {
    private UUID partnerId;
    private String name;
    private String description;
    private String logoUrl;
    private Integer memberCount;
    private Integer apiCount;
    private Integer accountCount;
    private Status status;

    public PartnerDTO() {}

    public PartnerDTO(UUID partnerId, String name, String description, String logoUrl, 
                     Integer memberCount, Integer apiCount, Integer accountCount, Status status) {
        this.partnerId = partnerId;
        this.name = name;
        this.description = description;
        this.logoUrl = logoUrl;
        this.memberCount = memberCount;
        this.apiCount = apiCount;
        this.accountCount = accountCount;
        this.status = status;
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

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public Integer getApiCount() {
        return apiCount;
    }

    public void setApiCount(Integer apiCount) {
        this.apiCount = apiCount;
    }

    public Integer getAccountCount() {
        return accountCount;
    }

    public void setAccountCount(Integer accountCount) {
        this.accountCount = accountCount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PartnerDTO{" +
                "partnerId=" + partnerId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", memberCount=" + memberCount +
                ", apiCount=" + apiCount +
                ", accountCount=" + accountCount +
                ", status=" + status +
                '}';
    }
} 