package com.example.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "apis")
public class Api {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String endpoint;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HttpMethod method;
    
    @Column
    private String description;
    
    @Column(nullable = false)
    private boolean active = true;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "environment_id", nullable = false)
    private Environment environment;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solution_id", nullable = false)
    private Solution solution;
    
    public enum HttpMethod {
        GET("GET", "#2e7d32", "#e8f5e8"),
        POST("POST", "#1976d2", "#e3f2fd"),
        PUT("PUT", "#f57c00", "#fff3e0"),
        DELETE("DELETE", "#d32f2f", "#fdeaea"),
        PATCH("PATCH", "#7b1fa2", "#f3e5f5");
        
        private final String value;
        private final String color;
        private final String bgColor;
        
        HttpMethod(String value, String color, String bgColor) {
            this.value = value;
            this.color = color;
            this.bgColor = bgColor;
        }
        
        public String getValue() {
            return value;
        }
        
        public String getColor() {
            return color;
        }
        
        public String getBgColor() {
            return bgColor;
        }
    }
    
    // Constructors
    public Api() {}
    
    public Api(String name, String endpoint, HttpMethod method, String description, Environment environment, Solution solution) {
        this.name = name;
        this.endpoint = endpoint;
        this.method = method;
        this.description = description;
        this.environment = environment;
        this.solution = solution;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEndpoint() {
        return endpoint;
    }
    
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
    
    public HttpMethod getMethod() {
        return method;
    }
    
    public void setMethod(HttpMethod method) {
        this.method = method;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
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
    
    public Environment getEnvironment() {
        return environment;
    }
    
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
    
    public Solution getSolution() {
        return solution;
    }
    
    public void setSolution(Solution solution) {
        this.solution = solution;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}