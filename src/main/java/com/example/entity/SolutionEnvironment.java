package com.example.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "solution_environments")
public class SolutionEnvironment {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solution_id", nullable = false)
    private Solution solution;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "environment_id", nullable = false)
    private Environment environment;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "solutionEnvironment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<EnvApi> envApis = new HashSet<>();
    
    public SolutionEnvironment() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public SolutionEnvironment(Solution solution, Environment environment) {
        this();
        this.solution = solution;
        this.environment = environment;
    }
    
    // Getters and Setters
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public Solution getSolution() {
        return solution;
    }
    
    public void setSolution(Solution solution) {
        this.solution = solution;
        this.updatedAt = LocalDateTime.now();
    }
    
    public Environment getEnvironment() {
        return environment;
    }
    
    public void setEnvironment(Environment environment) {
        this.environment = environment;
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
    
    public Set<EnvApi> getEnvApis() {
        return envApis;
    }
    
    public void setEnvApis(Set<EnvApi> envApis) {
        this.envApis = envApis;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "SolutionEnvironment{" +
                "id=" + id +
                ", solution=" + (solution != null ? solution.getName() : "null") +
                ", environment=" + (environment != null ? environment.getName() : "null") +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
} 