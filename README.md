# 🏥 Healthcare Management (Microservices Project)

A production-oriented **microservices-based healthcare management system** built using **Spring Boot, Spring Cloud, Kafka, Redis, Docker, PostgreSQL, and Observability Stack**.  
This platform demonstrates scalable backend architecture, distributed system design, secure authentication, asynchronous communication, monitoring, and resilience engineering.

---

# 🚀 Architecture Overview

The system follows a distributed microservices architecture where each service is independently deployable and communicates through:

- API Gateway
- Eureka Service Discovery
- REST APIs
- Kafka Event Streaming

Core focus areas:
- Scalable service communication
- Fault tolerance
- Distributed locking
- Secure authentication
- Real-time monitoring
- Containerized deployment

---

# ⚙️ Tech Stack

| Category | Technologies |
|---|---|
| Backend | Java, Spring Boot, Spring Cloud |
| Security | Spring Security, JWT, Keycloak |
| Database | PostgreSQL |
| Messaging | Apache Kafka |
| Caching | Redis |
| Service Discovery | Eureka Server |
| API Gateway | Spring Cloud Gateway |
| Monitoring | Prometheus, Grafana |
| Resilience | Resilience4j |
| Documentation | Swagger UI |
| Containerization | Docker, Docker Compose |
| Build Tool | Maven Multi-Module |

---

# 📦 Core Microservices

- Authentication Service
- Appointment Service
- Notification Service
- API Gateway
- Eureka Discovery Server
- Audit Service
- Shared Common Libraries

---

# 🔗 System Workflow

## 1. Authentication
- Users authenticate through Keycloak
- JWT access token issued
- Token validated at API Gateway

## 2. Appointment Booking
- Patients create/update/delete appointments
- Audit logs generated for every action
- Redis distributed locking prevents double booking

## 3. Event Streaming
- Appointment events published to Kafka
- Notification Service consumes events asynchronously

## 4. Notifications
- Email notifications sent for:
  - Appointment confirmation
  - Cancellation
  - Updates

## 5. Monitoring & Observability
- Prometheus collects metrics
- Grafana visualizes dashboards
- Resilience4j ensures fault tolerance

---

# 📚 Spring Boot Concepts Used

## Spring Core
- IoC
- Dependency Injection
- Bean Lifecycle
- Component Scanning

## Spring MVC
- REST APIs
- DTO Validation
- Global Exception Handling

## Spring AOP
- Audit Logging
- UUID Injection
- Method Interceptors

## Spring Security
- JWT Authentication
- RBAC Authorization
- Custom Security Filters

## Spring Data JPA
- Entity Mapping
- Repositories
- Transactions
- Derived Queries

## Spring Cloud
- Gateway Routing
- Eureka Load Balancing
- Distributed Service Discovery

---

# 🧩 Maven Multi-Module Design

The project follows a **library-first engineering approach**.

### Shared Internal Libraries

#### `audit-core`
- Auditing utilities
- UUID handling
- Shared AOP logic

#### `common-modules`
- Common DTOs
- Utility classes
- Constants
- Shared request/response models

Benefits:
- Reduced code duplication
- Better maintainability
- Cleaner scalability across services

---

# ⚡ Distributed System Features

## Apache Kafka
Used for asynchronous event-driven communication between services.

### Benefits
- Decoupled architecture
- Improved scalability
- Better fault isolation
- Reliable event processing

---

## Redis Distributed Locking
Prevents race conditions during appointment booking.

### Example
Two users attempting to book the same slot simultaneously:
- Redis lock ensures only one booking succeeds
- Prevents duplicate appointments

---

## Resilience4j
Improves inter-service reliability using:

- Circuit Breaker
- Retry
- Rate Limiter
- Bulkhead Isolation
- Fallback Handling

---

# 📊 Monitoring Stack

## Prometheus
Collects:
- API metrics
- Request latency
- JVM metrics
- CPU/Memory usage
- Circuit breaker statistics

## Grafana
Provides:
- Real-time dashboards
- Service health visualization
- Kafka monitoring
- Infrastructure monitoring

---

# 🐳 Docker Containerization

All services are fully containerized using Docker.

## Production-Oriented Docker Practices

### ✅ Multi-Stage Builds
Separate:
- Build stage
- Runtime stage

### ✅ Minimal Runtime Images
Only JRE included in runtime containers.

### ✅ Deterministic Base Images
Pinned image versions for reproducible builds.

Example:
```dockerfile
FROM maven:3-eclipse-temurin-17-alpine
