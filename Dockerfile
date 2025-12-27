FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /build

# Copy entire repo (parent + modules)
COPY . .

RUN mvn -B -DskipTests clean package

# 2. RUNTIME STAGE FOR SERVICES

# Common base image for all microservices
FROM eclipse-temurin:17-jre AS base

WORKDIR /app

# APPOINTMENT SERVICE
FROM base AS api-gateway-service
COPY --from=builder /build/api-gateway-service/target/api-gateway-service*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

# PATIENT SERVICE
FROM base AS patient-service
COPY --from=builder /build/patient-service/target/patient-service*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

# APPOINTMENT SERVICE
FROM base AS appointment-service
COPY --from=builder /build/appointment-service/target/appointment-service*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

# NOTIFICATION SERVICE
FROM base AS notification-service
COPY --from=builder /build/notification-service/target/notification-service*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

# AUDIT SERVICE
FROM base AS audit-service
COPY --from=builder /build/audit-service/target/audit-service*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
