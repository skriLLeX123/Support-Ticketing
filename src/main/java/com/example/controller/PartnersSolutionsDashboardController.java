package com.example.controller;

import com.example.dto.PartnerDashboardDTO;
import com.example.dto.SolutionDashboardDTO;
import com.example.dto.ApiDashboardDTO;
import com.example.entity.Partner;
import com.example.entity.Solution;
import com.example.repository.PartnerRepository;
import com.example.repository.SolutionRepository;
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

        // Create sample API data
        List<ApiDashboardDTO> apis = createSampleApiData();

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

    private List<ApiDashboardDTO> createSampleApiData() {
        List<ApiDashboardDTO> apis = new ArrayList<>();
        
        apis.add(new ApiDashboardDTO(
            UUID.randomUUID(),
            "User Management API",
            "Handles user authentication, registration, and profile management",
            "v2.1.0",
            "ACTIVE"
        ));
        
        apis.add(new ApiDashboardDTO(
            UUID.randomUUID(),
            "Payment Gateway API",
            "Processes payments and manages billing information",
            "v1.8.3",
            "ACTIVE"
        ));
        
        apis.add(new ApiDashboardDTO(
            UUID.randomUUID(),
            "Notification Service API",
            "Sends email, SMS, and push notifications",
            "v3.0.1",
            "ACTIVE"
        ));
        
        apis.add(new ApiDashboardDTO(
            UUID.randomUUID(),
            "Analytics API",
            "Provides data analytics and reporting capabilities",
            "v2.5.2",
            "ACTIVE"
        ));
        
        apis.add(new ApiDashboardDTO(
            UUID.randomUUID(),
            "File Storage API",
            "Manages file uploads, downloads, and storage",
            "v1.9.0",
            "ACTIVE"
        ));
        
        return apis;
    }
} 