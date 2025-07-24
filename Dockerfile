# Build stage
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Package stage
FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY --from=build ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]