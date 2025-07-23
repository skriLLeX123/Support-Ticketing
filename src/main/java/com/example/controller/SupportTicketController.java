package com.example.controller;

import com.example.entity.SupportTicket;
import com.example.entity.TicketType;
import com.example.entity.Severity;
import com.example.service.SupportTicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "*")
public class SupportTicketController {

    @Autowired
    private SupportTicketService supportTicketService;

    /**
     * Get all tickets
     */
    @GetMapping
    public ResponseEntity<List<SupportTicket>> getAllTickets() {
        List<SupportTicket> tickets = supportTicketService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }

    /**
     * Get ticket by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<SupportTicket> getTicketById(@PathVariable UUID id) {
        Optional<SupportTicket> ticket = supportTicketService.getTicketById(id);
        return ticket.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new ticket
     */
    @PostMapping
    public ResponseEntity<SupportTicket> createTicket(@Valid @RequestBody SupportTicket ticket) {
        try {
            SupportTicket createdTicket = supportTicketService.createTicket(ticket);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Update an existing ticket
     */
    @PutMapping("/{id}")
    public ResponseEntity<SupportTicket> updateTicket(@PathVariable UUID id, 
                                                     @Valid @RequestBody SupportTicket ticketDetails) {
        try {
            SupportTicket updatedTicket = supportTicketService.updateTicket(id, ticketDetails);
            return ResponseEntity.ok(updatedTicket);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a ticket
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable UUID id) {
        try {
            supportTicketService.deleteTicket(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Search tickets by title
     */
    @GetMapping("/search/title")
    public ResponseEntity<List<SupportTicket>> searchTicketsByTitle(@RequestParam String title) {
        List<SupportTicket> tickets = supportTicketService.searchTicketsByTitle(title);
        return ResponseEntity.ok(tickets);
    }

    /**
     * Get tickets by type
     */
    @GetMapping("/type/{ticketType}")
    public ResponseEntity<List<SupportTicket>> getTicketsByType(@PathVariable TicketType ticketType) {
        List<SupportTicket> tickets = supportTicketService.getTicketsByType(ticketType);
        return ResponseEntity.ok(tickets);
    }

    /**
     * Get tickets by severity
     */
    @GetMapping("/severity/{severity}")
    public ResponseEntity<List<SupportTicket>> getTicketsBySeverity(@PathVariable Severity severity) {
        List<SupportTicket> tickets = supportTicketService.getTicketsBySeverity(severity);
        return ResponseEntity.ok(tickets);
    }

    /**
     * Get tickets by partner name
     */
    @GetMapping("/partner/{partnerName}")
    public ResponseEntity<List<SupportTicket>> getTicketsByPartnerName(@PathVariable String partnerName) {
        List<SupportTicket> tickets = supportTicketService.getTicketsByPartnerName(partnerName);
        return ResponseEntity.ok(tickets);
    }

    /**
     * Get tickets by account name
     */
    @GetMapping("/account/{accountName}")
    public ResponseEntity<List<SupportTicket>> getTicketsByAccountName(@PathVariable String accountName) {
        List<SupportTicket> tickets = supportTicketService.getTicketsByAccountName(accountName);
        return ResponseEntity.ok(tickets);
    }

    /**
     * Get tickets by solution
     */
    @GetMapping("/solution/{solutionId}")
    public ResponseEntity<List<SupportTicket>> getTicketsBySolution(@PathVariable UUID solutionId) {
        List<SupportTicket> tickets = supportTicketService.getTicketsBySolution(solutionId);
        return ResponseEntity.ok(tickets);
    }

    /**
     * Get tickets by support group
     */
    @GetMapping("/support-group/{supportGroupId}")
    public ResponseEntity<List<SupportTicket>> getTicketsBySupportGroup(@PathVariable UUID supportGroupId) {
        List<SupportTicket> tickets = supportTicketService.getTicketsBySupportGroup(supportGroupId);
        return ResponseEntity.ok(tickets);
    }

    /**
     * Get tickets by assignee
     */
    @GetMapping("/assignee/{assigneeId}")
    public ResponseEntity<List<SupportTicket>> getTicketsByAssignee(@PathVariable UUID assigneeId) {
        List<SupportTicket> tickets = supportTicketService.getTicketsByAssignee(assigneeId);
        return ResponseEntity.ok(tickets);
    }

    /**
     * Get tickets by assignee name
     */
    @GetMapping("/assignee/name/{assigneeName}")
    public ResponseEntity<List<SupportTicket>> getTicketsByAssigneeName(@PathVariable String assigneeName) {
        List<SupportTicket> tickets = supportTicketService.getTicketsByAssigneeName(assigneeName);
        return ResponseEntity.ok(tickets);
    }

    /**
     * Get tickets with high customer impact
     */
    @GetMapping("/high-impact")
    public ResponseEntity<List<SupportTicket>> getTicketsWithHighCustomerImpact(@RequestParam(defaultValue = "10") Integer threshold) {
        List<SupportTicket> tickets = supportTicketService.getTicketsWithHighCustomerImpact(threshold);
        return ResponseEntity.ok(tickets);
    }

    /**
     * Search tickets by multiple criteria
     */
    @GetMapping("/search/criteria")
    public ResponseEntity<List<SupportTicket>> searchTicketsByCriteria(
            @RequestParam(required = false) TicketType ticketType,
            @RequestParam(required = false) Severity severity,
            @RequestParam(required = false) String partnerName,
            @RequestParam(required = false) String accountName) {
        List<SupportTicket> tickets = supportTicketService.searchTicketsByCriteria(ticketType, severity, partnerName, accountName);
        return ResponseEntity.ok(tickets);
    }

    /**
     * Get ticket statistics
     */
    @GetMapping("/statistics")
    public ResponseEntity<SupportTicketService.TicketStatistics> getTicketStatistics() {
        SupportTicketService.TicketStatistics stats = supportTicketService.getTicketStatistics();
        return ResponseEntity.ok(stats);
    }
} 