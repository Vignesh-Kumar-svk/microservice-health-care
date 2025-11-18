# ------- STAGE 1: Build -------
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /build

# 1. Copy only pom files (so dependencies are cached properly)
COPY pom.xml .
COPY audit-core/pom.xml audit-core/pom.xml
COPY patient-service/pom.xml patient-service/pom.xml
COPY common-module/pom.xml common-module/pom.xml
COPY appointment-service/pom.xml appointment-service/pom.xml
COPY notification-service/pom.xml notification-service/pom.xml
COPY auth-service/pom.xml auth-service/pom.xml
COPY api-gateway-service/pom.xml api-gateway-service/pom.xml

# Cache dependencies ONLY
RUN mvn -B -pl patient-service -am dependency:go-offline

# 2. Copy the complete project (this layer changes when code changes)
COPY . .

# Build jar for appointment-service
RUN mvn -B -pl patient-service -am clean package -DskipTests


# ------- STAGE 2: Runtime -------
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=builder /build/patient-service/target/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
