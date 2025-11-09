FROM maven:3.9.9-eclipse-temurin-17 AS builder

# Must be root of the project inside container
WORKDIR /build

# Copy parent pom
COPY pom.xml .

# Copy module folders needed for building patient-service
COPY audit-core ./audit-core
COPY patient-service ./patient-service

# Build only patient-service and required modules
RUN mvn -B -pl patient-service -am clean package -DskipTests


# ---- Runtime image ----
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy the built jar from patient-service target folder
COPY --from=builder /build/patient-service/target/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
