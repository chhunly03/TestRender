# Step 1: Use Maven to build the JAR
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Set working directory inside the container
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the project source code
COPY src ./src

# Build the Spring Boot JAR
RUN mvn clean package -DskipTests

# Step 2: Run the JAR using a lightweight JDK
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy built JAR from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
