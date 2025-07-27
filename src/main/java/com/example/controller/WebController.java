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
        List<SupportTicketDTO> tickets = supportTicketService.getAllTicketsAsDTOs();
        SupportTicketService.TicketStatistics stats = supportTicketService.getTicketStatistics();
        
        model.addAttribute("tickets", tickets);
        model.addAttribute("statistics", stats);
        return "dashboard";
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