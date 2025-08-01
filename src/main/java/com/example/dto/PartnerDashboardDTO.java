package com.example.dto;

import com.example.entity.Partner;
import java.util.UUID;

public class PartnerDashboardDTO {
    private UUID partnerId;
    private String name;
    private String description;
    private String logoUrl;
    private Integer memberCount;
    private Integer apiCount;
    private int accountCount;
    private int solutionCount;

    public PartnerDashboardDTO(Partner partner) {
        this.partnerId = partner.getPartnerId();
        this.name = partner.getName();
        this.description = partner.getDescription();
        this.logoUrl = partner.getLogoUrl();
        this.memberCount = partner.getMemberCount();
        this.apiCount = partner.getApiCount();
        this.accountCount = partner.getAccounts().size();
        this.solutionCount = partner.getAccounts().stream()
                .mapToInt(account -> account.getSolutions().size())
                .sum();
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
} 