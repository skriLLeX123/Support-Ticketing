package com.example.config;

import com.example.entity.*;
import com.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private SupportTicketRepository supportTicketRepository;

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private SupportGroupRepository supportGroupRepository;

    @Autowired
    private SupportMemberRepository supportMemberRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize sample data only if the tables are empty
        if (supportTicketRepository.count() == 0) {
            initializeSampleData();
        }
    }

    private void initializeSampleData() {
        // Create sample solutions
        Solution solution1 = new Solution("Database", "Resolve database connectivity problems and connection pool issues");
        Solution solution2 = new Solution("Upload", "Fix file upload functionality for files larger than 10MB with proper validation");
        Solution solution3 = new Solution("API", "Resolve API integration issues and implement proper rate limiting strategies");
        Solution solution4 = new Solution("Authentication", "Fix user login and authentication with enhanced security measures");
        Solution solution5 = new Solution("Performance", "Improve system performance through comprehensive monitoring and optimization");

        List<Solution> solutions = Arrays.asList(solution1, solution2, solution3, solution4, solution5);
        solutions.forEach(solutionRepository::save);

        // Create sample support groups
        SupportGroup group1 = new SupportGroup("Technical Support", "Handles technical issues and system problems");
        SupportGroup group2 = new SupportGroup("Customer Service", "Manages customer inquiries and general support");
        SupportGroup group3 = new SupportGroup("Infrastructure Team", "Handles infrastructure and deployment issues");

        List<SupportGroup> groups = Arrays.asList(group1, group2, group3);
        groups.forEach(supportGroupRepository::save);

        // Create sample support members
        SupportMember member1 = new SupportMember("John Smith", "john.smith@company.com", "+1-555-0101", group1);
        SupportMember member2 = new SupportMember("Sarah Johnson", "sarah.johnson@company.com", "+1-555-0102", group1);
        SupportMember member3 = new SupportMember("Mike Davis", "mike.davis@company.com", "+1-555-0103", group2);
        SupportMember member4 = new SupportMember("Lisa Wilson", "lisa.wilson@company.com", "+1-555-0104", group2);
        SupportMember member5 = new SupportMember("David Brown", "david.brown@company.com", "+1-555-0105", group3);

        List<SupportMember> members = Arrays.asList(member1, member2, member3, member4, member5);
        members.forEach(supportMemberRepository::save);

        // Create sample support tickets with specific partners and ticket ID pattern
        // Pattern: Three letter prefix + ticket type char (T/G/F) + 6 digit sequence
        
        // Amazon tickets
        SupportTicket ticket1 = createTicketWithId(
            "AMZ", "T", "000001",
            "API integration failure",
            "Third-party API integration is failing intermittently, causing data synchronization issues.",
            TicketType.TECHNICAL,
            "Amazon",
            "Amazon-Prod",
            solution1,
            Severity.HIGH,
            150,
            group1,
            member1
        );

        SupportTicket ticket2 = createTicketWithId(
            "AMZ", "F", "000001",
            "File upload failing",
            "File upload functionality is not working for files larger than 10MB. Users are unable to upload important documents.",
            TicketType.FILE_TRANSFER,
            "Amazon",
            "Amazon-Dev",
            solution2,
            Severity.MEDIUM,
            25,
            group1,
            member2
        );

        // DoorDash tickets
        SupportTicket ticket3 = createTicketWithId(
            "DOR", "T", "000001",
            "API rate limiting issue",
            "Third-party API integration is hitting rate limits frequently, causing service disruptions.",
            TicketType.TECHNICAL,
            "DoorDash",
            "DoorDash-API",
            solution3,
            Severity.CRITICAL,
            300,
            group3,
            member5
        );

        SupportTicket ticket4 = createTicketWithId(
            "DOR", "G", "000001",
            "User login problems",
            "Multiple users are reporting login failures and session timeouts.",
            TicketType.GENERAL,
            "DoorDash",
            "DoorDash-Prod",
            solution4,
            Severity.HIGH,
            75,
            group2,
            member3
        );

        // Apple tickets
        SupportTicket ticket5 = createTicketWithId(
            "APP", "T", "000001",
            "System performance degradation",
            "Overall system performance has degraded by 40% in the last week, affecting all users.",
            TicketType.TECHNICAL,
            "Apple",
            "Apple-Main",
            solution5,
            Severity.CRITICAL,
            500,
            group3,
            member5
        );

        SupportTicket ticket6 = createTicketWithId(
            "APP", "G", "000001",
            "General inquiry about features",
            "Customer wants to know about new features and pricing plans.",
            TicketType.GENERAL,
            "Apple",
            "Apple-Demo",
            solution1,
            Severity.LOW,
            1,
            group2,
            member4
        );

        // Amex tickets
        SupportTicket ticket7 = createTicketWithId(
            "AMX", "F", "000001",
            "Bulk file import issue",
            "Bulk file import is failing for CSV files with special characters.",
            TicketType.FILE_TRANSFER,
            "Amex",
            "Amex-Prod",
            solution2,
            Severity.MEDIUM,
            50,
            group1,
            member1
        );

        SupportTicket ticket8 = createTicketWithId(
            "AMX", "T", "000001",
            "Mobile app API errors",
            "Mobile application is experiencing API errors when syncing data.",
            TicketType.TECHNICAL,
            "Amex",
            "Amex-App",
            solution3,
            Severity.HIGH,
            200,
            group1,
            member2
        );

        // Disney tickets
        SupportTicket ticket9 = createTicketWithId(
            "DIS", "G", "000001",
            "Content streaming issues",
            "Users are experiencing buffering and quality issues with content streaming.",
            TicketType.GENERAL,
            "Disney",
            "Disney-Stream",
            solution4,
            Severity.MEDIUM,
            100,
            group2,
            member3
        );

        SupportTicket ticket10 = createTicketWithId(
            "DIS", "T", "000001",
            "Payment processing errors",
            "Payment processing is failing for international transactions.",
            TicketType.TECHNICAL,
            "Disney",
            "Disney-Pay",
            solution5,
            Severity.HIGH,
            150,
            group1,
            member1
        );

        // Hulu tickets
        SupportTicket ticket11 = createTicketWithId(
            "HUL", "F", "000001",
            "Video file processing issue",
            "Video file processing is taking longer than expected for HD content.",
            TicketType.FILE_TRANSFER,
            "Hulu",
            "Hulu-Content",
            solution2,
            Severity.MEDIUM,
            75,
            group1,
            member2
        );

        SupportTicket ticket12 = createTicketWithId(
            "HUL", "G", "000001",
            "Subscription management",
            "Users are having trouble managing their subscription preferences.",
            TicketType.GENERAL,
            "Hulu",
            "Hulu-Subs",
            solution4,
            Severity.LOW,
            25,
            group2,
            member4
        );

        // Netflix tickets
        SupportTicket ticket13 = createTicketWithId(
            "NET", "T", "000001",
            "Recommendation algorithm issue",
            "Content recommendation algorithm is not working properly for new users.",
            TicketType.TECHNICAL,
            "Netflix",
            "Netflix-AI",
            solution3,
            Severity.HIGH,
            300,
            group3,
            member5
        );

        SupportTicket ticket14 = createTicketWithId(
            "NET", "F", "000001",
            "Download functionality broken",
            "Download functionality is not working on mobile devices.",
            TicketType.FILE_TRANSFER,
            "Netflix",
            "Netflix-Mobile",
            solution2,
            Severity.MEDIUM,
            200,
            group1,
            member1
        );

        // Additional tickets to ensure we have more than 8
        SupportTicket ticket15 = createTicketWithId(
            "AMZ", "T", "000002",
            "Load balancer configuration issue",
            "Load balancer is not properly distributing traffic across servers.",
            TicketType.TECHNICAL,
            "Amazon",
            "Amazon-Infra",
            solution3,
            Severity.HIGH,
            180,
            group3,
            member5
        );

        SupportTicket ticket16 = createTicketWithId(
            "DOR", "G", "000002",
            "Billing system integration problem",
            "Billing system is not properly integrated with the main application.",
            TicketType.GENERAL,
            "DoorDash",
            "DoorDash-Billing",
            solution4,
            Severity.MEDIUM,
            45,
            group2,
            member3
        );

        SupportTicket ticket17 = createTicketWithId(
            "APP", "F", "000002",
            "Data export functionality broken",
            "Data export feature is not working for large datasets.",
            TicketType.FILE_TRANSFER,
            "Apple",
            "Apple-Data",
            solution2,
            Severity.MEDIUM,
            60,
            group1,
            member2
        );

        SupportTicket ticket18 = createTicketWithId(
            "AMX", "T", "000002",
            "Security certificate expiration",
            "SSL certificates are expiring soon and need renewal.",
            TicketType.TECHNICAL,
            "Amex",
            "Amex-Security",
            solution5,
            Severity.CRITICAL,
            400,
            group3,
            member5
        );

        SupportTicket ticket19 = createTicketWithId(
            "DIS", "G", "000002",
            "User feedback system down",
            "User feedback collection system is not functioning properly.",
            TicketType.GENERAL,
            "Disney",
            "Disney-Feedback",
            solution1,
            Severity.LOW,
            30,
            group2,
            member4
        );

        SupportTicket ticket20 = createTicketWithId(
            "HUL", "T", "000002",
            "CDN performance degradation",
            "Content Delivery Network performance has degraded significantly.",
            TicketType.TECHNICAL,
            "Hulu",
            "Hulu-CDN",
            solution3,
            Severity.HIGH,
            250,
            group3,
            member5
        );

        List<SupportTicket> tickets = Arrays.asList(
            ticket1, ticket2, ticket3, ticket4, ticket5, ticket6, ticket7, ticket8,
            ticket9, ticket10, ticket11, ticket12, ticket13, ticket14, ticket15,
            ticket16, ticket17, ticket18, ticket19, ticket20
        );
        tickets.forEach(supportTicketRepository::save);
    }

    private SupportTicket createTicketWithId(String prefix, String typeChar, String sequence,
                                           String title, String description, TicketType ticketType,
                                           String partnerName, String accountName, Solution solution,
                                           Severity severity, Integer customersImpacted,
                                           SupportGroup supportGroup, SupportMember assignee) {
        // Format: [3-letter prefix][1-letter type][6-digit sequence] - no delimiters
        String ticketId = prefix + typeChar + sequence;
        
        SupportTicket ticket = new SupportTicket(title, description, ticketType, partnerName, accountName, solution, severity, customersImpacted);
        ticket.setSupportGroup(supportGroup);
        ticket.setAssignee(assignee);
        
        // Use a simple random UUID to avoid any StringIndexOutOfBoundsException
        ticket.setTicketId(UUID.randomUUID());
        
        return ticket;
    }
} 