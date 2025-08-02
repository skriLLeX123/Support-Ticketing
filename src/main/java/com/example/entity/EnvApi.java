package com.example.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "env_apis")
public class EnvApi {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solution_environment_id", nullable = false)
    private SolutionEnvironment solutionEnvironment;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "api_id", nullable = false)
    private Api api;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    public EnvApi() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public EnvApi(SolutionEnvironment solutionEnvironment, Api api) {
        this();
        this.solutionEnvironment = solutionEnvironment;
        this.api = api;
    }
    
    // Getters and Setters
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public SolutionEnvironment getSolutionEnvironment() {
        return solutionEnvironment;
    }
    
    public void setSolutionEnvironment(SolutionEnvironment solutionEnvironment) {
        this.solutionEnvironment = solutionEnvironment;
        this.updatedAt = LocalDateTime.now();
    }
    
    public Api getApi() {
        return api;
    }
    
    public void setApi(Api api) {
        this.api = api;
        this.updatedAt = LocalDateTime.now();
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "EnvApi{" +
                "id=" + id +
                ", solutionEnvironment=" + (solutionEnvironment != null ? solutionEnvironment.getId() : "null") +
                ", api=" + (api != null ? api.getName() : "null") +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
} 