# Banking System Backend API

A production-ready RESTful Banking System Backend built using **Java**, **Spring Boot**, **Spring Security**, **JWT Authentication**, **Spring Data JPA**, and **MySQL**. The application provides secure banking operations such as account creation, deposits, withdrawals, fund transfers, authentication, and transaction history through well-designed REST APIs.

---

## Features

- User Registration & Login using JWT Authentication
- Secure REST APIs with Spring Security
- Create Bank Account
- View Account Details
- Deposit Money
- Withdraw Money
- Transfer Funds Between Accounts
- Transaction History Management
- Global Exception Handling
- Request Validation
- Swagger/OpenAPI Documentation
- Environment Variable Configuration
- Docker Support
- Production-Ready Project Structure

---

## Tech Stack

- Java 21
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- MySQL
- Swagger / OpenAPI
- Maven
- Docker

---

## Project Structure

```
src/main/java
│
├── config
├── controller
├── dto
│   ├── request
│   └── response
├── entity
├── exception
├── repository
├── security
├── service
├── serviceImpl
├── util
└── validation
```

---

## REST API Endpoints

### Authentication

| Method | Endpoint |
|----------|--------------------------|
| POST | `/api/auth/register` |
| POST | `/api/auth/login` |

### Account Management

| Method | Endpoint |
|----------|-----------------------------|
| POST | `/api/v1/accounts` |
| GET | `/api/v1/accounts/{id}` |
| GET | `/api/v1/accounts` |
| PUT | `/api/v1/accounts/{id}/deposit` |
| PUT | `/api/v1/accounts/{id}/withdraw` |
| PUT | `/api/v1/accounts/transfer` |
| DELETE | `/api/v1/accounts/{id}` |

---

## Authentication

This project uses **JWT (JSON Web Token)** for authentication.

After successful login, a JWT token is returned.

Include the token in every protected request.

```
Authorization: Bearer YOUR_JWT_TOKEN
```

---

## Swagger Documentation

```
http://localhost:8080/swagger-ui/index.html
```

or

```
http://localhost:8080/swagger-ui.html
```

---

## Environment Variables

Configure the following environment variables before running the project.

| Variable | Description |
|------------|----------------|
| DB_URL | MySQL Database URL |
| DB_USERNAME | Database Username |
| DB_PASSWORD | Database Password |
| JWT_SECRET | Base64 JWT Secret |
| JWT_EXPIRATION | JWT Expiration Time |

Example

```
DB_URL=jdbc:mysql://localhost:3306/bankingSystem
DB_USERNAME=root
DB_PASSWORD=root

JWT_SECRET=YOUR_BASE64_SECRET
JWT_EXPIRATION=86400000
```

---

## Running the Project

### Navigate

```bash
cd BankingSystembackend
```

### Build

```bash
mvn clean package
```

### Run

```bash
mvn spring-boot:run
```

---

## Docker

### Build Docker Image

```bash
docker build -t banking-system .
```

### Run Docker Container

```bash
docker run -p 8080:8080 banking-system
```

---

## Security

- JWT Authentication
- BCrypt Password Encryption
- Stateless Authentication
- Protected REST Endpoints
- Role-Based Authorization Support

---

## Validation

- Bean Validation
- Request Validation
- Input Sanitization
- Centralized Exception Handling

---

## Database

MySQL is used as the primary relational database.

Hibernate is used as the ORM framework.

---

## API Documentation

Interactive API documentation is available through Swagger UI.

The API documentation includes

- Request Parameters
- Response Models
- Authentication
- HTTP Status Codes
- Example Requests


---

## Author

**Harsh Sahu**

GitHub: https://github.com/Harshsahu11
