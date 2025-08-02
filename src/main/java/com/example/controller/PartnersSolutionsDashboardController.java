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

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

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
        List<ApiDashboardDTO> apis = new ArrayList<>();
        
        for (Api api : allApis) {
            String solutionName = null;
            String partnerName = null;
            
            // Get solution and partner information through EnvApi relationship
            List<EnvApi> envApis = envApiRepository.findByApiId(api.getId());
            if (!envApis.isEmpty()) {
                EnvApi envApi = envApis.get(0); // Get first association
                SolutionEnvironment solutionEnv = envApi.getSolutionEnvironment();
                if (solutionEnv != null && solutionEnv.getSolution() != null) {
                    Solution solution = solutionEnv.getSolution();
                    solutionName = solution.getName();
                    
                    if (solution.getAccount() != null && 
                        solution.getAccount().getPartner() != null) {
                        partnerName = solution.getAccount().getPartner().getName();
                    }
                }
            }
            
            apis.add(new ApiDashboardDTO(
                api.getId(),
                api.getName(),
                api.getDescription(),
                solutionName, // Add solution name for association
                partnerName,  // Add partner name for association
                api.isActive() ? "ACTIVE" : "INACTIVE"
            ));
        }
        
        return apis;
    }
} 