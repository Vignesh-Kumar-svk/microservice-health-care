FROM maven:3-eclipse-temurin-17-alpine AS builder

WORKDIR /build

COPY . .

RUN mvn clean package -pl patient-service -am -DskipTests

FROM eclipse-temurin:17.0.17_10-jdk-alpine-3.20

RUN apk add --no-cache dumb-init

WORKDIR /app

COPY --from=builder /build/patient-service/target/*.jar app.jar

ENTRYPOINT ["dumb-init", "java", "-jar", "app.jar"]

