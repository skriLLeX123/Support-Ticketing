package com.example.service;

import com.example.dto.SupportTicketDTO;
import com.example.dto.SolutionDTO;
import com.example.entity.SupportTicket;
import com.example.entity.Solution;
import com.example.entity.Account;
import com.example.entity.Partner;
import com.example.entity.TicketType;
import com.example.entity.Severity;
import com.example.repository.SupportTicketRepository;
import com.example.repository.SolutionRepository;
import com.example.repository.AccountRepository;
import com.example.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SupportTicketService {

    @Autowired
    private SupportTicketRepository supportTicketRepository;

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    /**
     * Get all support tickets
     */
    public List<SupportTicket> getAllTickets() {
        return supportTicketRepository.findAll();
    }

    /**
     * Get all support tickets as DTOs (for JSON serialization)
     */
    public List<SupportTicketDTO> getAllTicketsAsDTOs() {
        List<SupportTicket> tickets = supportTicketRepository.findAll();
        return tickets.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convert SupportTicket entity to DTO
     */
    private SupportTicketDTO convertToDTO(SupportTicket ticket) {
        try {
            // Generate formatted ticket ID
            String formattedTicketId = formatTicketId(ticket.getPartnerName(), ticket.getTicketType(), 1);
            
            return new SupportTicketDTO(
                    ticket.getTicketId(),
                    formattedTicketId,
                    ticket.getTitle(),
                    ticket.getDescription(),
                    ticket.getTicketType(),
                    ticket.getSeverity(),
                    ticket.getPartnerName(),
                    ticket.getAccountName(),
                    ticket.getSolution() != null ? ticket.getSolution().getName() : null,
                    ticket.getSupportGroup() != null ? ticket.getSupportGroup().getName() : null,
                    ticket.getAssignee() != null ? ticket.getAssignee().getName() : null,
                    ticket.getCustomersImpacted(),
                    ticket.getCreatedAt(),
                    ticket.getLastUpdated()
            );
        } catch (Exception e) {
            // Fallback to basic DTO if formatting fails
            return new SupportTicketDTO(
                    ticket.getTicketId(),
                    "XXXG000001", // Fallback formatted ID
                    ticket.getTitle(),
                    ticket.getDescription(),
                    ticket.getTicketType(),
                    ticket.getSeverity(),
                    ticket.getPartnerName(),
                    ticket.getAccountName(),
                    ticket.getSolution() != null ? ticket.getSolution().getName() : null,
                    ticket.getSupportGroup() != null ? ticket.getSupportGroup().getName() : null,
                    ticket.getAssignee() != null ? ticket.getAssignee().getName() : null,
                    ticket.getCustomersImpacted(),
                    ticket.getCreatedAt(),
                    ticket.getLastUpdated()
            );
        }
    }

    /**
     * Helper method to format ticket ID (copied from WebController)
     */
    private String formatTicketId(String partnerName, TicketType ticketType, int sequence) {
        String prefix = getPartnerPrefix(partnerName);
        String typeChar = getTypeChar(ticketType);
        return String.format("%s%s%06d", prefix, typeChar, sequence);
    }

    private String getPartnerPrefix(String partnerName) {
        if (partnerName == null || partnerName.trim().isEmpty()) {
            return "XXX";
        }
        
        switch (partnerName.toUpperCase()) {
            case "AMAZON": return "AMZ";
            case "DOORDASH": return "DOR";
            case "APPLE": return "APP";
            case "AMEX": return "AMX";
            case "DISNEY": return "DIS";
            case "HULU": return "HUL";
            case "NETFLIX": return "NET";
            default: 
                if (partnerName.length() >= 3) {
                    return partnerName.substring(0, 3).toUpperCase();
                } else {
                    return partnerName.toUpperCase();
                }
        }
    }

    private String getTypeChar(TicketType ticketType) {
        if (ticketType == null) {
            return "G";
        }
        
        switch (ticketType) {
            case TECHNICAL: return "T";
            case GENERAL: return "G";
            case FILE_TRANSFER: return "F";
            default: return "G";
        }
    }

    /**
     * Get ticket by ID
     */
    public Optional<SupportTicket> getTicketById(UUID id) {
        return supportTicketRepository.findById(id);
    }

    /**
     * Create a new ticket
     */
    public SupportTicket createTicket(SupportTicket ticket) {
        return supportTicketRepository.save(ticket);
    }

    /**
     * Update an existing ticket
     */
    public SupportTicket updateTicket(UUID id, SupportTicket ticketDetails) {
        SupportTicket ticket = supportTicketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));

        ticket.setTitle(ticketDetails.getTitle());
        ticket.setDescription(ticketDetails.getDescription());
        ticket.setTicketType(ticketDetails.getTicketType());
        ticket.setPartnerName(ticketDetails.getPartnerName());
        ticket.setAccountName(ticketDetails.getAccountName());
        ticket.setSolution(ticketDetails.getSolution());
        ticket.setSeverity(ticketDetails.getSeverity());
        ticket.setCustomersImpacted(ticketDetails.getCustomersImpacted());
        ticket.setSupportGroup(ticketDetails.getSupportGroup());
        ticket.setAssignee(ticketDetails.getAssignee());

        return supportTicketRepository.save(ticket);
    }

    /**
     * Delete a ticket
     */
    public void deleteTicket(UUID id) {
        SupportTicket ticket = supportTicketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));
        supportTicketRepository.delete(ticket);
    }

    /**
     * Search tickets by title
     */
    public List<SupportTicket> searchTicketsByTitle(String title) {
        return supportTicketRepository.findByTitleContainingIgnoreCase(title);
    }

    /**
     * Get tickets by type
     */
    public List<SupportTicket> getTicketsByType(TicketType ticketType) {
        return supportTicketRepository.findByTicketType(ticketType);
    }

    /**
     * Get tickets by severity
     */
    public List<SupportTicket> getTicketsBySeverity(Severity severity) {
        return supportTicketRepository.findBySeverity(severity);
    }

    /**
     * Get tickets by partner name
     */
    public List<SupportTicket> getTicketsByPartnerName(String partnerName) {
        return supportTicketRepository.findByPartnerNameContainingIgnoreCase(partnerName);
    }

    /**
     * Get tickets by account name
     */
    public List<SupportTicket> getTicketsByAccountName(String accountName) {
        return supportTicketRepository.findByAccountNameContainingIgnoreCase(accountName);
    }

    /**
     * Get tickets by solution
     */
    public List<SupportTicket> getTicketsBySolution(UUID solutionId) {
        return supportTicketRepository.findBySolution_SolutionId(solutionId);
    }

    /**
     * Get tickets by support group
     */
    public List<SupportTicket> getTicketsBySupportGroup(UUID supportGroupId) {
        return supportTicketRepository.findBySupportGroup_SupportGroupId(supportGroupId);
    }

    /**
     * Get tickets by assignee
     */
    public List<SupportTicket> getTicketsByAssignee(UUID assigneeId) {
        return supportTicketRepository.findByAssignee_MemberId(assigneeId);
    }

    /**
     * Get tickets by assignee name
     */
    public List<SupportTicket> getTicketsByAssigneeName(String assigneeName) {
        return supportTicketRepository.findByAssignee_NameContainingIgnoreCase(assigneeName);
    }

    /**
     * Get tickets with high customer impact
     */
    public List<SupportTicket> getTicketsWithHighCustomerImpact(Integer threshold) {
        return supportTicketRepository.findByCustomersImpactedGreaterThan(threshold);
    }

    /**
     * Search tickets by multiple criteria
     */
    public List<SupportTicket> searchTicketsByCriteria(TicketType ticketType, Severity severity, 
                                                      String partnerName, String accountName) {
        return supportTicketRepository.findTicketsByCriteria(ticketType, severity, partnerName, accountName);
    }

    /**
     * Get all solutions
     */
    public List<Solution> getAllSolutions() {
        return solutionRepository.findAll();
    }

    /**
     * Get all solutions as DTOs with hierarchical data (Account and Partner information)
     */
    public List<SolutionDTO> getAllSolutionsAsDTOs() {
        List<Solution> solutions = solutionRepository.findAll();
        return solutions.stream()
                .map(this::convertSolutionToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convert Solution entity to DTO with hierarchical data
     */
    private SolutionDTO convertSolutionToDTO(Solution solution) {
        Account account = solution.getAccount();
        Partner partner = account != null ? account.getPartner() : null;
        
        return new SolutionDTO(
                solution.getSolutionId(),
                solution.getName(),
                solution.getDescription(),
                solution.getCreatedAt(),
                solution.getLastUpdated(),
                account != null ? account.getAccountId() : null,
                account != null ? account.getName() : null,
                partner != null ? partner.getPartnerId() : null,
                partner != null ? partner.getName() : null,
                partner != null ? partner.getLogoUrl() : null,
                partner != null ? partner.getMemberCount() : null,
                partner != null ? partner.getApiCount() : null
        );
    }

    /**
     * Get ticket statistics
     */
    public TicketStatistics getTicketStatistics() {
        TicketStatistics stats = new TicketStatistics();
        stats.setTotalTickets(supportTicketRepository.count());
        stats.setOpenTickets(0); // No status field in entity
        stats.setResolvedTickets(0); // No status field in entity
        stats.setTotalSolutions(solutionRepository.count());
        stats.setCriticalTickets(supportTicketRepository.countBySeverity(Severity.CRITICAL));
        stats.setHighTickets(supportTicketRepository.countBySeverity(Severity.HIGH));
        stats.setMediumTickets(supportTicketRepository.countBySeverity(Severity.MEDIUM));
        stats.setLowTickets(supportTicketRepository.countBySeverity(Severity.LOW));
        stats.setGeneralTickets(supportTicketRepository.countByTicketType(TicketType.GENERAL));
        stats.setTechnicalTickets(supportTicketRepository.countByTicketType(TicketType.TECHNICAL));
        stats.setFileTransferTickets(supportTicketRepository.countByTicketType(TicketType.FILE_TRANSFER));
        return stats;
    }

    /**
     * Inner class for ticket statistics
     */
    public static class TicketStatistics {
        private long totalTickets;
        private long openTickets;
        private long resolvedTickets;
        private long totalSolutions;
        private long criticalTickets;
        private long highTickets;
        private long mediumTickets;
        private long lowTickets;
        private long generalTickets;
        private long technicalTickets;
        private long fileTransferTickets;

        // Getters and Setters
        public long getTotalTickets() { return totalTickets; }
        public void setTotalTickets(long totalTickets) { this.totalTickets = totalTickets; }

        public long getOpenTickets() { return openTickets; }
        public void setOpenTickets(long openTickets) { this.openTickets = openTickets; }

        public long getResolvedTickets() { return resolvedTickets; }
        public void setResolvedTickets(long resolvedTickets) { this.resolvedTickets = resolvedTickets; }

        public long getTotalSolutions() { return totalSolutions; }
        public void setTotalSolutions(long totalSolutions) { this.totalSolutions = totalSolutions; }

        public long getCriticalTickets() { return criticalTickets; }
        public void setCriticalTickets(long criticalTickets) { this.criticalTickets = criticalTickets; }

        public long getHighTickets() { return highTickets; }
        public void setHighTickets(long highTickets) { this.highTickets = highTickets; }

        public long getMediumTickets() { return mediumTickets; }
        public void setMediumTickets(long mediumTickets) { this.mediumTickets = mediumTickets; }

        public long getLowTickets() { return lowTickets; }
        public void setLowTickets(long lowTickets) { this.lowTickets = lowTickets; }

        public long getGeneralTickets() { return generalTickets; }
        public void setGeneralTickets(long generalTickets) { this.generalTickets = generalTickets; }

        public long getTechnicalTickets() { return technicalTickets; }
        public void setTechnicalTickets(long technicalTickets) { this.technicalTickets = technicalTickets; }

        public long getFileTransferTickets() { return fileTransferTickets; }
        public void setFileTransferTickets(long fileTransferTickets) { this.fileTransferTickets = fileTransferTickets; }
    }
} 