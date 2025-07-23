# Support Ticket Management System

A comprehensive Spring Boot application for managing support tickets with a modern web interface. This system allows users to create, view, and manage support tickets with detailed information including severity levels, customer impact, and assignment tracking.

## Features

### Core Functionality
- **Ticket Management**: Create, read, update, and delete support tickets
- **Ticket Types**: Support for General, Technical, and File Transfer tickets
- **Severity Levels**: Critical, High, Medium, and Low priority levels
- **Customer Impact Tracking**: Track number of customers affected by issues
- **Solution-based Tickets**: Tickets must be associated with predefined solutions
- **Support Group Assignment**: Assign tickets to specific support groups
- **Assignee Tracking**: Assign tickets to specific support team members

### User Interface
- **Modern Dashboard**: Beautiful, responsive dashboard with statistics
- **Ticket Listing**: Interactive ticket cards with hover effects
- **Detailed View**: Comprehensive ticket detail page with timeline
- **Search & Filter**: Advanced filtering by severity, type, partner, and account
- **Real-time Statistics**: Live dashboard statistics and metrics

### Technical Features
- **RESTful API**: Complete REST API for all operations
- **H2 Database**: In-memory database with sample data
- **JPA/Hibernate**: Object-relational mapping
- **Thymeleaf Templates**: Server-side templating
- **Bootstrap 5**: Modern, responsive UI framework
- **Font Awesome Icons**: Professional iconography

## Technology Stack

- **Backend**: Spring Boot 3.2.0, Java 17
- **Database**: H2 (In-memory)
- **ORM**: Spring Data JPA, Hibernate
- **Frontend**: Thymeleaf, Bootstrap 5, JavaScript
- **Build Tool**: Maven
- **Validation**: Jakarta Validation

## Project Structure

```
src/
├── main/
│   ├── java/com/example/
│   │   ├── entity/
│   │   │   ├── SupportTicket.java
│   │   │   ├── Solution.java
│   │   │   ├── SupportGroup.java
│   │   │   ├── SupportMember.java
│   │   │   ├── TicketType.java
│   │   │   └── Severity.java
│   │   ├── repository/
│   │   │   ├── SupportTicketRepository.java
│   │   │   ├── SolutionRepository.java
│   │   │   ├── SupportGroupRepository.java
│   │   │   └── SupportMemberRepository.java
│   │   ├── service/
│   │   │   └── SupportTicketService.java
│   │   ├── controller/
│   │   │   ├── SupportTicketController.java
│   │   │   └── WebController.java
│   │   ├── config/
│   │   │   └── DataInitializer.java
│   │   └── SupportTicketSystemApplication.java
│   └── resources/
│       ├── templates/
│       │   ├── dashboard.html
│       │   └── ticket-detail.html
│       └── application.properties
```

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd support-ticket-system
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - Web Interface: http://localhost:8080
   - H2 Console: http://localhost:8080/h2-console
   - REST API: http://localhost:8080/api/tickets

### H2 Database Console
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: `password`

## API Endpoints

### Support Tickets

#### Get All Tickets
```
GET /api/tickets
```

#### Get Ticket by ID
```
GET /api/tickets/{id}
```

#### Create New Ticket
```
POST /api/tickets
Content-Type: application/json

{
    "title": "Database connection timeout",
    "description": "Users experiencing database connection issues",
    "ticketType": "TECHNICAL",
    "partnerName": "TechCorp Inc.",
    "accountName": "TechCorp-Prod",
    "severity": "HIGH",
    "customersImpacted": 150
}
```

#### Update Ticket
```
PUT /api/tickets/{id}
Content-Type: application/json

{
    "title": "Updated title",
    "description": "Updated description",
    "severity": "MEDIUM"
}
```

#### Delete Ticket
```
DELETE /api/tickets/{id}
```

#### Search Tickets
```
GET /api/tickets/search/title?title=database
GET /api/tickets/type/TECHNICAL
GET /api/tickets/severity/HIGH
GET /api/tickets/partner/TechCorp
GET /api/tickets/account/TechCorp-Prod
```

#### Get Ticket Statistics
```
GET /api/tickets/statistics
```

#### Search by Multiple Criteria
```
GET /api/tickets/search/criteria?ticketType=TECHNICAL&severity=HIGH&partnerName=TechCorp
```

## Sample Data

The application comes pre-loaded with sample data including:

- **5 Solutions**: Database Connection Issue, File Upload Problem, API Integration Error, User Authentication Issue, Performance Optimization
- **3 Support Groups**: Technical Support, Customer Service, Infrastructure Team
- **5 Support Members**: John Smith, Sarah Johnson, Mike Davis, Lisa Wilson, David Brown
- **8 Sample Tickets**: Various tickets with different severities, types, and assignments

## Web Interface Features

### Dashboard
- **Statistics Cards**: Total tickets, critical/high priority, technical, and general tickets
- **Advanced Filtering**: Filter by severity, type, partner, and account
- **Search Functionality**: Search tickets by title
- **Interactive Cards**: Click to view ticket details
- **Responsive Design**: Works on desktop and mobile devices

### Ticket Detail View
- **Comprehensive Information**: All ticket details in an organized layout
- **Timeline**: Visual timeline of ticket events
- **Contact Information**: Assignee contact details
- **Solution Details**: Associated solution information
- **Quick Actions**: Edit, print, and share options

## Database Schema

### SupportTicket
- `ticket_id` (UUID, Primary Key)
- `title` (String, Required)
- `description` (Text)
- `ticket_type` (Enum: GENERAL, TECHNICAL, FILE_TRANSFER)
- `partner_name` (String)
- `account_name` (String)
- `severity` (Enum: LOW, MEDIUM, HIGH, CRITICAL)
- `customers_impacted` (Integer)
- `created_at` (LocalDateTime)
- `last_updated` (LocalDateTime)
- `solution_id` (UUID, Foreign Key)
- `support_group_id` (UUID, Foreign Key)
- `assignee_id` (UUID, Foreign Key)

### Solution
- `solution_id` (UUID, Primary Key)
- `name` (String, Required, Unique)
- `description` (Text)
- `created_at` (LocalDateTime)
- `last_updated` (LocalDateTime)

### SupportGroup
- `support_group_id` (UUID, Primary Key)
- `name` (String, Required, Unique)
- `description` (Text)
- `created_at` (LocalDateTime)
- `last_updated` (LocalDateTime)

### SupportMember
- `member_id` (UUID, Primary Key)
- `name` (String, Required)
- `email` (String, Required, Unique)
- `phone` (String)
- `created_at` (LocalDateTime)
- `last_updated` (LocalDateTime)
- `support_group_id` (UUID, Foreign Key)

## Development

### Adding New Features
1. Create entity classes in the `entity` package
2. Create repository interfaces in the `repository` package
3. Create service classes in the `service` package
4. Create controller classes in the `controller` package
5. Add Thymeleaf templates in `resources/templates`

### Running Tests
```bash
mvn test
```

### Building for Production
```bash
mvn clean package
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License.

## Support

For support and questions, please contact the development team or create an issue in the repository. 