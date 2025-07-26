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
            "Third-party API integration is failing intermittently, causing data synchronization issues between our e-commerce platform and Amazon's inventory management system. The integration was working properly until last week when we started experiencing timeout errors and connection drops. This is affecting our ability to sync product inventory, pricing updates, and order status changes in real-time. Our monitoring system shows that approximately 30% of API calls are failing with HTTP 500 errors, and the remaining calls are taking significantly longer than the expected response time of 2 seconds. We have already tried increasing the connection timeout values and implementing retry logic, but the issue persists. The problem appears to be related to the recent deployment of our new microservices architecture.",
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
            "File upload functionality is not working for files larger than 10MB. Users are unable to upload important documents including product catalogs, bulk inventory spreadsheets, and high-resolution product images. The upload process starts normally but fails at approximately 85% completion with a generic 'Upload Failed' error message. This issue affects our content management system where vendors need to upload large product catalogs and marketing materials. We have tested with various file types including PDF, Excel, CSV, and image files, and the problem is consistent across all formats. The server logs show that the upload is being terminated due to memory constraints in our application server. We have already increased the maximum file size limit in our web server configuration, but the issue persists. This is blocking several critical business processes that depend on large file uploads.",
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
            "Third-party API integration is hitting rate limits frequently, causing service disruptions in our food delivery platform. The DoorDash API is returning HTTP 429 (Too Many Requests) errors approximately 40% of the time during peak hours, which is preventing us from fetching real-time order status updates, restaurant availability, and delivery tracking information. This issue started occurring after we increased our order volume by 60% following a successful marketing campaign. Our current API call frequency is around 500 requests per minute, which exceeds the documented rate limit of 300 requests per minute. We have implemented exponential backoff and retry logic, but the delays are causing poor user experience with order status updates taking up to 5 minutes to refresh. The issue is particularly severe during lunch and dinner rush hours when our system needs to make the most API calls. We need to either optimize our API usage patterns or negotiate higher rate limits with DoorDash.",
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
            "Overall system performance has degraded by 40% in the last week, affecting all users of our enterprise software platform. Response times for database queries have increased from an average of 200ms to 800ms, and page load times have gone from 2 seconds to 8 seconds. The performance degradation is most noticeable during peak business hours when concurrent user sessions exceed 1000. Our monitoring tools indicate that the bottleneck is primarily in the database layer, where connection pooling is not working efficiently and queries are not being optimized properly. We have also noticed increased CPU usage on our application servers, reaching 85% during peak hours compared to the normal 40-50% range. The issue appears to be related to a recent database migration where we moved from MySQL 5.7 to MySQL 8.0, and some query optimizations were lost during the migration process. We need to investigate the query execution plans and potentially add database indexes to improve performance.",
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
            "Mobile application is experiencing API errors when syncing data with our backend services. Users are reporting that the app crashes when trying to refresh account information, transaction history, and payment status updates. The error logs show that the mobile app is receiving HTTP 500 errors from our REST API endpoints, specifically when calling the /api/v1/transactions and /api/v1/account endpoints. The issue appears to be related to a recent deployment where we updated the API response format to include additional fields, but the mobile app is still expecting the old format. This is causing JSON parsing errors in the mobile application. We have also noticed that the API is returning malformed JSON responses in some cases, which is causing the mobile app to crash with a 'JSONException: Unexpected token' error. The problem affects both iOS and Android versions of the app, and we have received over 200 crash reports in the last 24 hours. We need to either rollback the API changes or update the mobile app to handle the new response format.",
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
            "Content recommendation algorithm is not working properly for new users, resulting in poor user experience and decreased engagement metrics. New users are receiving generic recommendations instead of personalized content suggestions based on their viewing preferences and demographic information. The algorithm is failing to process user behavior data correctly, including watch history, ratings, and search patterns. Our analytics show that new user retention has dropped by 25% in the last two weeks, and average session duration has decreased from 45 minutes to 28 minutes. The machine learning model that powers our recommendation engine appears to be stuck in a local minimum, providing similar recommendations to all users regardless of their individual preferences. We have also noticed that the algorithm is not properly handling edge cases such as users who watch content in multiple languages or users with diverse genre preferences. The issue seems to be related to a recent update to our recommendation model where we introduced new features but didn't properly validate the model's performance on new user data.",
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