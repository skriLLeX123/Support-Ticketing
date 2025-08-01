package com.example.controller;

import com.example.dto.HierarchicalPartnerDTO;
import com.example.service.HierarchicalViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HierarchicalViewController {

    @Autowired
    private HierarchicalViewService hierarchicalViewService;

    @GetMapping("/hierarchical")
    public String hierarchicalView(Model model) {
        try {
            // Get hierarchical data
            List<HierarchicalPartnerDTO> partners = hierarchicalViewService.getHierarchicalData();
            
            // Calculate statistics
            long partnerCount = partners.size();
            long accountCount = partners.stream()
                    .mapToLong(partner -> partner.getAccounts().size())
                    .sum();
            long solutionCount = partners.stream()
                    .flatMap(partner -> partner.getAccounts().stream())
                    .mapToLong(account -> account.getSolutions().size())
                    .sum();
            long activeSolutions = partners.stream()
                    .flatMap(partner -> partner.getAccounts().stream())
                    .flatMap(account -> account.getSolutions().stream())
                    .filter(solution -> "ACTIVE".equals(solution.getStatus().toString()))
                    .count();

            // Add data to model
            model.addAttribute("partners", partners);
            model.addAttribute("partnerCount", partnerCount);
            model.addAttribute("accountCount", accountCount);
            model.addAttribute("solutionCount", solutionCount);
            model.addAttribute("activeSolutions", activeSolutions);

            return "hierarchical-view";
        } catch (Exception e) {
            // Log the error and return a simple error page
            e.printStackTrace();
            model.addAttribute("error", "Failed to load hierarchical data: " + e.getMessage());
            return "error";
        }
    }
} 