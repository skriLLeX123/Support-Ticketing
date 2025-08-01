package com.example.controller;

import com.example.entity.Partner;
import com.example.entity.SupportTicket;
import com.example.dto.PartnerDashboardDTO;
import com.example.service.SupportTicketService;
import com.example.repository.PartnerRepository;
import com.example.repository.SupportTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PartnerDashboardController {

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private SupportTicketRepository supportTicketRepository;

    @Autowired
    private SupportTicketService supportTicketService;

    @GetMapping("/partner-dashboard")
    public String partnerDashboard(Model model) {
        try {
            // Get partners and convert to DTOs with calculated counts
            List<Partner> allPartners = partnerRepository.findAll();
            List<PartnerDashboardDTO> partners = allPartners.stream()
                    .map(PartnerDashboardDTO::new)
                    .collect(Collectors.toList());
            
            // Get recent tickets (get all and limit to 10)
            List<SupportTicket> allTickets = supportTicketRepository.findAll();
            List<SupportTicket> tickets = allTickets.stream()
                    .limit(10)
                    .toList();
            
            // Calculate statistics
            long partnerCount = partners.size();
            long accountCount = partners.stream()
                    .mapToLong(PartnerDashboardDTO::getAccountCount)
                    .sum();
            long solutionCount = partners.stream()
                    .mapToLong(PartnerDashboardDTO::getSolutionCount)
                    .sum();
            long ticketCount = supportTicketRepository.count();

            // Add data to model
            model.addAttribute("partners", partners);
            model.addAttribute("tickets", tickets);
            model.addAttribute("partnerCount", partnerCount);
            model.addAttribute("accountCount", accountCount);
            model.addAttribute("solutionCount", solutionCount);
            model.addAttribute("ticketCount", ticketCount);

            return "partner-dashboard";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Failed to load partner dashboard data: " + e.getMessage());
            return "error";
        }
    }
} 