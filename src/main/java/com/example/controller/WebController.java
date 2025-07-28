package com.example.controller;

import com.example.dto.SupportTicketDTO;
import com.example.entity.SupportTicket;
import com.example.entity.Solution;
import com.example.entity.SupportGroup;
import com.example.entity.SupportMember;
import com.example.service.SupportTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.example.entity.TicketType;

@Controller
public class WebController {

    @Autowired
    private SupportTicketService supportTicketService;

    /**
     * Dashboard page
     */
    @GetMapping("/")
    public String dashboard(Model model) {
        try {
            List<SupportTicketDTO> tickets = supportTicketService.getAllTicketsAsDTOs();
            List<Solution> solutions = supportTicketService.getAllSolutions();
            
            model.addAttribute("tickets", tickets);
            model.addAttribute("solutions", solutions);
            return "dashboard";
        } catch (Exception e) {
            // If there's an error, redirect to basic dashboard
            return "redirect:/dashboard-basic";
        }
    }

    /**
     * Dashboard page (alternative route)
     */
    @GetMapping("/dashboard")
    public String dashboardAlt(Model model) {
        try {
            List<SupportTicketDTO> tickets = supportTicketService.getAllTicketsAsDTOs();
            List<Solution> solutions = supportTicketService.getAllSolutions();
            
            model.addAttribute("tickets", tickets);
            model.addAttribute("solutions", solutions);
            return "dashboard";
        } catch (Exception e) {
            // If there's an error, redirect to basic dashboard
            return "redirect:/dashboard-basic";
        }
    }

    /**
     * Dashboard test page
     */
    @GetMapping("/dashboard-test")
    public String dashboardTest(Model model) {
        List<SupportTicketDTO> tickets = supportTicketService.getAllTicketsAsDTOs();
        List<Solution> solutions = supportTicketService.getAllSolutions();
        
        model.addAttribute("tickets", tickets);
        model.addAttribute("solutions", solutions);
        return "dashboard-test";
    }

    /**
     * Simple dashboard page
     */
    @GetMapping("/dashboard-simple")
    public String dashboardSimple(Model model) {
        List<SupportTicketDTO> tickets = supportTicketService.getAllTicketsAsDTOs();
        List<Solution> solutions = supportTicketService.getAllSolutions();
        
        model.addAttribute("tickets", tickets);
        model.addAttribute("solutions", solutions);
        return "dashboard-simple";
    }

    /**
     * Basic dashboard test page - completely independent
     */
    @GetMapping("/dashboard-basic")
    public String dashboardBasic(Model model) {
        // Don't try to load any data - just return a simple working page
        model.addAttribute("message", "Basic dashboard is working!");
        return "dashboard-basic";
    }

    /**
     * Simple test page - no data loading
     */
    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("message", "Test page is working!");
        return "test";
    }

    /**
     * Tickets listing page
     */
    @GetMapping("/tickets")
    public String tickets(Model model) {
        List<SupportTicketDTO> tickets = supportTicketService.getAllTicketsAsDTOs();
        SupportTicketService.TicketStatistics stats = supportTicketService.getTicketStatistics();
        
        model.addAttribute("tickets", tickets);
        model.addAttribute("statistics", stats);
        return "tickets"; // Using dedicated tickets template
    }

    /**
     * Solutions page
     */
    @GetMapping("/solutions")
    public String solutions(Model model) {
        List<Solution> solutions = supportTicketService.getAllSolutions();
        model.addAttribute("solutions", solutions);
        return "solutions"; // We'll need to create this template
    }

    /**
     * Ticket detail page
     */
    @GetMapping("/ticket/{id}")
    public String ticketDetail(@PathVariable UUID id, Model model) {
        Optional<SupportTicket> ticket = supportTicketService.getTicketById(id);
        if (ticket.isPresent()) {
            SupportTicket ticketData = ticket.get();
            model.addAttribute("ticket", ticketData);
            
            // Add formatted ticket ID
            String formattedTicketId = formatTicketId(ticketData.getPartnerName(), ticketData.getTicketType(), 1);
            model.addAttribute("formattedTicketId", formattedTicketId);
            
            return "ticket-detail";
        } else {
            return "redirect:/";
        }
    }

    /**
     * Create ticket page
     */
    @GetMapping("/ticket/create")
    public String createTicket(Model model) {
        return "create-ticket";
    }

    /**
     * Search results page
     */
    @GetMapping("/search")
    public String searchTickets(@RequestParam(required = false) String title,
                               @RequestParam(required = false) String partnerName,
                               @RequestParam(required = false) String accountName,
                               Model model) {
        List<SupportTicket> tickets = supportTicketService.getAllTickets();
        
        if (title != null && !title.trim().isEmpty()) {
            tickets = supportTicketService.searchTicketsByTitle(title);
        }
        
        model.addAttribute("tickets", tickets);
        model.addAttribute("searchTitle", title);
        model.addAttribute("searchPartnerName", partnerName);
        model.addAttribute("searchAccountName", accountName);
        return "search-results";
    }

    /**
     * Helper method to format ticket ID
     */
    public static String formatTicketId(String partnerName, TicketType ticketType, int sequence) {
        String prefix = getPartnerPrefix(partnerName);
        String typeChar = getTypeChar(ticketType);
        return String.format("%s%s%06d", prefix, typeChar, sequence);
    }

    private static String getPartnerPrefix(String partnerName) {
        switch (partnerName.toUpperCase()) {
            case "AMAZON": return "AMZ";
            case "DOORDASH": return "DOR";
            case "APPLE": return "APP";
            case "AMEX": return "AMX";
            case "DISNEY": return "DIS";
            case "HULU": return "HUL";
            case "NETFLIX": return "NET";
            default: return partnerName.substring(0, 3).toUpperCase();
        }
    }

    private static String getTypeChar(TicketType ticketType) {
        switch (ticketType) {
            case TECHNICAL: return "T";
            case GENERAL: return "G";
            case FILE_TRANSFER: return "F";
            default: return "G";
        }
    }
} 