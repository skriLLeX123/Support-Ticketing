package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "support_tickets")
public class SupportTicket {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ticket_id", updatable = false, nullable = false)
    private UUID ticketId;

    @NotBlank(message = "Title is required")
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Ticket type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_type", nullable = false)
    private TicketType ticketType;

    @Column(name = "partner_name")
    private String partnerName;

    @Column(name = "account_name")
    private String accountName;

    @NotNull(message = "Solution is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solution_id", nullable = false)
    @JsonManagedReference
    private Solution solution;

    @NotNull(message = "Severity is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = false)
    private Severity severity;

    @Column(name = "customers_impacted")
    private Integer customersImpacted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "support_group_id")
    @JsonManagedReference
    private SupportGroup supportGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    @JsonManagedReference
    private SupportMember assignee;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    public SupportTicket() {
        this.createdAt = LocalDateTime.now();
        this.lastUpdated = LocalDateTime.now();
    }

    public SupportTicket(String title, String description, TicketType ticketType, 
                        String partnerName, String accountName, Solution solution, 
                        Severity severity, Integer customersImpacted) {
        this();
        this.title = title;
        this.description = description;
        this.ticketType = ticketType;
        this.partnerName = partnerName;
        this.accountName = accountName;
        this.solution = solution;
        this.severity = severity;
        this.customersImpacted = customersImpacted;
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
        this.lastUpdated = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.lastUpdated = LocalDateTime.now();
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
        this.lastUpdated = LocalDateTime.now();
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
        this.lastUpdated = LocalDateTime.now();
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
        this.lastUpdated = LocalDateTime.now();
    }

    public Solution getSolution() {
        return solution;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
        this.lastUpdated = LocalDateTime.now();
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
        this.lastUpdated = LocalDateTime.now();
    }

    public Integer getCustomersImpacted() {
        return customersImpacted;
    }

    public void setCustomersImpacted(Integer customersImpacted) {
        this.customersImpacted = customersImpacted;
        this.lastUpdated = LocalDateTime.now();
    }

    public SupportGroup getSupportGroup() {
        return supportGroup;
    }

    public void setSupportGroup(SupportGroup supportGroup) {
        this.supportGroup = supportGroup;
        this.lastUpdated = LocalDateTime.now();
    }

    public SupportMember getAssignee() {
        return assignee;
    }

    public void setAssignee(SupportMember assignee) {
        this.assignee = assignee;
        this.lastUpdated = LocalDateTime.now();
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

    @Override
    public String toString() {
        return "SupportTicket{" +
                "ticketId=" + ticketId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", ticketType=" + ticketType +
                ", partnerName='" + partnerName + '\'' +
                ", accountName='" + accountName + '\'' +
                ", solution=" + (solution != null ? solution.getName() : "null") +
                ", severity=" + severity +
                ", customersImpacted=" + customersImpacted +
                ", supportGroup=" + (supportGroup != null ? supportGroup.getName() : "null") +
                ", assignee=" + (assignee != null ? assignee.getName() : "null") +
                ", createdAt=" + createdAt +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
} 