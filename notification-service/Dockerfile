FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy only pom.xml first (for caching dependencies)
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy all source code
COPY src ./src

# Build the JAR
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk AS runner

WORKDIR /app

# Copy the built JAR from builder
COPY --from=builder /app/target/*.jar ./app.jar

# Expose service port
EXPOSE 8083

# Start application
ENTRYPOINT ["java", "-jar", "app.jar"]

