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
        // Only create sample data if no APIs exist
        if (apiRepository.count() == 0) {
            createSampleApis();
        }
    }
    
    private void createSampleApis() {
        // Get environments
        Environment prod = environmentRepository.findByType(Environment.EnvironmentType.PRODUCTION).orElse(null);
        Environment dev = environmentRepository.findByType(Environment.EnvironmentType.DEVELOPMENT).orElse(null);
        Environment uat = environmentRepository.findByType(Environment.EnvironmentType.UAT).orElse(null);
        Environment sandbox = environmentRepository.findByType(Environment.EnvironmentType.SANDBOX).orElse(null);
        
        if (prod == null || dev == null || uat == null || sandbox == null) {
            return; // Environments not initialized
        }
        
        // Get some solutions to associate APIs with
        List<Solution> solutions = solutionRepository.findAll();
        if (solutions.isEmpty()) {
            return; // No solutions available
        }
        
        // Create sample APIs for each solution and environment
        for (Solution solution : solutions) {
            createApisForSolution(solution, prod, dev, uat, sandbox);
        }
    }
    
    private void createApisForSolution(Solution solution, Environment prod, Environment dev, Environment uat, Environment sandbox) {
        String solutionName = solution.getName().toLowerCase();
        
        // Production APIs
        if (solutionName.contains("compute") || solutionName.contains("server")) {
            apiRepository.save(new Api("VM Management API", "/api/v1/vms", Api.HttpMethod.GET, 
                "Virtual machine management endpoints", prod, solution));
            apiRepository.save(new Api("Container Orchestration", "/api/v1/containers", Api.HttpMethod.POST, 
                "Container deployment and scaling", prod, solution));
        } else if (solutionName.contains("storage") || solutionName.contains("database")) {
            apiRepository.save(new Api("Blob Storage API", "/api/v1/storage/blob", Api.HttpMethod.PUT, 
                "Object storage management", prod, solution));
            apiRepository.save(new Api("File Storage API", "/api/v1/storage/file", Api.HttpMethod.GET, 
                "File system operations", prod, solution));
        } else {
            apiRepository.save(new Api("Core API", "/api/v1/core", Api.HttpMethod.GET, 
                "Core service endpoints", prod, solution));
        }
        
        // Development APIs
        if (solutionName.contains("compute") || solutionName.contains("server")) {
            apiRepository.save(new Api("Dev VM API", "/api/v1/dev/vms", Api.HttpMethod.GET, 
                "Development environment management", dev, solution));
        } else if (solutionName.contains("storage") || solutionName.contains("database")) {
            apiRepository.save(new Api("Dev Storage API", "/api/v1/dev/storage", Api.HttpMethod.POST, 
                "Development storage operations", dev, solution));
        } else {
            apiRepository.save(new Api("Dev Core API", "/api/v1/dev/core", Api.HttpMethod.GET, 
                "Development core endpoints", dev, solution));
        }
        
        // UAT APIs
        if (solutionName.contains("compute") || solutionName.contains("server")) {
            apiRepository.save(new Api("UAT Testing API", "/api/v1/uat/test", Api.HttpMethod.POST, 
                "User acceptance testing endpoints", uat, solution));
        } else if (solutionName.contains("storage") || solutionName.contains("database")) {
            apiRepository.save(new Api("UAT Storage API", "/api/v1/uat/storage", Api.HttpMethod.GET, 
                "UAT storage testing", uat, solution));
        } else {
            apiRepository.save(new Api("UAT Core API", "/api/v1/uat/core", Api.HttpMethod.POST, 
                "UAT core testing", uat, solution));
        }
        
        // Sandbox APIs
        if (solutionName.contains("compute") || solutionName.contains("server")) {
            apiRepository.save(new Api("Sandbox API", "/api/v1/sandbox", Api.HttpMethod.GET, 
                "Experimental and testing endpoints", sandbox, solution));
        } else if (solutionName.contains("storage") || solutionName.contains("database")) {
            apiRepository.save(new Api("Sandbox Storage API", "/api/v1/sandbox/storage", Api.HttpMethod.DELETE, 
                "Experimental storage operations", sandbox, solution));
        } else {
            apiRepository.save(new Api("Sandbox Core API", "/api/v1/sandbox/core", Api.HttpMethod.GET, 
                "Experimental core operations", sandbox, solution));
        }
    }
}