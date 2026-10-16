# StoreMaster

A comprehensive e-commerce backend application built with Spring Boot. StoreMaster provides RESTful APIs for managing products, users, shopping carts, and orders with security features and database persistence.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Setup and Installation](#setup-and-installation)
- [Configuration](#configuration)
- [API Endpoints](#api-endpoints)
- [Database Schema](#database-schema)
- [Running the Application](#running-the-application)
- [Docker Deployment](#docker-deployment)
- [Testing](#testing)
- [Contributing](#contributing)

## Overview

StoreMaster is a full-featured e-commerce backend service that manages:
- **User Management**: User registration and authentication with secure password encoding
- **Product Catalog**: CRUD operations for products
- **Shopping Cart**: Add/remove items from cart, manage cart items
- **Order Management**: Create and track orders

The application is built following RESTful principles with proper separation of concerns using Service, Repository, and Controller layers.

## Features

✅ **User Authentication & Authorization**
- User registration with validation
- Secure login with BCrypt password encoding
- Spring Security integration

✅ **Product Management**
- Add, update, delete, and retrieve products
- Bulk product addition
- Product listing and individual product retrieval

✅ **Shopping Cart**
- Add items to cart
- Remove items from cart
- View cart contents
- Cart item management

✅ **Order Processing**
- Create orders from cart items
- Order tracking and history
- Order item details with pricing

✅ **Database Persistence**
- PostgreSQL database integration
- JPA/Hibernate ORM
- Automatic schema updates (DDL auto)

✅ **Monitoring & Actuator Endpoints**
- Health checks
- Application metrics
- Application information

✅ **Docker Support**
- Containerized application deployment
- Docker Compose for multi-service orchestration
- PostgreSQL database container

## Technology Stack

### Core Framework
- **Java 21** - Programming language
- **Spring Boot 3.5.8** - Application framework
- **Maven** - Build and dependency management

### Key Dependencies
- **Spring Boot Starter Web** - RESTful web services
- **Spring Boot Starter Data JPA** - ORM and database access
- **Spring Boot Starter Security** - Authentication and authorization
- **Spring Boot Starter Validation** - Input validation
- **Spring Boot Actuator** - Monitoring and metrics
- **Spring Boot DevTools** - Development utilities
- **PostgreSQL Driver** - Database connectivity
- **Lombok** - Reduce boilerplate code
- **Spring Security Test** - Security testing

### Development & Testing
- **JUnit 5** - Unit testing framework
- **Mockito** - Mocking framework
- **Spring Boot Test** - Integration testing

### Infrastructure
- **Docker** - Containerization
- **Docker Compose** - Multi-container orchestration
- **PostgreSQL 13** - Database

## Prerequisites

Before running the application, ensure you have:

- **Java 21** or higher installed
- **Maven 3.9.10** or higher
- **PostgreSQL 13** (if running locally without Docker)
- **Docker & Docker Compose** (for containerized deployment)
- **Git** (for version control)

## Project Structure

```
StoreMaster/
├── src/
│   ├── main/
│   │   ├── java/com/akshay/StoreMaster/
│   │   │   ├── StoreMasterApplication.java       # Main application class
│   │   │   ├── config/                           # Spring configurations
│   │   │   ├── Constants/                        # Application constants
│   │   │   ├── controller/                       # REST controllers
│   │   │   │   ├── UserController.java
│   │   │   │   ├── ProductController.java
│   │   │   │   ├── CartController.java
│   │   │   │   └── OrderController.java
│   │   │   ├── service/                          # Business logic
│   │   │   │   ├── UserService.java
│   │   │   │   ├── ProductService.java
│   │   │   │   ├── CartService.java
│   │   │   │   └── OrderService.java
│   │   │   ├── repository/                       # Data access layer
│   │   │   ├── entity/                           # JPA entities
│   │   │   │   ├── User.java
│   │   │   │   ├── Product.java
│   │   │   │   ├── Cart.java
│   │   │   │   ├── CartItem.java
│   │   │   │   ├── Order.java
│   │   │   │   └── OrderItem.java
│   │   │   ├── dto/                              # Data Transfer Objects
│   │   │   └── exception/                        # Custom exceptions
│   │   └── resources/
│   │       └── application.yml                   # Application configuration
│   └── test/                                     # Test classes
├── pom.xml                                       # Maven configuration
├── Dockerfile                                    # Docker image definition
├── docker-compose.yml                            # Multi-container setup
├── mvnw & mvnw.cmd                              # Maven wrapper scripts
└── README.md                                     # This file
```

## Setup and Installation

### 1. Clone the Repository

```bash
cd D:\Learn\JAVA\SpringBoot Projects\StoreMaster
```

### 2. Build the Project

Using Maven Wrapper:

```bash
./mvnw.cmd clean package
```

Or using installed Maven:

```bash
mvn clean package
```

### 3. Configure Database

The application uses PostgreSQL. Configure your database connection in `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5433/storemaster_db
    username: postgres
    password: Postgrespw
```

## Configuration

### Application Configuration (`application.yml`)

Key configuration properties:

| Property | Value | Description |
|----------|-------|-------------|
| `spring.datasource.url` | `jdbc:postgresql://localhost:5433/storemaster_db` | Database connection URL |
| `spring.jpa.hibernate.ddl-auto` | `update` | Auto-update database schema |
| `server.port` | `8080` | Application port |
| `spring.jpa.show-sql` | `true` | Log SQL queries |
| `spring.jpa.properties.hibernate.format_sql` | `true` | Format SQL output |

### Security Configuration

- **Password Encoding**: BCrypt with strength 10
- **Validation**: Jakarta validation framework
- **Authentication**: Spring Security

## API Endpoints

### User Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/storeMaster/user/hello` | Health check - returns service status |
| `POST` | `/storeMaster/user/register` | User registration |
| `POST` | `/storeMaster/user/login` | User login |

**Register Request Body:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "securePassword123"
}
```

**Login Request Body:**
```json
{
  "email": "john@example.com",
  "password": "securePassword123"
}
```

### Product Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/storeMaster/product/add` | Add new products (bulk) |
| `PUT` | `/storeMaster/product/update/{productId}` | Update product details |
| `DELETE` | `/storeMaster/product/delete/{productId}` | Delete a product |
| `GET` | `/storeMaster/product/getAllProduct` | Get all products |
| `GET` | `/storeMaster/product/get/{productId}` | Get specific product |

**Product Request Body:**
```json
{
  "name": "Product Name",
  "description": "Product description",
  "price": 99.99,
  "quantity": 100
}
```

### Cart Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/storeMaster/cart/add` | Add item to cart |
| `GET` | `/storeMaster/cart/get/{userId}` | Get user's cart |
| `DELETE` | `/storeMaster/cart/remove/{cartItemId}` | Remove item from cart |

### Order Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/storeMaster/order/create` | Create order from cart |
| `GET` | `/storeMaster/order/get/{orderId}` | Get order details |
| `GET` | `/storeMaster/order/user/{userId}` | Get user's orders |

## Database Schema

### Core Entities

**User**
- id (PK)
- name
- email (unique)
- password (encrypted)
- registration_date

**Product**
- id (PK)
- name
- description
- price
- quantity
- created_date

**Cart**
- id (PK)
- user_id (FK)
- created_date

**CartItem**
- id (PK)
- cart_id (FK)
- product_id (FK)
- quantity
- added_date

**Order**
- id (PK)
- user_id (FK)
- order_date
- status (PENDING, PROCESSING, SHIPPED, DELIVERED)
- total_price

**OrderItem**
- id (PK)
- order_id (FK)
- product_id (FK)
- quantity
- unit_price

## Running the Application

### Option 1: Local Development (Without Docker)

1. **Start PostgreSQL** database on `localhost:5433`

2. **Create database:**
```sql
CREATE DATABASE storemaster_db;
```

3. **Run the application:**

Using Maven:
```bash
./mvnw.cmd spring-boot:run
```

Or run the JAR:
```bash
java -jar target/StoreMaster-0.0.1-SNAPSHOT.jar
```

4. **Access the application:**
```
http://localhost:8080/storeMaster/user/hello
```

### Option 2: Health Check & Metrics

```bash
# Health endpoint
GET http://localhost:8080/actuator/health

# Metrics
GET http://localhost:8080/actuator/metrics

# Application info
GET http://localhost:8080/actuator/info
```

## Docker Deployment

### Build and Run with Docker Compose

1. **Build the application first:**
```bash
./mvnw.cmd clean package
```

2. **Start all services:**
```bash
docker-compose up --build
```

This will:
- Build the StoreMaster application image
- Start the StoreMaster application on port `8080`
- Start PostgreSQL 13 on port `5433`
- Automatically create the database and tables

3. **Verify services are running:**
```bash
docker ps
```

4. **View logs:**
```bash
docker logs StoreMaster
docker logs storemaster_db
```

5. **Stop services:**
```bash
docker-compose down
```

### Docker Compose Environment Variables

The `docker-compose.yml` includes environment variables for:
- Database connection
- Hibernate DDL auto-update
- Logging levels
- Actuator endpoints
- Application port

## Testing

### Run Tests

Using Maven:
```bash
./mvnw.cmd test
```

Or run specific test class:
```bash
./mvnw.cmd test -Dtest=CartServiceTest
```

### Test Dependencies
- **Spring Boot Test** - Integration testing
- **Spring Security Test** - Security testing
- **Mockito** - Unit test mocking
- **JUnit 5** - Test framework

### Example Test Class
```java
@SpringBootTest
class StoreMasterApplicationTests {
    
    @Autowired
    private UserService userService;
    
    @Test
    void testUserRegistration() {
        // Test implementation
    }
}
```

## Project Highlights

### Architecture Patterns
- **MVC Architecture** - Model-View-Controller pattern
- **Service Layer** - Business logic separation
- **Repository Pattern** - Data access abstraction
- **DTO Pattern** - Data transfer objects for API contracts

### Security Features
- **BCrypt Password Encoding** - Secure password hashing
- **Spring Security** - Framework-level security
- **Input Validation** - Jakarta validation annotations

### Best Practices
- **Logging** - SLF4J with Logback
- **Transaction Management** - @Transactional annotations
- **Error Handling** - Custom exception handling
- **API Documentation** - Endpoint documentation

## Development Notes

### Adding New Features

1. **Create Entity** in `entity/` directory
2. **Create Repository** extending `JpaRepository`
3. **Create Service** with business logic
4. **Create Controller** with REST endpoints
5. **Create DTOs** for API contracts
6. **Write Tests** in `test/` directory

### Lombok Usage

The project uses Lombok to reduce boilerplate code:
- `@Getter` / `@Setter` - Generate getters and setters
- `@AllArgsConstructor` - Generate all-args constructor
- `@NoArgsConstructor` - Generate no-args constructor
- `@Slf4j` - Inject logger

## Troubleshooting

### Database Connection Issues

If you get `Connection refused` errors:
1. Verify PostgreSQL is running on port 5433
2. Check credentials in `application.yml`
3. Ensure database `storemaster_db` exists

### Port Already in Use

If port 8080 is in use, change it in `application.yml`:
```yaml
server:
  port: 8081  # New port
```

### Docker Issues

Clear Docker cache and rebuild:
```bash
docker-compose down -v
docker system prune
docker-compose up --build
```

## Contributing

To contribute to this project:

1. Create a new branch: `git checkout -b feature/new-feature`
2. Commit changes: `git commit -m 'Add new feature'`
3. Push to branch: `git push origin feature/new-feature`
4. Submit a pull request

## License

This project is a demo project for Spring Boot learning purposes.

## Contact & Support

For questions or support, please contact the project maintainer.

---

**Last Updated**: April 2026
**Version**: 0.0.1-SNAPSHOT
**Status**: Active Development

