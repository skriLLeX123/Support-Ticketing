package com.example.dto;

import com.example.entity.Severity;
import com.example.entity.TicketType;

import java.time.LocalDateTime;
import java.util.UUID;

public class SupportTicketDTO {
    private UUID ticketId;
    private String title;
    private String description;
    private TicketType ticketType;
    private Severity severity;
    private String partnerName;
    private String accountName;
    private String solutionName;
    private String supportGroupName;
    private String assigneeName;
    private Integer customersImpacted;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;

    // Constructor
    public SupportTicketDTO(UUID ticketId, String title, String description, 
                           TicketType ticketType, Severity severity, String partnerName, 
                           String accountName, String solutionName, String supportGroupName, 
                           String assigneeName, Integer customersImpacted, 
                           LocalDateTime createdAt, LocalDateTime lastUpdated) {
        this.ticketId = ticketId;
        this.title = title;
        this.description = description;
        this.ticketType = ticketType;
        this.severity = severity;
        this.partnerName = partnerName;
        this.accountName = accountName;
        this.solutionName = solutionName;
        this.supportGroupName = supportGroupName;
        this.assigneeName = assigneeName;
        this.customersImpacted = customersImpacted;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
    }

    // Getters and Setters
    public UUID getTicketId() {
        return ticketId;
    }

    public void setTicketId(UUID ticketId) {
        this.ticketId = ticketId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
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

    public String getSolutionName() {
        return solutionName;
    }

    public void setSolutionName(String solutionName) {
        this.solutionName = solutionName;
    }

    public String getSupportGroupName() {
        return supportGroupName;
    }

    public void setSupportGroupName(String supportGroupName) {
        this.supportGroupName = supportGroupName;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    public Integer getCustomersImpacted() {
        return customersImpacted;
    }

    public void setCustomersImpacted(Integer customersImpacted) {
        this.customersImpacted = customersImpacted;
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
} 