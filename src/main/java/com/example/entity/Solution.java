package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.GenericGenerator;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "solutions")
public class Solution {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "solution_id", updatable = false, nullable = false)
    private UUID solutionId;

    @NotBlank(message = "Solution name is required")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @OneToMany(mappedBy = "solution", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<SupportTicket> tickets = new HashSet<>();

    @OneToMany(mappedBy = "solution", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<SolutionEnvironment> solutionEnvironments = new HashSet<>();

    public Solution() {
        this.createdAt = LocalDateTime.now();
        this.lastUpdated = LocalDateTime.now();
    }

    public Solution(String name, String description, Account account) {
        this();
        this.name = name;
        this.description = description;
        this.account = account;
    }

    // Getters and Setters
    public UUID getSolutionId() {
        return solutionId;
    }

    public void setSolutionId(UUID solutionId) {
        this.solutionId = solutionId;
    }

    // Alias for Spring Data JPA compatibility
    public UUID getId() {
        return solutionId;
    }

    public void setId(UUID id) {
        this.solutionId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.lastUpdated = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
        this.lastUpdated = LocalDateTime.now();
    }

    public Set<SupportTicket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<SupportTicket> tickets) {
        this.tickets = tickets;
    }

    public Set<SolutionEnvironment> getSolutionEnvironments() {
        return solutionEnvironments;
    }

    public void setSolutionEnvironments(Set<SolutionEnvironment> solutionEnvironments) {
        this.solutionEnvironments = solutionEnvironments;
    }

    // Helper methods to access environments
    public Set<Environment> getEnvironments() {
        Set<Environment> environments = new HashSet<>();
        for (SolutionEnvironment se : solutionEnvironments) {
            environments.add(se.getEnvironment());
        }
        return environments;
    }

    public void setEnvironments(Set<Environment> environments) {
        this.solutionEnvironments.clear();
        for (Environment environment : environments) {
            SolutionEnvironment se = new SolutionEnvironment();
            se.setSolution(this);
            se.setEnvironment(environment);
            this.solutionEnvironments.add(se);
        }
    }

    @Override
    public String toString() {
        return "Solution{" +
                "solutionId=" + solutionId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", account=" + (account != null ? account.getName() : "null") +
                ", createdAt=" + createdAt +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
} 