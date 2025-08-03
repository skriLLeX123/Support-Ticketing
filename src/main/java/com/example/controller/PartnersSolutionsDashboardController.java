package com.example.controller;

import com.example.dto.PartnerDashboardDTO;
import com.example.dto.SolutionDashboardDTO;
import com.example.dto.ApiDashboardDTO;
import com.example.entity.Partner;
import com.example.entity.Solution;
import com.example.entity.Api;
import com.example.entity.SolutionEnvironment;
import com.example.entity.EnvApi;
import com.example.repository.PartnerRepository;
import com.example.repository.SolutionRepository;
import com.example.repository.ApiRepository;
import com.example.repository.SolutionEnvironmentRepository;
import com.example.repository.EnvApiRepository;
import com.example.service.SupportTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import com.example.dto.AssociationDTO;
import com.example.entity.Environment;
import java.util.Map;
import java.util.HashMap;

@Controller
public class PartnersSolutionsDashboardController {

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private SupportTicketService supportTicketService;

    @Autowired
    private ApiRepository apiRepository;

    @Autowired
    private SolutionEnvironmentRepository solutionEnvironmentRepository;

    @Autowired
    private EnvApiRepository envApiRepository;

    @GetMapping("/partners-solutions-dashboard")
    public String partnersSolutionsDashboard(Model model) {
        // Get all partners and convert to DTOs
        List<Partner> allPartners = partnerRepository.findAll();
        List<PartnerDashboardDTO> partners = allPartners.stream()
                .map(PartnerDashboardDTO::new)
                .collect(Collectors.toList());

        // Get all solutions and convert to DTOs
        List<Solution> allSolutions = solutionRepository.findAll();
        List<SolutionDashboardDTO> solutions = allSolutions.stream()
                .map(this::convertSolutionToDTO)
                .collect(Collectors.toList());

        // Get real API data from database using new structure
        List<ApiDashboardDTO> apis = getApiDataFromDatabase();

        // Calculate statistics
        long partnerCount = partners.size();
        long accountCount = partners.stream().mapToLong(PartnerDashboardDTO::getAccountCount).sum();
        long solutionCount = solutions.size();
        long apiCount = apis.size();
        long ticketCount = supportTicketService.getTicketStatistics().getTotalTickets();

        // Add data to model
        model.addAttribute("partners", partners);
        model.addAttribute("solutions", solutions);
        model.addAttribute("apis", apis);
        model.addAttribute("partnerCount", partnerCount);
        model.addAttribute("accountCount", accountCount);
        model.addAttribute("solutionCount", solutionCount);
        model.addAttribute("apiCount", apiCount);
        model.addAttribute("ticketCount", ticketCount);

        return "partners-solutions-dashboard";
    }

    @GetMapping("/associations")
    public String showAssociations(Model model) {
        List<AssociationDTO> associations = new ArrayList<>();
        
        // Get all APIs and their associations
        List<Api> allApis = apiRepository.findAll();
        
        for (Api api : allApis) {
            // Get all partner information for this API
            List<Object[]> partnerInfo = envApiRepository.findPartnerInfoByApiId(api.getId());
            
            for (Object[] info : partnerInfo) {
                String partnerName = (String) info[0];
                String logoUrl = (String) info[1];
                
                // Get all solution associations for this API
                List<EnvApi> envApis = envApiRepository.findByApiId(api.getId());
                
                for (EnvApi envApi : envApis) {
                    SolutionEnvironment solutionEnv = envApi.getSolutionEnvironment();
                    if (solutionEnv != null && solutionEnv.getSolution() != null) {
                        Solution solution = solutionEnv.getSolution();
                        Environment environment = solutionEnv.getEnvironment();
                        
                        if (solution.getAccount() != null && 
                            solution.getAccount().getPartner() != null &&
                            solution.getAccount().getPartner().getName().equals(partnerName)) {
                            
                            AssociationDTO association = new AssociationDTO();
                            association.setApiId(api.getId());
                            association.setApiName(api.getName());
                            association.setApiDescription(api.getDescription());
                            association.setPartnerName(partnerName);
                            association.setPartnerLogoUrl(logoUrl);
                            association.setAccountName(solution.getAccount().getName());
                            association.setSolutionName(solution.getName());
                            association.setSolutionDescription(solution.getDescription());
                            association.setEnvironmentName(environment.getName());
                            association.setEnvironmentType(environment.getType().getDisplayName());
                            
                            associations.add(association);
                        }
                    }
                }
            }
        }
        
        // Calculate unique counts
        Set<String> uniqueApis = associations.stream()
            .map(AssociationDTO::getApiName)
            .collect(Collectors.toSet());
        
        Set<String> uniquePartners = associations.stream()
            .map(AssociationDTO::getPartnerName)
            .collect(Collectors.toSet());
        
        Set<String> uniqueSolutions = associations.stream()
            .map(AssociationDTO::getSolutionName)
            .collect(Collectors.toSet());
        
        model.addAttribute("associations", associations);
        model.addAttribute("uniqueApiCount", uniqueApis.size());
        model.addAttribute("uniquePartnerCount", uniquePartners.size());
        model.addAttribute("uniqueSolutionCount", uniqueSolutions.size());
        
        return "associations";
    }

    @GetMapping("/dashboard-search")
    @ResponseBody
    public Map<String, Object> search(@RequestParam String query, @RequestParam String searchType) {
        Map<String, Object> result = new HashMap<>();
        String searchLower = query.toLowerCase();
        
        List<Map<String, Object>> partners = new ArrayList<>();
        List<Map<String, Object>> solutions = new ArrayList<>();
        List<Map<String, Object>> apis = new ArrayList<>();
        
        if ("all".equals(searchType)) {
            // Search partners for general search
            List<Partner> allPartners = partnerRepository.findAll();
            for (Partner partner : allPartners) {
                if (partner.getName().toLowerCase().contains(searchLower) || 
                    (partner.getDescription() != null && partner.getDescription().toLowerCase().contains(searchLower))) {
                    Map<String, Object> partnerData = new HashMap<>();
                    partnerData.put("id", partner.getPartnerId());
                    partnerData.put("name", partner.getName());
                    partnerData.put("description", partner.getDescription());
                    partnerData.put("logoUrl", partner.getLogoUrl());
                    partners.add(partnerData);
                }
            }
        } else if ("partner".equals(searchType)) {
            // For partner search, only find the specific partner being searched
            List<Partner> allPartners = partnerRepository.findAll();
            for (Partner partner : allPartners) {
                if (partner.getName().toLowerCase().contains(searchLower)) {
                    Map<String, Object> partnerData = new HashMap<>();
                    partnerData.put("id", partner.getPartnerId());
                    partnerData.put("name", partner.getName());
                    partnerData.put("description", partner.getDescription());
                    partnerData.put("logoUrl", partner.getLogoUrl());
                    partners.add(partnerData);
                }
            }
        }
        
        if ("all".equals(searchType) || "solution".equals(searchType)) {
            // Search solutions
            List<Solution> allSolutions = solutionRepository.findAll();
            for (Solution solution : allSolutions) {
                boolean matches = solution.getName().toLowerCase().contains(searchLower) ||
                                (solution.getDescription() != null && solution.getDescription().toLowerCase().contains(searchLower));
                
                // Also check if partner name matches
                if (solution.getAccount() != null && solution.getAccount().getPartner() != null) {
                    String partnerName = solution.getAccount().getPartner().getName().toLowerCase();
                    if (partnerName.contains(searchLower)) {
                        matches = true;
                    }
                }
                
                if (matches) {
                    Map<String, Object> solutionData = new HashMap<>();
                    solutionData.put("id", solution.getSolutionId());
                    solutionData.put("name", solution.getName());
                    solutionData.put("description", solution.getDescription());
                    
                    if (solution.getAccount() != null) {
                        solutionData.put("accountName", solution.getAccount().getName());
                        if (solution.getAccount().getPartner() != null) {
                            solutionData.put("partnerName", solution.getAccount().getPartner().getName());
                            solutionData.put("partnerLogoUrl", solution.getAccount().getPartner().getLogoUrl());
                        }
                    }
                    solutions.add(solutionData);
                }
            }
        } else if ("partner".equals(searchType)) {
            // For partner search, only find solutions that belong to the specific partner being searched
            List<Solution> allSolutions = solutionRepository.findAll();
            for (Solution solution : allSolutions) {
                if (solution.getAccount() != null && solution.getAccount().getPartner() != null) {
                    String partnerName = solution.getAccount().getPartner().getName().toLowerCase();
                    if (partnerName.contains(searchLower)) {
                        Map<String, Object> solutionData = new HashMap<>();
                        solutionData.put("id", solution.getSolutionId());
                        solutionData.put("name", solution.getName());
                        solutionData.put("description", solution.getDescription());
                        
                        solutionData.put("accountName", solution.getAccount().getName());
                        solutionData.put("partnerName", solution.getAccount().getPartner().getName());
                        solutionData.put("partnerLogoUrl", solution.getAccount().getPartner().getLogoUrl());
                        
                        solutions.add(solutionData);
                    }
                }
            }
        }
        
        if ("all".equals(searchType) || "api".equals(searchType)) {
            // Search APIs by name/description or associated partner/solution
            List<Api> allApis = apiRepository.findAll();
            Map<String, Map<String, Object>> uniqueApis = new HashMap<>(); // Group by base API name
            
            for (Api api : allApis) {
                // Get base API name (remove environment prefixes)
                String baseApiName = getBaseApiName(api.getName());
                
                boolean matches = api.getName().toLowerCase().contains(searchLower) ||
                                (api.getDescription() != null && api.getDescription().toLowerCase().contains(searchLower));
                
                // Also check if any associated partners or solutions match
                List<Object[]> partnerInfo = envApiRepository.findPartnerInfoByApiId(api.getId());
                for (Object[] info : partnerInfo) {
                    String partnerName = ((String) info[0]).toLowerCase();
                    if (partnerName.contains(searchLower)) {
                        matches = true;
                        break;
                    }
                }
                
                if (matches) {
                    // Get associated partners and solutions for this API
                    List<String> associatedPartners = new ArrayList<>();
                    List<String> associatedSolutions = new ArrayList<>();
                    
                    List<EnvApi> envApis = envApiRepository.findByApiId(api.getId());
                    for (EnvApi envApi : envApis) {
                        SolutionEnvironment solutionEnv = envApi.getSolutionEnvironment();
                        if (solutionEnv != null && solutionEnv.getSolution() != null) {
                            Solution solution = solutionEnv.getSolution();
                            if (solution.getAccount() != null && solution.getAccount().getPartner() != null) {
                                String partnerName = solution.getAccount().getPartner().getName();
                                String solutionName = solution.getName();
                                
                                if (!associatedPartners.contains(partnerName)) {
                                    associatedPartners.add(partnerName);
                                }
                                if (!associatedSolutions.contains(solutionName)) {
                                    associatedSolutions.add(solutionName);
                                }
                            }
                        }
                    }
                    
                    // Merge with existing base API or create new one
                    if (uniqueApis.containsKey(baseApiName)) {
                        Map<String, Object> existingApi = uniqueApis.get(baseApiName);
                        List<String> existingPartners = (List<String>) existingApi.get("associatedPartners");
                        List<String> existingSolutions = (List<String>) existingApi.get("associatedSolutions");
                        
                        // Merge partners
                        for (String partner : associatedPartners) {
                            if (!existingPartners.contains(partner)) {
                                existingPartners.add(partner);
                            }
                        }
                        
                        // Merge solutions
                        for (String solution : associatedSolutions) {
                            if (!existingSolutions.contains(solution)) {
                                existingSolutions.add(solution);
                            }
                        }
                    } else {
                        Map<String, Object> apiData = new HashMap<>();
                        apiData.put("id", api.getId()); // Use the first API's ID
                        apiData.put("name", baseApiName); // Use base name
                        apiData.put("description", api.getDescription());
                        apiData.put("associatedPartners", associatedPartners);
                        apiData.put("associatedSolutions", associatedSolutions);
                        uniqueApis.put(baseApiName, apiData);
                    }
                }
            }
            
            // Add unique APIs to the result
            apis.addAll(uniqueApis.values());
        } else if ("solution".equals(searchType)) {
            // For solution search, only find APIs that are directly associated with the specific solutions being searched
            List<Api> allApis = apiRepository.findAll();
            Map<String, Map<String, Object>> uniqueApis = new HashMap<>(); // Group by base API name
            
            for (Api api : allApis) {
                // Get base API name (remove environment prefixes)
                String baseApiName = getBaseApiName(api.getName());
                
                // Check if this API is associated with any solution that matches the search
                List<EnvApi> envApis = envApiRepository.findByApiId(api.getId());
                boolean isAssociatedWithSearchedSolution = false;
                Set<String> matchingSolutions = new HashSet<>();
                Set<String> matchingPartners = new HashSet<>();
                
                for (EnvApi envApi : envApis) {
                    SolutionEnvironment solutionEnv = envApi.getSolutionEnvironment();
                    if (solutionEnv != null && solutionEnv.getSolution() != null) {
                        Solution solution = solutionEnv.getSolution();
                        String solutionName = solution.getName().toLowerCase();
                        
                        if (solutionName.contains(searchLower)) {
                            isAssociatedWithSearchedSolution = true;
                            matchingSolutions.add(solution.getName());
                            if (solution.getAccount() != null && solution.getAccount().getPartner() != null) {
                                matchingPartners.add(solution.getAccount().getPartner().getName());
                            }
                        }
                    }
                }
                
                if (isAssociatedWithSearchedSolution) {
                    // Merge with existing base API or create new one
                    if (uniqueApis.containsKey(baseApiName)) {
                        Map<String, Object> existingApi = uniqueApis.get(baseApiName);
                        List<String> existingPartners = (List<String>) existingApi.get("associatedPartners");
                        List<String> existingSolutions = (List<String>) existingApi.get("associatedSolutions");
                        
                        // Merge partners
                        for (String partner : matchingPartners) {
                            if (!existingPartners.contains(partner)) {
                                existingPartners.add(partner);
                            }
                        }
                        
                        // Merge solutions
                        for (String solution : matchingSolutions) {
                            if (!existingSolutions.contains(solution)) {
                                existingSolutions.add(solution);
                            }
                        }
                    } else {
                        Map<String, Object> apiData = new HashMap<>();
                        apiData.put("id", api.getId()); // Use the first API's ID
                        apiData.put("name", baseApiName); // Use base name
                        apiData.put("description", api.getDescription());
                        apiData.put("associatedPartners", new ArrayList<>(matchingPartners));
                        apiData.put("associatedSolutions", new ArrayList<>(matchingSolutions));
                        uniqueApis.put(baseApiName, apiData);
                    }
                }
            }
            
            // Add unique APIs to the result
            apis.addAll(uniqueApis.values());
        } else if ("partner".equals(searchType)) {
            // For partner search, only find APIs that are directly associated with the specific partner being searched
            List<Api> allApis = apiRepository.findAll();
            Map<String, Map<String, Object>> uniqueApis = new HashMap<>(); // Group by base API name
            
            for (Api api : allApis) {
                // Get base API name (remove environment prefixes)
                String baseApiName = getBaseApiName(api.getName());
                
                // Check if this API is associated with the searched partner
                List<Object[]> partnerInfo = envApiRepository.findPartnerInfoByApiId(api.getId());
                boolean isAssociatedWithSearchedPartner = false;
                
                for (Object[] info : partnerInfo) {
                    String partnerName = ((String) info[0]).toLowerCase();
                    if (partnerName.contains(searchLower)) {
                        isAssociatedWithSearchedPartner = true;
                        break;
                    }
                }
                
                if (isAssociatedWithSearchedPartner) {
                    // For partner search, only include the specific partner being searched
                    List<String> associatedPartners = new ArrayList<>();
                    List<String> associatedSolutions = new ArrayList<>();
                    
                    // Find the exact partner name that matches the search
                    String searchedPartnerName = null;
                    for (Object[] info : partnerInfo) {
                        String partnerName = (String) info[0];
                        if (partnerName.toLowerCase().contains(searchLower)) {
                            searchedPartnerName = partnerName;
                            break;
                        }
                    }
                    
                    if (searchedPartnerName != null) {
                        associatedPartners.add(searchedPartnerName);
                    }
                    
                    // Get solutions associated with this API and the searched partner
                    List<EnvApi> envApis = envApiRepository.findByApiId(api.getId());
                    for (EnvApi envApi : envApis) {
                        SolutionEnvironment solutionEnv = envApi.getSolutionEnvironment();
                        if (solutionEnv != null && solutionEnv.getSolution() != null) {
                            Solution solution = solutionEnv.getSolution();
                            if (solution.getAccount() != null && solution.getAccount().getPartner() != null) {
                                String partnerName = solution.getAccount().getPartner().getName();
                                String solutionName = solution.getName();
                                
                                // Only include solutions that belong to the searched partner
                                if (partnerName.equals(searchedPartnerName) && !associatedSolutions.contains(solutionName)) {
                                    associatedSolutions.add(solutionName);
                                }
                            }
                        }
                    }
                    
                    // Merge with existing base API or create new one
                    if (uniqueApis.containsKey(baseApiName)) {
                        Map<String, Object> existingApi = uniqueApis.get(baseApiName);
                        List<String> existingPartners = (List<String>) existingApi.get("associatedPartners");
                        List<String> existingSolutions = (List<String>) existingApi.get("associatedSolutions");
                        
                        // Merge partners
                        for (String partner : associatedPartners) {
                            if (!existingPartners.contains(partner)) {
                                existingPartners.add(partner);
                            }
                        }
                        
                        // Merge solutions
                        for (String solution : associatedSolutions) {
                            if (!existingSolutions.contains(solution)) {
                                existingSolutions.add(solution);
                            }
                        }
                    } else {
                        Map<String, Object> apiData = new HashMap<>();
                        apiData.put("id", api.getId()); // Use the first API's ID
                        apiData.put("name", baseApiName); // Use base name
                        apiData.put("description", api.getDescription());
                        apiData.put("associatedPartners", associatedPartners);
                        apiData.put("associatedSolutions", associatedSolutions);
                        uniqueApis.put(baseApiName, apiData);
                    }
                }
            }
            
            // Add unique APIs to the result
            apis.addAll(uniqueApis.values());
        }
        
        result.put("partners", partners);
        result.put("solutions", solutions);
        result.put("apis", apis);
        
        return result;
    }
    
    /**
     * Extract the base API name by removing environment prefixes
     */
    private String getBaseApiName(String apiName) {
        if (apiName == null) return null;
        
        // Remove common environment prefixes
        String baseName = apiName;
        String[] prefixes = {"Dev ", "UAT ", "Sandbox ", "Test ", "Staging "};
        
        for (String prefix : prefixes) {
            if (baseName.startsWith(prefix)) {
                baseName = baseName.substring(prefix.length());
                break;
            }
        }
        
        return baseName;
    }

    private SolutionDashboardDTO convertSolutionToDTO(Solution solution) {
        String partnerName = null;
        String partnerLogoUrl = null;
        String accountName = null;
        
        // Get partner and account information
        if (solution.getAccount() != null) {
            accountName = solution.getAccount().getName();
            if (solution.getAccount().getPartner() != null) {
                partnerName = solution.getAccount().getPartner().getName();
                partnerLogoUrl = solution.getAccount().getPartner().getLogoUrl();
            }
        }
        
        return new SolutionDashboardDTO(
            solution.getSolutionId(),
            solution.getName(),
            solution.getDescription(),
            partnerName,
            partnerLogoUrl,
            accountName
        );
    }

    private List<ApiDashboardDTO> getApiDataFromDatabase() {
        List<Api> allApis = apiRepository.findAll();
        Map<String, ApiDashboardDTO> uniqueApis = new HashMap<>(); // Group by base API name
        
        for (Api api : allApis) {
            // Get base API name (remove environment prefixes)
            String baseApiName = getBaseApiName(api.getName());
            
            // Get all partner information for this API
            List<Object[]> partnerInfo = envApiRepository.findPartnerInfoByApiId(api.getId());
            List<String> partnerLogos = new ArrayList<>();
            List<String> partnerNames = new ArrayList<>();
            List<String> solutionNames = new ArrayList<>();
            
            // Extract unique partner information
            for (Object[] info : partnerInfo) {
                String partnerName = (String) info[0]; // partnerName is at index 0
                String logoUrl = (String) info[1]; // logoUrl is at index 1
                
                if (partnerName != null && !partnerNames.contains(partnerName)) {
                    partnerNames.add(partnerName);
                }
                if (logoUrl != null && !partnerLogos.contains(logoUrl)) {
                    partnerLogos.add(logoUrl);
                }
            }
            
            // Get all solution associations for this API
            List<EnvApi> envApis = envApiRepository.findByApiId(api.getId());
            for (EnvApi envApi : envApis) {
                SolutionEnvironment solutionEnv = envApi.getSolutionEnvironment();
                if (solutionEnv != null && solutionEnv.getSolution() != null) {
                    Solution solution = solutionEnv.getSolution();
                    String solutionName = solution.getName();
                    
                    if (solutionName != null && !solutionNames.contains(solutionName)) {
                        solutionNames.add(solutionName);
                    }
                }
            }
            
            // Merge with existing base API or create new one
            if (uniqueApis.containsKey(baseApiName)) {
                ApiDashboardDTO existingApi = uniqueApis.get(baseApiName);
                
                // Merge partner information
                for (String partnerName : partnerNames) {
                    if (!existingApi.getPartnerNames().contains(partnerName)) {
                        existingApi.getPartnerNames().add(partnerName);
                    }
                }
                
                for (String logoUrl : partnerLogos) {
                    if (!existingApi.getPartnerLogos().contains(logoUrl)) {
                        existingApi.getPartnerLogos().add(logoUrl);
                    }
                }
                
                // Merge solution information
                for (String solutionName : solutionNames) {
                    if (!existingApi.getSolutionNames().contains(solutionName)) {
                        existingApi.getSolutionNames().add(solutionName);
                    }
                }
                
                // Update partner usage count
                existingApi.setPartnerUsageCount(existingApi.getPartnerNames().size());
                
            } else {
                // Use the first association for backward compatibility (for data attributes)
                String firstSolutionName = solutionNames.isEmpty() ? null : solutionNames.get(0);
                String firstPartnerName = partnerNames.isEmpty() ? null : partnerNames.get(0);
                
                ApiDashboardDTO apiDTO = new ApiDashboardDTO(
                    api.getId(),
                    baseApiName, // Use base name instead of full name
                    api.getDescription(),
                    firstSolutionName, // Use first solution for backward compatibility
                    firstPartnerName,  // Use first partner for backward compatibility
                    api.isActive() ? "ACTIVE" : "INACTIVE"
                );
                
                // Set partner logos, names, solution names and usage count
                apiDTO.setPartnerLogos(partnerLogos);
                apiDTO.setPartnerNames(partnerNames);
                apiDTO.setSolutionNames(solutionNames);
                apiDTO.setPartnerUsageCount(partnerNames.size());
                
                uniqueApis.put(baseApiName, apiDTO);
            }
        }
        
        return new ArrayList<>(uniqueApis.values());
    }
}