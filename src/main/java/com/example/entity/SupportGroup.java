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
@Table(name = "support_groups")
public class SupportGroup {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "support_group_id", updatable = false, nullable = false)
    private UUID supportGroupId;

    @NotBlank(message = "Group name is required")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    @OneToMany(mappedBy = "supportGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<SupportMember> members = new HashSet<>();

    @OneToMany(mappedBy = "supportGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<SupportTicket> tickets = new HashSet<>();

    public SupportGroup() {
        this.createdAt = LocalDateTime.now();
        this.lastUpdated = LocalDateTime.now();
    }

    public SupportGroup(String name, String description) {
        this();
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public UUID getSupportGroupId() {
        return supportGroupId;
    }

    public void setSupportGroupId(UUID supportGroupId) {
        this.supportGroupId = supportGroupId;
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

    public Set<SupportMember> getMembers() {
        return members;
    }

    public void setMembers(Set<SupportMember> members) {
        this.members = members;
    }

    public Set<SupportTicket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<SupportTicket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "SupportGroup{" +
                "supportGroupId=" + supportGroupId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
} 