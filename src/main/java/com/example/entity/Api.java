package com.example.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "apis")
public class Api {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    
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
    
    @OneToMany(mappedBy = "api", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<EnvApi> envApis = new HashSet<>();
    
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
    public Api() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public Api(String name, String endpoint, HttpMethod method, String description) {
        this();
        this.name = name;
        this.endpoint = endpoint;
        this.method = method;
        this.description = description;
    }
    
    // Getters and Setters
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getEndpoint() {
        return endpoint;
    }
    
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        this.updatedAt = LocalDateTime.now();
    }
    
    public HttpMethod getMethod() {
        return method;
    }
    
    public void setMethod(HttpMethod method) {
        this.method = method;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
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
        return "Api{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", endpoint='" + endpoint + '\'' +
                ", method=" + method +
                ", description='" + description + '\'' +
                ", active=" + active +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}