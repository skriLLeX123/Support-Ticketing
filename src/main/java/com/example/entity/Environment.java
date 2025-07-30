package com.example.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "environments")
public class Environment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private EnvironmentType type;
    
    @Column(nullable = false)
    private String name;
    
    @Column
    private String description;
    
    @Column(nullable = false)
    private boolean active = true;
    
    @ManyToMany(mappedBy = "environments")
    private List<Solution> solutions;
    
    @OneToMany(mappedBy = "environment", cascade = CascadeType.ALL)
    private List<Api> apis;
    
    public enum EnvironmentType {
        PRODUCTION("Production", "prod", "#e74c3c"),
        DEVELOPMENT("Development", "dev", "#3498db"),
        UAT("UAT", "uat", "#f39c12"),
        SANDBOX("Sandbox", "sandbox", "#27ae60");
        
        private final String displayName;
        private final String code;
        private final String color;
        
        EnvironmentType(String displayName, String code, String color) {
            this.displayName = displayName;
            this.code = code;
            this.color = color;
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
        public String getCode() {
            return code;
        }
        
        public String getColor() {
            return color;
        }
    }
    
    // Constructors
    public Environment() {}
    
    public Environment(EnvironmentType type, String name, String description) {
        this.type = type;
        this.name = name;
        this.description = description;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public EnvironmentType getType() {
        return type;
    }
    
    public void setType(EnvironmentType type) {
        this.type = type;
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
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public List<Solution> getSolutions() {
        return solutions;
    }
    
    public void setSolutions(List<Solution> solutions) {
        this.solutions = solutions;
    }
    
    public List<Api> getApis() {
        return apis;
    }
    
    public void setApis(List<Api> apis) {
        this.apis = apis;
    }
}