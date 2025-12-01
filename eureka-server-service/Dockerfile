#FROM maven:3.9.9-eclipse-temurin-17 AS builder
#
#WORKDIR /app
#
#COPY pom.xml .
#
#RUN mvn dependency:go-offline -B
#
#COPY src ./src
#
#RUN mvn clean package -DskipTests
#
#FROM openjdk:17-jdk AS runner
#
#WORKDIR /app
#
#COPY --from=builder /app/target/*.jar ./app.jar
#
#EXPOSE 8763
#
#ENTRYPOINT ["java", "-jar", "app.jar"]
#


FROM maven:3-eclipse-temurin-17-alpine AS builder

WORKDIR /build

COPY . /build

RUN mvn clean package -pl eureka-server-service -am -DskipTests

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=builder /build/eureka-server-service/target/*.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]