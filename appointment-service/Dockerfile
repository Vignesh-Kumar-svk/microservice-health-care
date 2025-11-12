FROM maven:3.9.9-eclipse-temurin-17 AS builder

# Must be root of the project inside container
WORKDIR /build

#Copy parent pom
COPY pom.xml .

# Copy module folders needed for building appointment-service
COPY common-module ./common-module
COPY appointment-service ./appointment-service
COPY notification-service ./notification-service

# Build only appoinntment-service and required modules
RUN mvn -B -pl appointment-service -am clean package -DskipTests


# ---- Runtime image ----
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy the built jar from patient-service target folder
COPY --from=builder /build/appointment-service/target/*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]

