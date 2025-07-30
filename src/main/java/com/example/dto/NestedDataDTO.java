package com.example.dto;

import com.example.entity.Api;
import com.example.entity.Environment;
import com.example.entity.Solution;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NestedDataDTO {
    
    public static class PartnerDTO {
        private String id;
        private String name;
        private String description;
        private String logo;
        private List<AccountDTO> accounts;
        private PartnerStats stats;
        
        public PartnerDTO() {}
        
        public PartnerDTO(String id, String name, String description, String logo) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.logo = logo;
        }
        
        // Getters and Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public String getLogo() { return logo; }
        public void setLogo(String logo) { this.logo = logo; }
        
        public List<AccountDTO> getAccounts() { return accounts; }
        public void setAccounts(List<AccountDTO> accounts) { this.accounts = accounts; }
        
        public PartnerStats getStats() { return stats; }
        public void setStats(PartnerStats stats) { this.stats = stats; }
    }
    
    public static class AccountDTO {
        private String id;
        private String name;
        private String description;
        private String icon;
        private List<SolutionDTO> solutions;
        private AccountStats stats;
        
        public AccountDTO() {}
        
        public AccountDTO(String id, String name, String description, String icon) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.icon = icon;
        }
        
        // Getters and Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public String getIcon() { return icon; }
        public void setIcon(String icon) { this.icon = icon; }
        
        public List<SolutionDTO> getSolutions() { return solutions; }
        public void setSolutions(List<SolutionDTO> solutions) { this.solutions = solutions; }
        
        public AccountStats getStats() { return stats; }
        public void setStats(AccountStats stats) { this.stats = stats; }
    }
    
    public static class SolutionDTO {
        private String id;
        private String name;
        private String description;
        private String icon;
        private List<EnvironmentDTO> environments;
        private SolutionStats stats;
        
        public SolutionDTO() {}
        
        public SolutionDTO(String id, String name, String description, String icon) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.icon = icon;
        }
        
        // Getters and Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public String getIcon() { return icon; }
        public void setIcon(String icon) { this.icon = icon; }
        
        public List<EnvironmentDTO> getEnvironments() { return environments; }
        public void setEnvironments(List<EnvironmentDTO> environments) { this.environments = environments; }
        
        public SolutionStats getStats() { return stats; }
        public void setStats(SolutionStats stats) { this.stats = stats; }
    }
    
    public static class EnvironmentDTO {
        private String id;
        private String name;
        private String type;
        private String code;
        private String color;
        private String icon;
        private List<ApiDTO> apis;
        
        public EnvironmentDTO() {}
        
        public EnvironmentDTO(String id, String name, String type, String code, String color, String icon) {
            this.id = id;
            this.name = name;
            this.type = type;
            this.code = code;
            this.color = color;
            this.icon = icon;
        }
        
        // Getters and Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        
        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }
        
        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }
        
        public String getIcon() { return icon; }
        public void setIcon(String icon) { this.icon = icon; }
        
        public List<ApiDTO> getApis() { return apis; }
        public void setApis(List<ApiDTO> apis) { this.apis = apis; }
    }
    
    public static class ApiDTO {
        private String id;
        private String name;
        private String endpoint;
        private String method;
        private String methodColor;
        private String methodBgColor;
        private String description;
        
        public ApiDTO() {}
        
        public ApiDTO(String id, String name, String endpoint, String method, String methodColor, String methodBgColor, String description) {
            this.id = id;
            this.name = name;
            this.endpoint = endpoint;
            this.method = method;
            this.methodColor = methodColor;
            this.methodBgColor = methodBgColor;
            this.description = description;
        }
        
        // Getters and Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getEndpoint() { return endpoint; }
        public void setEndpoint(String endpoint) { this.endpoint = endpoint; }
        
        public String getMethod() { return method; }
        public void setMethod(String method) { this.method = method; }
        
        public String getMethodColor() { return methodColor; }
        public void setMethodColor(String methodColor) { this.methodColor = methodColor; }
        
        public String getMethodBgColor() { return methodBgColor; }
        public void setMethodBgColor(String methodBgColor) { this.methodBgColor = methodBgColor; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
    
    public static class PartnerStats {
        private int accountCount;
        private int solutionCount;
        private int environmentCount;
        private int apiCount;
        
        public PartnerStats() {}
        
        public PartnerStats(int accountCount, int solutionCount, int environmentCount, int apiCount) {
            this.accountCount = accountCount;
            this.solutionCount = solutionCount;
            this.environmentCount = environmentCount;
            this.apiCount = apiCount;
        }
        
        // Getters and Setters
        public int getAccountCount() { return accountCount; }
        public void setAccountCount(int accountCount) { this.accountCount = accountCount; }
        
        public int getSolutionCount() { return solutionCount; }
        public void setSolutionCount(int solutionCount) { this.solutionCount = solutionCount; }
        
        public int getEnvironmentCount() { return environmentCount; }
        public void setEnvironmentCount(int environmentCount) { this.environmentCount = environmentCount; }
        
        public int getApiCount() { return apiCount; }
        public void setApiCount(int apiCount) { this.apiCount = apiCount; }
    }
    
    public static class AccountStats {
        private int solutionCount;
        private int environmentCount;
        private int apiCount;
        
        public AccountStats() {}
        
        public AccountStats(int solutionCount, int environmentCount, int apiCount) {
            this.solutionCount = solutionCount;
            this.environmentCount = environmentCount;
            this.apiCount = apiCount;
        }
        
        // Getters and Setters
        public int getSolutionCount() { return solutionCount; }
        public void setSolutionCount(int solutionCount) { this.solutionCount = solutionCount; }
        
        public int getEnvironmentCount() { return environmentCount; }
        public void setEnvironmentCount(int environmentCount) { this.environmentCount = environmentCount; }
        
        public int getApiCount() { return apiCount; }
        public void setApiCount(int apiCount) { this.apiCount = apiCount; }
    }
    
    public static class SolutionStats {
        private int environmentCount;
        private int apiCount;
        
        public SolutionStats() {}
        
        public SolutionStats(int environmentCount, int apiCount) {
            this.environmentCount = environmentCount;
            this.apiCount = apiCount;
        }
        
        // Getters and Setters
        public int getEnvironmentCount() { return environmentCount; }
        public void setEnvironmentCount(int environmentCount) { this.environmentCount = environmentCount; }
        
        public int getApiCount() { return apiCount; }
        public void setApiCount(int apiCount) { this.apiCount = apiCount; }
    }
    
    // Utility methods for conversion
    public static ApiDTO fromApi(Api api) {
        return new ApiDTO(
            api.getId().toString(),
            api.getName(),
            api.getEndpoint(),
            api.getMethod().getValue(),
            api.getMethod().getColor(),
            api.getMethod().getBgColor(),
            api.getDescription()
        );
    }
    
    public static EnvironmentDTO fromEnvironment(Environment environment) {
        return new EnvironmentDTO(
            environment.getId().toString(),
            environment.getName(),
            environment.getType().getDisplayName(),
            environment.getType().getCode(),
            environment.getType().getColor(),
            getEnvironmentIcon(environment.getType())
        );
    }
    
    public static SolutionDTO fromSolution(Solution solution) {
        return new SolutionDTO(
            solution.getSolutionId().toString(),
            solution.getName(),
            solution.getDescription(),
            getSolutionIcon(solution.getName())
        );
    }
    
    public static String getEnvironmentIcon(Environment.EnvironmentType type) {
        switch (type) {
            case PRODUCTION: return "fas fa-rocket";
            case DEVELOPMENT: return "fas fa-code";
            case UAT: return "fas fa-vial";
            case SANDBOX: return "fas fa-flask";
            default: return "fas fa-server";
        }
    }
    
    public static String getSolutionIcon(String solutionName) {
        String lowerName = solutionName.toLowerCase();
        if (lowerName.contains("compute") || lowerName.contains("server")) {
            return "fas fa-server";
        } else if (lowerName.contains("storage") || lowerName.contains("database")) {
            return "fas fa-database";
        } else if (lowerName.contains("network")) {
            return "fas fa-network-wired";
        } else if (lowerName.contains("security")) {
            return "fas fa-shield-alt";
        } else {
            return "fas fa-cube";
        }
    }
}