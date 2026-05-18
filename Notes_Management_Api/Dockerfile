# Build Stage
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY Notes_Management_Api/pom.xml .
COPY Notes_Management_Api/src ./src

RUN mvn clean package -DskipTests

# Run Stage
FROM eclipse-temurin:21

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]