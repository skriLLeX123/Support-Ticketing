package com.example.config;

import com.example.entity.*;
import com.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

@Component
public class NestedDataInitializer implements CommandLineRunner {
    
    @Autowired
    private EnvironmentRepository environmentRepository;
    
    @Autowired
    private ApiRepository apiRepository;
    
    @Autowired
    private SolutionRepository solutionRepository;
    
    @Autowired
    private PartnerRepository partnerRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private SolutionEnvironmentRepository solutionEnvironmentRepository;
    
    @Autowired
    private EnvApiRepository envApiRepository;
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("NestedDataInitializer starting...");
        initializeEnvironments();
        initializeSampleData();
        System.out.println("NestedDataInitializer completed.");
    }
    
    private void initializeEnvironments() {
        // Only create environments if they don't exist
        if (environmentRepository.count() == 0) {
            System.out.println("Creating environments...");
            Environment prod = new Environment(
                Environment.EnvironmentType.PRODUCTION,
                "Production Environment",
                "Live production environment for customer-facing applications"
            );
            
            Environment dev = new Environment(
                Environment.EnvironmentType.DEVELOPMENT,
                "Development Environment",
                "Development environment for active development work"
            );
            
            Environment uat = new Environment(
                Environment.EnvironmentType.UAT,
                "User Acceptance Testing Environment",
                "UAT environment for testing before production deployment"
            );
            
            Environment sandbox = new Environment(
                Environment.EnvironmentType.SANDBOX,
                "Sandbox Environment",
                "Experimental environment for testing and prototyping"
            );
            
            environmentRepository.saveAll(Arrays.asList(prod, dev, uat, sandbox));
            System.out.println("Environments created successfully");
        } else {
            System.out.println("Environments already exist, count: " + environmentRepository.count());
        }
    }
    
    private void initializeSampleData() {
        // Clear existing data and recreate
        System.out.println("Clearing existing data...");
        envApiRepository.deleteAll();
        solutionEnvironmentRepository.deleteAll();
        apiRepository.deleteAll();
        
        System.out.println("Creating new data with proper structure...");
        createSampleDataWithNewStructure();
    }
    
    private void createSampleDataWithNewStructure() {
        // Get environments
        Environment prod = environmentRepository.findByType(Environment.EnvironmentType.PRODUCTION).orElse(null);
        Environment dev = environmentRepository.findByType(Environment.EnvironmentType.DEVELOPMENT).orElse(null);
        Environment uat = environmentRepository.findByType(Environment.EnvironmentType.UAT).orElse(null);
        Environment sandbox = environmentRepository.findByType(Environment.EnvironmentType.SANDBOX).orElse(null);
        
        if (prod == null || dev == null || uat == null || sandbox == null) {
            System.out.println("Environments not initialized properly");
            return;
        }
        
        // Get solutions
        List<Solution> solutions = solutionRepository.findAll();
        if (solutions.isEmpty()) {
            System.out.println("No solutions available");
            return;
        }
        
        // Step 1: Create standalone APIs
        List<Api> apis = createStandaloneApis();
        
        // Step 2: Create SolutionEnvironment entities for each solution-environment combination
        List<SolutionEnvironment> solutionEnvironments = createSolutionEnvironments(solutions, Arrays.asList(prod, dev, uat, sandbox));
        
        // Step 3: Create EnvApi associations
        createEnvApiAssociations(apis, solutionEnvironments);
    }
    
    private List<Api> createStandaloneApis() {
        List<Api> apis = new ArrayList<>();
        
        // Infrastructure APIs
        apis.add(new Api("VM Management API", "/api/v1/vms", Api.HttpMethod.GET, "Virtual machine management endpoints"));
        apis.add(new Api("Container Orchestration", "/api/v1/containers", Api.HttpMethod.POST, "Container deployment and scaling"));
        apis.add(new Api("Blob Storage API", "/api/v1/storage/blob", Api.HttpMethod.PUT, "Object storage management"));
        apis.add(new Api("File Storage API", "/api/v1/storage/file", Api.HttpMethod.GET, "File system operations"));
        
        // Entertainment/Media APIs
        apis.add(new Api("Content Delivery API", "/api/v1/content/delivery", Api.HttpMethod.GET, "Content delivery and streaming endpoints"));
        apis.add(new Api("Media Processing API", "/api/v1/media/process", Api.HttpMethod.POST, "Video and audio processing endpoints"));
        apis.add(new Api("DRM Protection API", "/api/v1/drm/protect", Api.HttpMethod.POST, "Digital rights management endpoints"));
        
        // General service APIs
        apis.add(new Api("Authentication API", "/api/v1/auth", Api.HttpMethod.POST, "User authentication and authorization"));
        apis.add(new Api("Performance Monitoring API", "/api/v1/performance", Api.HttpMethod.GET, "System performance monitoring"));
        
        // API-specific solutions
        apis.add(new Api("API Gateway", "/api/v1/gateway", Api.HttpMethod.GET, "API gateway and routing endpoints"));
        apis.add(new Api("API Management", "/api/v1/management", Api.HttpMethod.POST, "API management and configuration"));
        
        // Upload solutions
        apis.add(new Api("File Upload API", "/api/v1/upload", Api.HttpMethod.POST, "File upload and processing endpoints"));
        apis.add(new Api("Upload Management", "/api/v1/upload/manage", Api.HttpMethod.GET, "Upload management and status"));
        
        // Default APIs
        apis.add(new Api("Core API", "/api/v1/core", Api.HttpMethod.GET, "Core service endpoints"));
        apis.add(new Api("Management API", "/api/v1/manage", Api.HttpMethod.POST, "General management endpoints"));
        
        // Development APIs
        apis.add(new Api("Dev VM API", "/api/v1/dev/vms", Api.HttpMethod.GET, "Development environment management"));
        apis.add(new Api("Dev Storage API", "/api/v1/dev/storage", Api.HttpMethod.POST, "Development storage operations"));
        apis.add(new Api("Dev Content API", "/api/v1/dev/content", Api.HttpMethod.GET, "Development content operations"));
        apis.add(new Api("Dev API Gateway", "/api/v1/dev/gateway", Api.HttpMethod.GET, "Development API gateway"));
        apis.add(new Api("Dev Upload API", "/api/v1/dev/upload", Api.HttpMethod.POST, "Development upload operations"));
        apis.add(new Api("Dev Core API", "/api/v1/dev/core", Api.HttpMethod.GET, "Development core endpoints"));
        
        // UAT APIs
        apis.add(new Api("UAT Testing API", "/api/v1/uat/test", Api.HttpMethod.POST, "User acceptance testing endpoints"));
        apis.add(new Api("UAT Storage API", "/api/v1/uat/storage", Api.HttpMethod.GET, "UAT storage testing"));
        apis.add(new Api("UAT Content API", "/api/v1/uat/content", Api.HttpMethod.POST, "UAT content testing"));
        apis.add(new Api("UAT API Gateway", "/api/v1/uat/gateway", Api.HttpMethod.GET, "UAT API gateway testing"));
        apis.add(new Api("UAT Upload API", "/api/v1/uat/upload", Api.HttpMethod.POST, "UAT upload testing"));
        apis.add(new Api("UAT Core API", "/api/v1/uat/core", Api.HttpMethod.POST, "UAT core testing"));
        
        // Sandbox APIs
        apis.add(new Api("Sandbox API", "/api/v1/sandbox", Api.HttpMethod.GET, "Experimental and testing endpoints"));
        apis.add(new Api("Sandbox Storage API", "/api/v1/sandbox/storage", Api.HttpMethod.DELETE, "Experimental storage operations"));
        apis.add(new Api("Sandbox Content API", "/api/v1/sandbox/content", Api.HttpMethod.GET, "Experimental content operations"));
        apis.add(new Api("Sandbox API Gateway", "/api/v1/sandbox/gateway", Api.HttpMethod.GET, "Experimental API gateway"));
        apis.add(new Api("Sandbox Upload API", "/api/v1/sandbox/upload", Api.HttpMethod.POST, "Experimental upload operations"));
        apis.add(new Api("Sandbox Core API", "/api/v1/sandbox/core", Api.HttpMethod.GET, "Experimental core operations"));
        
        // Save all APIs
        apiRepository.saveAll(apis);
        System.out.println("Created " + apis.size() + " standalone APIs");
        
        return apis;
    }
    
    private List<SolutionEnvironment> createSolutionEnvironments(List<Solution> solutions, List<Environment> environments) {
        List<SolutionEnvironment> solutionEnvironments = new ArrayList<>();
        
        for (Solution solution : solutions) {
            for (Environment environment : environments) {
                SolutionEnvironment solutionEnvironment = new SolutionEnvironment(solution, environment);
                solutionEnvironments.add(solutionEnvironment);
            }
        }
        
        solutionEnvironmentRepository.saveAll(solutionEnvironments);
        System.out.println("Created " + solutionEnvironments.size() + " solution-environment associations");
        
        return solutionEnvironments;
    }
    
    private void createEnvApiAssociations(List<Api> apis, List<SolutionEnvironment> solutionEnvironments) {
        List<EnvApi> envApis = new ArrayList<>();
        
        for (SolutionEnvironment solutionEnvironment : solutionEnvironments) {
            String solutionName = solutionEnvironment.getSolution().getName().toLowerCase();
            String environmentName = solutionEnvironment.getEnvironment().getName().toLowerCase();
            
            // Associate APIs based on solution type and environment
            for (Api api : apis) {
                boolean shouldAssociate = false;
                
                // Production environment associations
                if (environmentName.contains("production")) {
                    if (solutionName.contains("compute") || solutionName.contains("server")) {
                        shouldAssociate = api.getName().contains("VM Management") || api.getName().contains("Container Orchestration");
                    } else if (solutionName.contains("storage") || solutionName.contains("database")) {
                        shouldAssociate = api.getName().contains("Blob Storage") || api.getName().contains("File Storage");
                    } else if (solutionName.contains("streaming") || solutionName.contains("content") || solutionName.contains("drm") || solutionName.contains("video")) {
                        shouldAssociate = api.getName().contains("Content Delivery") || api.getName().contains("Media Processing") || api.getName().contains("DRM Protection");
                    } else if (solutionName.contains("api")) {
                        shouldAssociate = api.getName().contains("API Gateway") || api.getName().contains("API Management");
                    } else if (solutionName.contains("upload")) {
                        shouldAssociate = api.getName().contains("File Upload") || api.getName().contains("Upload Management");
                    } else {
                        shouldAssociate = api.getName().contains("Core API") || api.getName().contains("Management API");
                    }
                }
                // Development environment associations
                else if (environmentName.contains("development")) {
                    if (solutionName.contains("compute") || solutionName.contains("server")) {
                        shouldAssociate = api.getName().contains("Dev VM");
                    } else if (solutionName.contains("storage") || solutionName.contains("database")) {
                        shouldAssociate = api.getName().contains("Dev Storage");
                    } else if (solutionName.contains("streaming") || solutionName.contains("content") || solutionName.contains("drm") || solutionName.contains("video")) {
                        shouldAssociate = api.getName().contains("Dev Content");
                    } else if (solutionName.contains("api")) {
                        shouldAssociate = api.getName().contains("Dev API Gateway");
                    } else if (solutionName.contains("upload")) {
                        shouldAssociate = api.getName().contains("Dev Upload");
                    } else {
                        shouldAssociate = api.getName().contains("Dev Core");
                    }
                }
                // UAT environment associations
                else if (environmentName.contains("uat")) {
                    if (solutionName.contains("compute") || solutionName.contains("server")) {
                        shouldAssociate = api.getName().contains("UAT Testing");
                    } else if (solutionName.contains("storage") || solutionName.contains("database")) {
                        shouldAssociate = api.getName().contains("UAT Storage");
                    } else if (solutionName.contains("streaming") || solutionName.contains("content") || solutionName.contains("drm") || solutionName.contains("video")) {
                        shouldAssociate = api.getName().contains("UAT Content");
                    } else if (solutionName.contains("api")) {
                        shouldAssociate = api.getName().contains("UAT API Gateway");
                    } else if (solutionName.contains("upload")) {
                        shouldAssociate = api.getName().contains("UAT Upload");
                    } else {
                        shouldAssociate = api.getName().contains("UAT Core");
                    }
                }
                // Sandbox environment associations
                else if (environmentName.contains("sandbox")) {
                    if (solutionName.contains("compute") || solutionName.contains("server")) {
                        shouldAssociate = api.getName().contains("Sandbox API") && !api.getName().contains("Storage") && !api.getName().contains("Content") && !api.getName().contains("Gateway") && !api.getName().contains("Upload") && !api.getName().contains("Core");
                    } else if (solutionName.contains("storage") || solutionName.contains("database")) {
                        shouldAssociate = api.getName().contains("Sandbox Storage");
                    } else if (solutionName.contains("streaming") || solutionName.contains("content") || solutionName.contains("drm") || solutionName.contains("video")) {
                        shouldAssociate = api.getName().contains("Sandbox Content");
                    } else if (solutionName.contains("api")) {
                        shouldAssociate = api.getName().contains("Sandbox API Gateway");
                    } else if (solutionName.contains("upload")) {
                        shouldAssociate = api.getName().contains("Sandbox Upload");
                    } else {
                        shouldAssociate = api.getName().contains("Sandbox Core");
                    }
                }
                
                if (shouldAssociate) {
                    EnvApi envApi = new EnvApi(solutionEnvironment, api);
                    envApis.add(envApi);
                }
            }
        }
        
        envApiRepository.saveAll(envApis);
        System.out.println("Created " + envApis.size() + " API associations");
    }
}