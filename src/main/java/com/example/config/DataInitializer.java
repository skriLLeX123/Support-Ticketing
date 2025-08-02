package com.example.config;

import com.example.entity.*;
import com.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
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

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private EnvironmentRepository environmentRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("DataInitializer starting...");
        
        // Initialize sample data only if the tables are empty
        if (supportTicketRepository.count() == 0) {
            System.out.println("Initializing sample data...");
            initializeSampleData();
        }
        
        // Always associate environments with solutions (even for existing data)
        System.out.println("Calling associateEnvironmentsWithSolutions...");
        // associateEnvironmentsWithSolutions(); // Disabled as per edit hint
        System.out.println("DataInitializer completed.");
    }
                               
    private void initializeSampleData() {
        // Create sample partners
        Partner amazonPartner = new Partner("Amazon", "E-commerce and cloud services partner");
        amazonPartner.setLogoUrl("https://logo.clearbit.com/amazon.com");
        amazonPartner.setApiCount(45);
        amazonPartner.setMemberCount(12);
        partnerRepository.save(amazonPartner);

        Partner doordashPartner = new Partner("DoorDash", "Food delivery and logistics partner");
        doordashPartner.setLogoUrl("https://logo.clearbit.com/doordash.com");
        doordashPartner.setApiCount(23);
        doordashPartner.setMemberCount(8);
        partnerRepository.save(doordashPartner);

        Partner uberPartner = new Partner("Uber", "Ride-sharing and mobility partner");
        uberPartner.setLogoUrl("https://logo.clearbit.com/uber.com");
        uberPartner.setApiCount(34);
        uberPartner.setMemberCount(15);
        partnerRepository.save(uberPartner);

        Partner disneyPartner = new Partner("Disney", "Entertainment and media partner");
        disneyPartner.setLogoUrl("https://logo.clearbit.com/disney.com");
        disneyPartner.setApiCount(67);
        disneyPartner.setMemberCount(20);
        partnerRepository.save(disneyPartner);

        Partner huluPartner = new Partner("Hulu", "Streaming and content partner");
        huluPartner.setLogoUrl("https://logo.clearbit.com/hulu.com");
        huluPartner.setApiCount(28);
        huluPartner.setMemberCount(10);
        partnerRepository.save(huluPartner);

        Partner netflixPartner = new Partner("Netflix", "Streaming and entertainment partner");
        netflixPartner.setLogoUrl("https://logo.clearbit.com/netflix.com");
        netflixPartner.setApiCount(89);
        partnerRepository.save(netflixPartner);

        // Create sample accounts
        Account amazonProdAccount = new Account("Amazon-Prod", "Production environment for Amazon integration", amazonPartner);
        Account amazonDevAccount = new Account("Amazon-Dev", "Development environment for Amazon integration", amazonPartner);
        accountRepository.save(amazonProdAccount);
        accountRepository.save(amazonDevAccount);

        Account doordashMainAccount = new Account("DoorDash-Main", "Main production account for DoorDash services", doordashPartner);
        accountRepository.save(doordashMainAccount);

        Account uberEatsAccount = new Account("Uber-Eats", "Uber Eats delivery platform integration", uberPartner);
        accountRepository.save(uberEatsAccount);

        // Disney accounts
        Account disneyStreamAccount = new Account("Disney-Stream", "Disney+ streaming platform integration", disneyPartner);
        Account disneyContentAccount = new Account("Disney-Content", "Disney content management and distribution", disneyPartner);
        accountRepository.save(disneyStreamAccount);
        accountRepository.save(disneyContentAccount);

        // Hulu accounts
        Account huluStreamAccount = new Account("Hulu-Stream", "Hulu streaming platform integration", huluPartner);
        Account huluContentAccount = new Account("Hulu-Content", "Hulu content management and analytics", huluPartner);
        accountRepository.save(huluStreamAccount);
        accountRepository.save(huluContentAccount);

        // Netflix accounts
        Account netflixStreamAccount = new Account("Netflix-Stream", "Netflix streaming platform integration", netflixPartner);
        Account netflixAIAccount = new Account("Netflix-AI", "Netflix recommendation and AI services", netflixPartner);
        accountRepository.save(netflixStreamAccount);
        accountRepository.save(netflixAIAccount);

        // Create sample solutions with accounts
        Solution solution1 = new Solution("Database", "Resolve database connectivity problems and connection pool issues", amazonProdAccount);
        Solution solution2 = new Solution("Upload", "Fix file upload functionality for files larger than 10MB with proper validation", amazonDevAccount);
        Solution solution3 = new Solution("API", "Resolve API integration issues and implement proper rate limiting strategies", doordashMainAccount);
        Solution solution4 = new Solution("Authentication", "Fix user login and authentication with enhanced security measures", uberEatsAccount);
        Solution solution5 = new Solution("Performance", "Improve system performance through comprehensive monitoring and optimization", amazonProdAccount);

        // Disney solutions
        Solution disneyStreaming = new Solution("Streaming", "Disney+ streaming platform with DRM protection and content delivery", disneyStreamAccount);
        Solution disneyContent = new Solution("Content Management", "Disney content management system with metadata and analytics", disneyContentAccount);
        Solution disneyDRM = new Solution("DRM Protection", "Digital rights management for Disney content with encryption", disneyContentAccount);

        // Hulu solutions
        Solution huluStreaming = new Solution("Video Processing", "Hulu video processing pipeline with transcoding and optimization", huluStreamAccount);
        Solution huluAnalytics = new Solution("Analytics", "Hulu analytics platform with viewer insights and recommendations", huluContentAccount);
        Solution huluCDN = new Solution("Hulu Global CDN", "Hulu content delivery network with global edge locations", huluStreamAccount);

        // Netflix solutions
        Solution netflixAI = new Solution("Recommendation Engine", "Netflix AI-powered recommendation system with machine learning", netflixAIAccount);
        Solution netflixStreaming = new Solution("Streaming Platform", "Netflix streaming platform with adaptive bitrate and quality", netflixStreamAccount);
        Solution netflixCDN = new Solution("Netflix Global CDN", "Netflix content delivery network with global optimization", netflixStreamAccount);

        List<Solution> solutions = Arrays.asList(
            solution1, solution2, solution3, solution4, solution5,
            disneyStreaming, disneyContent, disneyDRM,
            huluStreaming, huluAnalytics, huluCDN,
            netflixStreaming, netflixAI, netflixCDN
        );
        solutions.forEach(solutionRepository::save);

        // Note: Environment associations are now handled by NestedDataInitializer
        // which uses the proper SolutionEnvironment and EnvApi structure
        System.out.println("DataInitializer: Skipping old environment associations - use NestedDataInitializer instead");

        // Create sample support groups
        SupportGroup group1 = new SupportGroup("Technical Support", "Handles technical issues and system problems");
        SupportGroup group2 = new SupportGroup("Customer Service", "Manages customer inquiries and general support");
        SupportGroup group3 = new SupportGroup("Infrastructure Team", "Manages infrastructure and deployment issues");

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

        // Initialize tickets last
        initializeTickets();
    }

    private void initializePartners() {
        System.out.println("Initializing Partners...");
        Partner amazonPartner = new Partner("Amazon", "E-commerce and cloud services partner");
        amazonPartner.setLogoUrl("https://logo.clearbit.com/amazon.com");
        amazonPartner.setMemberCount(1250);
        amazonPartner.setApiCount(45);
        partnerRepository.save(amazonPartner);

        Partner doordashPartner = new Partner("DoorDash", "Food delivery and logistics partner");
        doordashPartner.setLogoUrl("https://logo.clearbit.com/doordash.com");
        doordashPartner.setMemberCount(850);
        doordashPartner.setApiCount(32);
        partnerRepository.save(doordashPartner);

        Partner uberPartner = new Partner("Uber", "Transportation and delivery partner");
        uberPartner.setLogoUrl("https://logo.clearbit.com/uber.com");
        uberPartner.setMemberCount(2100);
        uberPartner.setApiCount(67);
        partnerRepository.save(uberPartner);

        Partner disneyPartner = new Partner("Disney", "Entertainment and media streaming partner");
        disneyPartner.setLogoUrl("https://logo.clearbit.com/disney.com");
        disneyPartner.setMemberCount(1800);
        disneyPartner.setApiCount(28);
        partnerRepository.save(disneyPartner);

        Partner huluPartner = new Partner("Hulu", "Streaming entertainment platform partner");
        huluPartner.setLogoUrl("https://logo.clearbit.com/hulu.com");
        huluPartner.setMemberCount(950);
        huluPartner.setApiCount(22);
        partnerRepository.save(huluPartner);

        Partner netflixPartner = new Partner("Netflix", "Global streaming entertainment partner");
        netflixPartner.setLogoUrl("https://logo.clearbit.com/netflix.com");
        netflixPartner.setMemberCount(3200);
        netflixPartner.setApiCount(89);
        partnerRepository.save(netflixPartner);
        System.out.println("Partners initialized.");
    }

    private void initializeAccounts() {
        System.out.println("Initializing Accounts...");
        Account amazonProdAccount = new Account("Amazon-Prod", "Production environment for Amazon integration", partnerRepository.findByName("Amazon").orElse(null));
        Account amazonDevAccount = new Account("Amazon-Dev", "Development environment for Amazon integration", partnerRepository.findByName("Amazon").orElse(null));
        accountRepository.save(amazonProdAccount);
        accountRepository.save(amazonDevAccount);

        Account doordashMainAccount = new Account("DoorDash-Main", "Main production account for DoorDash services", partnerRepository.findByName("DoorDash").orElse(null));
        accountRepository.save(doordashMainAccount);

        Account uberEatsAccount = new Account("Uber-Eats", "Uber Eats delivery platform integration", partnerRepository.findByName("Uber").orElse(null));
        accountRepository.save(uberEatsAccount);

        // Disney accounts
        Account disneyStreamAccount = new Account("Disney-Stream", "Disney+ streaming platform integration", partnerRepository.findByName("Disney").orElse(null));
        Account disneyContentAccount = new Account("Disney-Content", "Disney content management and distribution", partnerRepository.findByName("Disney").orElse(null));
        accountRepository.save(disneyStreamAccount);
        accountRepository.save(disneyContentAccount);

        // Hulu accounts
        Account huluStreamAccount = new Account("Hulu-Stream", "Hulu streaming platform integration", partnerRepository.findByName("Hulu").orElse(null));
        Account huluContentAccount = new Account("Hulu-Content", "Hulu content management and analytics", partnerRepository.findByName("Hulu").orElse(null));
        accountRepository.save(huluStreamAccount);
        accountRepository.save(huluContentAccount);

        // Netflix accounts
        Account netflixStreamAccount = new Account("Netflix-Stream", "Netflix streaming platform integration", partnerRepository.findByName("Netflix").orElse(null));
        Account netflixAIAccount = new Account("Netflix-AI", "Netflix recommendation and AI services", partnerRepository.findByName("Netflix").orElse(null));
        accountRepository.save(netflixStreamAccount);
        accountRepository.save(netflixAIAccount);
        System.out.println("Accounts initialized.");
    }

    private void initializeSolutions() {
        System.out.println("Initializing Solutions...");
        Solution solution1 = new Solution("Database", "Resolve database connectivity problems and connection pool issues", accountRepository.findByName("Amazon-Prod").orElse(null));
        Solution solution2 = new Solution("Upload", "Fix file upload functionality for files larger than 10MB with proper validation", accountRepository.findByName("Amazon-Dev").orElse(null));
        Solution solution3 = new Solution("API", "Resolve API integration issues and implement proper rate limiting strategies", accountRepository.findByName("DoorDash-Main").orElse(null));
        Solution solution4 = new Solution("Authentication", "Fix user login and authentication with enhanced security measures", accountRepository.findByName("Uber-Eats").orElse(null));
        Solution solution5 = new Solution("Performance", "Improve system performance through comprehensive monitoring and optimization", accountRepository.findByName("Amazon-Prod").orElse(null));

        // Disney solutions
        Solution solution6 = new Solution("Streaming", "Optimize video streaming performance and reduce buffering issues", accountRepository.findByName("Disney-Stream").orElse(null));
        Solution solution7 = new Solution("Content Delivery", "Improve content delivery network and reduce latency for global users", accountRepository.findByName("Disney-Content").orElse(null));
        Solution solution8 = new Solution("DRM Protection", "Enhance digital rights management and content security measures", accountRepository.findByName("Disney-Stream").orElse(null));

        // Hulu solutions
        Solution solution9 = new Solution("Video Processing", "Optimize video encoding and transcoding for multiple quality levels", accountRepository.findByName("Hulu-Stream").orElse(null));
        Solution solution10 = new Solution("Analytics", "Improve user behavior analytics and content recommendation accuracy", accountRepository.findByName("Hulu-Content").orElse(null));
        Solution solution11 = new Solution("Ad Integration", "Enhance advertisement integration and targeting capabilities", accountRepository.findByName("Hulu-Stream").orElse(null));

        // Netflix solutions
        Solution solution12 = new Solution("Recommendation Engine", "Optimize machine learning algorithms for personalized content recommendations", accountRepository.findByName("Netflix-AI").orElse(null));
        Solution solution13 = new Solution("Netflix Global CDN", "Improve content delivery network performance across multiple regions", accountRepository.findByName("Netflix-Stream").orElse(null));
        Solution solution14 = new Solution("A/B Testing", "Enhance A/B testing framework for content optimization and user experience", accountRepository.findByName("Netflix-AI").orElse(null));
        Solution solution15 = new Solution("Subtitle Processing", "Improve subtitle and closed caption processing for multiple languages", accountRepository.findByName("Netflix-Stream").orElse(null));

        List<Solution> solutions = Arrays.asList(
            solution1, solution2, solution3, solution4, solution5,
            solution6, solution7, solution8, solution9, solution10,
            solution11, solution12, solution13, solution14, solution15
        );
        solutions.forEach(solutionRepository::save);
        System.out.println("Solutions initialized.");
    }

    private void initializeSupportGroups() {
        System.out.println("Initializing Support Groups...");
        SupportGroup group1 = new SupportGroup("Technical Support", "Handles technical issues and system problems");
        SupportGroup group2 = new SupportGroup("Customer Service", "Manages customer inquiries and general support");
        SupportGroup group3 = new SupportGroup("Infrastructure Team", "Handles infrastructure and deployment issues");

        List<SupportGroup> groups = Arrays.asList(group1, group2, group3);
        groups.forEach(supportGroupRepository::save);
        System.out.println("Support Groups initialized.");
    }

    private void initializeSupportMembers() {
        System.out.println("Initializing Support Members...");
        SupportMember member1 = new SupportMember("John Smith", "john.smith@company.com", "+1-555-0101", supportGroupRepository.findByName("Technical Support").orElse(null));
        SupportMember member2 = new SupportMember("Sarah Johnson", "sarah.johnson@company.com", "+1-555-0102", supportGroupRepository.findByName("Technical Support").orElse(null));
        SupportMember member3 = new SupportMember("Mike Davis", "mike.davis@company.com", "+1-555-0103", supportGroupRepository.findByName("Customer Service").orElse(null));
        SupportMember member4 = new SupportMember("Lisa Wilson", "lisa.wilson@company.com", "+1-555-0104", supportGroupRepository.findByName("Customer Service").orElse(null));
        SupportMember member5 = new SupportMember("David Brown", "david.brown@company.com", "+1-555-0105", supportGroupRepository.findByName("Infrastructure Team").orElse(null));

        List<SupportMember> members = Arrays.asList(member1, member2, member3, member4, member5);
        members.forEach(supportMemberRepository::save);
        System.out.println("Support Members initialized.");
    }

    private void initializeEnvironments() {
        System.out.println("Initializing Environments...");
        Environment prod = new Environment(Environment.EnvironmentType.PRODUCTION, "Production", "Production environment");
        Environment dev = new Environment(Environment.EnvironmentType.DEVELOPMENT, "Development", "Development environment");
        Environment uat = new Environment(Environment.EnvironmentType.UAT, "UAT", "User Acceptance Testing environment");
        Environment sandbox = new Environment(Environment.EnvironmentType.SANDBOX, "Sandbox", "Sandbox environment");

        List<Environment> environments = Arrays.asList(prod, dev, uat, sandbox);
        environments.forEach(environmentRepository::save);
        System.out.println("Environments initialized.");
    }

    private void initializeTickets() {
        System.out.println("Initializing Support Tickets...");
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
            solutionRepository.findByName("Database").orElse(null),
            Severity.HIGH,
            150,
            supportGroupRepository.findByName("Technical Support").orElse(null),
            supportMemberRepository.findByName("John Smith").orElse(null)
        );

        SupportTicket ticket2 = createTicketWithId(
            "AMZ", "F", "000001",
            "File upload failing",
            "File upload functionality is not working for files larger than 10MB. Users are unable to upload important documents including product catalogs, bulk inventory spreadsheets, and high-resolution product images. The upload process starts normally but fails at approximately 85% completion with a generic 'Upload Failed' error message. This issue affects our content management system where vendors need to upload large product catalogs and marketing materials. We have tested with various file types including PDF, Excel, CSV, and image files, and the problem is consistent across all formats. The server logs show that the upload is being terminated due to memory constraints in our application server. We have already increased the maximum file size limit in our web server configuration, but the issue persists. This is blocking several critical business processes that depend on large file uploads.",
            TicketType.FILE_TRANSFER,
            "Amazon",
            "Amazon-Dev",
            solutionRepository.findByName("Upload").orElse(null),
            Severity.MEDIUM,
            25,
            supportGroupRepository.findByName("Technical Support").orElse(null),
            supportMemberRepository.findByName("Sarah Johnson").orElse(null)
        );

        // DoorDash tickets
        SupportTicket ticket3 = createTicketWithId(
            "DOR", "T", "000001",
            "API rate limiting issue",
            "Third-party API integration is hitting rate limits frequently, causing service disruptions in our food delivery platform. The DoorDash API is returning HTTP 429 (Too Many Requests) errors approximately 40% of the time during peak hours, which is preventing us from fetching real-time order status updates, restaurant availability, and delivery tracking information. This issue started occurring after we increased our order volume by 60% following a successful marketing campaign. Our current API call frequency is around 500 requests per minute, which exceeds the documented rate limit of 300 requests per minute. We have implemented exponential backoff and retry logic, but the delays are causing poor user experience with order status updates taking up to 5 minutes to refresh. The issue is particularly severe during lunch and dinner rush hours when our system needs to make the most API calls. We need to either optimize our API usage patterns or negotiate higher rate limits with DoorDash.",
            TicketType.TECHNICAL,
            "DoorDash",
            "DoorDash-API",
            solutionRepository.findByName("API").orElse(null),
            Severity.CRITICAL,
            300,
            supportGroupRepository.findByName("Technical Support").orElse(null),
            supportMemberRepository.findByName("Mike Davis").orElse(null)
        );

        SupportTicket ticket4 = createTicketWithId(
            "DOR", "G", "000001",
            "User login problems",
            "Multiple users are reporting login failures and session timeouts.",
            TicketType.GENERAL,
            "DoorDash",
            "DoorDash-Prod",
            solutionRepository.findByName("Authentication").orElse(null),
            Severity.HIGH,
            75,
            supportGroupRepository.findByName("Customer Service").orElse(null),
            supportMemberRepository.findByName("Lisa Wilson").orElse(null)
        );

        // Apple tickets
        SupportTicket ticket5 = createTicketWithId(
            "APP", "T", "000001",
            "System performance degradation",
            "Overall system performance has degraded by 40% in the last week, affecting all users of our enterprise software platform. Response times for database queries have increased from an average of 200ms to 800ms, and page load times have gone from 2 seconds to 8 seconds. The performance degradation is most noticeable during peak business hours when concurrent user sessions exceed 1000. Our monitoring tools indicate that the bottleneck is primarily in the database layer, where connection pooling is not working efficiently and queries are not being optimized properly. We have also noticed increased CPU usage on our application servers, reaching 85% during peak hours compared to the normal 40-50% range. The issue appears to be related to a recent database migration where we moved from MySQL 5.7 to MySQL 8.0, and some query optimizations were lost during the migration process. We need to investigate the query execution plans and potentially add database indexes to improve performance.",
            TicketType.TECHNICAL,
            "Apple",
            "Apple-Main",
            solutionRepository.findByName("Performance").orElse(null),
            Severity.CRITICAL,
            500,
            supportGroupRepository.findByName("Infrastructure Team").orElse(null),
            supportMemberRepository.findByName("David Brown").orElse(null)
        );

        SupportTicket ticket6 = createTicketWithId(
            "APP", "G", "000001",
            "General inquiry about features",
            "Customer wants to know about new features and pricing plans.",
            TicketType.GENERAL,
            "Apple",
            "Apple-Demo",
            solutionRepository.findByName("Database").orElse(null),
            Severity.LOW,
            1,
            supportGroupRepository.findByName("Customer Service").orElse(null),
            supportMemberRepository.findByName("Lisa Wilson").orElse(null)
        );

        // Amex tickets
        SupportTicket ticket7 = createTicketWithId(
            "AMX", "F", "000001",
            "Bulk file import issue",
            "Bulk file import is failing for CSV files with special characters.",
            TicketType.FILE_TRANSFER,
            "Amex",
            "Amex-Prod",
            solutionRepository.findByName("Upload").orElse(null),
            Severity.MEDIUM,
            50,
            supportGroupRepository.findByName("Technical Support").orElse(null),
            supportMemberRepository.findByName("John Smith").orElse(null)
        );

        SupportTicket ticket8 = createTicketWithId(
            "AMX", "T", "000001",
            "Mobile app API errors",
            "Mobile application is experiencing API errors when syncing data with our backend services. Users are reporting that the app crashes when trying to refresh account information, transaction history, and payment status updates. The error logs show that the mobile app is receiving HTTP 500 errors from our REST API endpoints, specifically when calling the /api/v1/transactions and /api/v1/account endpoints. The issue appears to be related to a recent deployment where we updated the API response format to include additional fields, but the mobile app is still expecting the old format. This is causing JSON parsing errors in the mobile application. We have also noticed that the API is returning malformed JSON responses in some cases, which is causing the mobile app to crash with a 'JSONException: Unexpected token' error. The problem affects both iOS and Android versions of the app, and we have received over 200 crash reports in the last 24 hours. We need to either rollback the API changes or update the mobile app to handle the new response format.",
            TicketType.TECHNICAL,
            "Amex",
            "Amex-App",
            solutionRepository.findByName("API").orElse(null),
            Severity.HIGH,
            200,
            supportGroupRepository.findByName("Technical Support").orElse(null),
            supportMemberRepository.findByName("Sarah Johnson").orElse(null)
        );

        // Disney tickets
        SupportTicket ticket9 = createTicketWithId(
            "DIS", "G", "000001",
            "Content streaming issues",
            "Users are experiencing buffering and quality issues with content streaming.",
            TicketType.GENERAL,
            "Disney",
            "Disney-Stream",
            solutionRepository.findByName("Streaming").orElse(null),
            Severity.MEDIUM,
            100,
            supportGroupRepository.findByName("Customer Service").orElse(null),
            supportMemberRepository.findByName("Mike Davis").orElse(null)
        );

        SupportTicket ticket10 = createTicketWithId(
            "DIS", "T", "000001",
            "Payment processing errors",
            "Payment processing is failing for international transactions.",
            TicketType.TECHNICAL,
            "Disney",
            "Disney-Pay",
            solutionRepository.findByName("Performance").orElse(null),
            Severity.HIGH,
            150,
            supportGroupRepository.findByName("Technical Support").orElse(null),
            supportMemberRepository.findByName("John Smith").orElse(null)
        );

        // Hulu tickets
        SupportTicket ticket11 = createTicketWithId(
            "HUL", "F", "000001",
            "Video file processing issue",
            "Video file processing is taking longer than expected for HD content.",
            TicketType.FILE_TRANSFER,
            "Hulu",
            "Hulu-Content",
            solutionRepository.findByName("Video Processing").orElse(null),
            Severity.MEDIUM,
            75,
            supportGroupRepository.findByName("Technical Support").orElse(null),
            supportMemberRepository.findByName("Sarah Johnson").orElse(null)
        );

        SupportTicket ticket12 = createTicketWithId(
            "HUL", "G", "000001",
            "Subscription management",
            "Users are having trouble managing their subscription preferences.",
            TicketType.GENERAL,
            "Hulu",
            "Hulu-Subs",
            solutionRepository.findByName("Recommendation Engine").orElse(null),
            Severity.LOW,
            25,
            supportGroupRepository.findByName("Customer Service").orElse(null),
            supportMemberRepository.findByName("Lisa Wilson").orElse(null)
        );

        // Netflix tickets
        SupportTicket ticket13 = createTicketWithId(
            "NET", "T", "000001",
            "Recommendation algorithm issue",
            "Content recommendation algorithm is not working properly for new users, resulting in poor user experience and decreased engagement metrics. New users are receiving generic recommendations instead of personalized content suggestions based on their viewing preferences and demographic information. The algorithm is failing to process user behavior data correctly, including watch history, ratings, and search patterns. Our analytics show that new user retention has dropped by 25% in the last two weeks, and average session duration has decreased from 45 minutes to 28 minutes. The machine learning model that powers our recommendation engine appears to be stuck in a local minimum, providing similar recommendations to all users regardless of their individual preferences. We have also noticed that the algorithm is not properly handling edge cases such as users who watch content in multiple languages or users with diverse genre preferences. The issue seems to be related to a recent update to our recommendation model where we introduced new features but didn't properly validate the model's performance on new user data.",
            TicketType.TECHNICAL,
            "Netflix",
            "Netflix-AI",
            solutionRepository.findByName("Recommendation Engine").orElse(null),
            Severity.HIGH,
            300,
            supportGroupRepository.findByName("Infrastructure Team").orElse(null),
            supportMemberRepository.findByName("David Brown").orElse(null)
        );

        SupportTicket ticket14 = createTicketWithId(
            "NET", "F", "000001",
            "Download functionality broken",
            "Download functionality is not working on mobile devices.",
            TicketType.FILE_TRANSFER,
            "Netflix",
            "Netflix-Mobile",
            solutionRepository.findByName("Netflix Global CDN").orElse(null),
            Severity.MEDIUM,
            200,
            supportGroupRepository.findByName("Technical Support").orElse(null),
            supportMemberRepository.findByName("John Smith").orElse(null)
        );

        // Additional tickets to ensure we have more than 8
        SupportTicket ticket15 = createTicketWithId(
            "AMZ", "T", "000002",
            "Load balancer configuration issue",
            "Load balancer is not properly distributing traffic across servers.",
            TicketType.TECHNICAL,
            "Amazon",
            "Amazon-Infra",
            solutionRepository.findByName("API").orElse(null),
            Severity.HIGH,
            180,
            supportGroupRepository.findByName("Infrastructure Team").orElse(null),
            supportMemberRepository.findByName("David Brown").orElse(null)
        );

        SupportTicket ticket16 = createTicketWithId(
            "DOR", "G", "000002",
            "Billing system integration problem",
            "Billing system is not properly integrated with the main application.",
            TicketType.GENERAL,
            "DoorDash",
            "DoorDash-Billing",
            solutionRepository.findByName("Authentication").orElse(null),
            Severity.MEDIUM,
            45,
            supportGroupRepository.findByName("Customer Service").orElse(null),
            supportMemberRepository.findByName("Mike Davis").orElse(null)
        );

        SupportTicket ticket17 = createTicketWithId(
            "APP", "F", "000002",
            "Data export functionality broken",
            "Data export feature is not working for large datasets.",
            TicketType.FILE_TRANSFER,
            "Apple",
            "Apple-Data",
            solutionRepository.findByName("Upload").orElse(null),
            Severity.MEDIUM,
            60,
            supportGroupRepository.findByName("Technical Support").orElse(null),
            supportMemberRepository.findByName("Sarah Johnson").orElse(null)
        );

        SupportTicket ticket18 = createTicketWithId(
            "AMX", "T", "000002",
            "Security certificate expiration",
            "SSL certificates are expiring soon and need renewal.",
            TicketType.TECHNICAL,
            "Amex",
            "Amex-Security",
            solutionRepository.findByName("Performance").orElse(null),
            Severity.CRITICAL,
            400,
            supportGroupRepository.findByName("Infrastructure Team").orElse(null),
            supportMemberRepository.findByName("David Brown").orElse(null)
        );

        SupportTicket ticket19 = createTicketWithId(
            "DIS", "G", "000002",
            "User feedback system down",
            "User feedback collection system is not functioning properly.",
            TicketType.GENERAL,
            "Disney",
            "Disney-Feedback",
            solutionRepository.findByName("Recommendation Engine").orElse(null),
            Severity.LOW,
            30,
            supportGroupRepository.findByName("Customer Service").orElse(null),
            supportMemberRepository.findByName("Lisa Wilson").orElse(null)
        );

        SupportTicket ticket20 = createTicketWithId(
            "HUL", "T", "000002",
            "CDN performance degradation",
            "Content Delivery Network performance has degraded significantly.",
            TicketType.TECHNICAL,
            "Hulu",
            "Hulu-CDN",
            solutionRepository.findByName("Hulu Global CDN").orElse(null),
            Severity.HIGH,
            250,
            supportGroupRepository.findByName("Infrastructure Team").orElse(null),
            supportMemberRepository.findByName("David Brown").orElse(null)
        );

        List<SupportTicket> tickets = Arrays.asList(
            ticket1, ticket2, ticket3, ticket4, ticket5, ticket6, ticket7, ticket8,
            ticket9, ticket10, ticket11, ticket12, ticket13, ticket14, ticket15,
            ticket16, ticket17, ticket18, ticket19, ticket20
        );
        tickets.forEach(supportTicketRepository::save);
        System.out.println("Support Tickets initialized.");
    }

    private void associateEnvironmentsWithSolutions() {
        System.out.println("Starting environment associations...");
        // Get all environments
        Environment prod = environmentRepository.findByType(Environment.EnvironmentType.PRODUCTION).orElse(null);
        Environment dev = environmentRepository.findByType(Environment.EnvironmentType.DEVELOPMENT).orElse(null);
        Environment uat = environmentRepository.findByType(Environment.EnvironmentType.UAT).orElse(null);
        Environment sandbox = environmentRepository.findByType(Environment.EnvironmentType.SANDBOX).orElse(null);
        
        System.out.println("Found environments - PROD: " + (prod != null) + ", DEV: " + (dev != null) + ", UAT: " + (uat != null) + ", SANDBOX: " + (sandbox != null));
        
        if (prod == null || dev == null || uat == null || sandbox == null) {
            System.out.println("Environments not found, skipping environment associations");
            return;
        }
        
        // Get all solutions
        List<Solution> solutions = solutionRepository.findAll();
        int updatedCount = 0;
        
        for (Solution solution : solutions) {
            // Skip if solution already has environments
            if (solution.getEnvironments() != null && !solution.getEnvironments().isEmpty()) {
                continue;
            }
            
            String solutionName = solution.getName().toLowerCase();
            HashSet<Environment> environments = new HashSet<>();
            
            // Associate environments based on solution characteristics with variety
            if (solutionName.contains("database")) {
                // Database solutions get all environments
                environments.add(prod);
                environments.add(dev);
                environments.add(uat);
                environments.add(sandbox);
            } else if (solutionName.contains("upload")) {
                // Upload solutions get prod, dev, and uat
                environments.add(prod);
                environments.add(dev);
                environments.add(uat);
            } else if (solutionName.contains("api")) {
                // API solutions get prod and dev
                environments.add(prod);
                environments.add(dev);
            } else if (solutionName.contains("authentication")) {
                // Authentication solutions get prod, dev, and sandbox
                environments.add(prod);
                environments.add(dev);
                environments.add(sandbox);
            } else if (solutionName.contains("performance")) {
                // Performance solutions get prod and uat
                environments.add(prod);
                environments.add(uat);
            } else if (solutionName.contains("streaming")) {
                // Streaming solutions get all environments
                environments.add(prod);
                environments.add(dev);
                environments.add(uat);
                environments.add(sandbox);
            } else if (solutionName.contains("content")) {
                // Content solutions get prod, dev, and sandbox
                environments.add(prod);
                environments.add(dev);
                environments.add(sandbox);
            } else if (solutionName.contains("analytics")) {
                // Analytics solutions get dev, uat, and sandbox
                environments.add(dev);
                environments.add(uat);
                environments.add(sandbox);
            } else if (solutionName.contains("drm")) {
                // DRM solutions get prod and uat
                environments.add(prod);
                environments.add(uat);
            } else if (solutionName.contains("recommendation")) {
                // Recommendation solutions get dev and uat
                environments.add(dev);
                environments.add(uat);
            } else if (solutionName.contains("testing")) {
                // Testing solutions get uat and sandbox
                environments.add(uat);
                environments.add(sandbox);
            } else if (solutionName.contains("delivery") || solutionName.contains("cdn")) {
                // Delivery/CDN solutions get prod, dev, and uat
                environments.add(prod);
                environments.add(dev);
                environments.add(uat);
            } else if (solutionName.contains("subtitle") || solutionName.contains("processing")) {
                // Processing solutions get dev and sandbox
                environments.add(dev);
                environments.add(sandbox);
            } else {
                // Default: all solutions get at least dev and uat
                environments.add(dev);
                environments.add(uat);
            }
            
            // Set the environments for the solution
            solution.setEnvironments(environments);
            solutionRepository.save(solution);
            updatedCount++;
        }
        
        if (updatedCount > 0) {
            System.out.println("Successfully associated environments with " + updatedCount + " solutions");
        } else {
            System.out.println("All solutions already have environment associations");
        }
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