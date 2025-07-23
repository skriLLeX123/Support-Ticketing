package com.example.repository;

import com.example.entity.SupportTicket;
import com.example.entity.TicketType;
import com.example.entity.Severity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SupportTicketRepository extends JpaRepository<SupportTicket, UUID> {

    /**
     * Find tickets by title containing (case-insensitive)
     */
    List<SupportTicket> findByTitleContainingIgnoreCase(String title);

    /**
     * Find tickets by ticket type
     */
    List<SupportTicket> findByTicketType(TicketType ticketType);

    /**
     * Find tickets by severity
     */
    List<SupportTicket> findBySeverity(Severity severity);

    /**
     * Find tickets by partner name
     */
    List<SupportTicket> findByPartnerNameContainingIgnoreCase(String partnerName);

    /**
     * Find tickets by account name
     */
    List<SupportTicket> findByAccountNameContainingIgnoreCase(String accountName);

    /**
     * Find tickets by solution
     */
    List<SupportTicket> findBySolution_SolutionId(UUID solutionId);

    /**
     * Find tickets by support group
     */
    List<SupportTicket> findBySupportGroup_SupportGroupId(UUID supportGroupId);

    /**
     * Find tickets by assignee
     */
    List<SupportTicket> findByAssignee_MemberId(UUID assigneeId);

    /**
     * Find tickets by assignee name
     */
    List<SupportTicket> findByAssignee_NameContainingIgnoreCase(String assigneeName);

    /**
     * Find tickets with customers impacted greater than
     */
    List<SupportTicket> findByCustomersImpactedGreaterThan(Integer customersImpacted);

    /**
     * Custom query to find tickets by multiple criteria
     */
    @Query("SELECT t FROM SupportTicket t WHERE " +
           "(:ticketType IS NULL OR t.ticketType = :ticketType) AND " +
           "(:severity IS NULL OR t.severity = :severity) AND " +
           "(:partnerName IS NULL OR LOWER(t.partnerName) LIKE LOWER(CONCAT('%', :partnerName, '%'))) AND " +
           "(:accountName IS NULL OR LOWER(t.accountName) LIKE LOWER(CONCAT('%', :accountName, '%')))")
    List<SupportTicket> findTicketsByCriteria(@Param("ticketType") TicketType ticketType,
                                             @Param("severity") Severity severity,
                                             @Param("partnerName") String partnerName,
                                             @Param("accountName") String accountName);

    /**
     * Count tickets by severity
     */
    long countBySeverity(Severity severity);

    /**
     * Count tickets by ticket type
     */
    long countByTicketType(TicketType ticketType);

    /**
     * Count tickets by support group
     */
    long countBySupportGroup_SupportGroupId(UUID supportGroupId);

    /**
     * Count tickets by assignee
     */
    long countByAssignee_MemberId(UUID assigneeId);
} 