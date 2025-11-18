FROM maven:3.9.9-eclipse-temurin-17 AS builder

# Must be root of the project inside container
WORKDIR /build

#Copy parent pom
COPY pom.xml .

# Copy module folders needed for building appointment-service
COPY audit-core ./audit-core
COPY patient-service ./patient-service
COPY common-module ./common-module
COPY appointment-service ./appointment-service
COPY notification-service ./notification-service
COPY auth-service/pom.xml auth-service/pom.xml
COPY api-gateway-service/pom.xml api-gateway-service/pom.xml

# Build only notification-service and required modules
RUN mvn -B -pl notification-service -am clean package -DskipTests


# ---- Runtime image ----
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy the built jar from notification-service target folder
COPY --from=builder /build/notification-service/target/*.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]

