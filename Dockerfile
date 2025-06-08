# Use a lightweight base image with Java 17 (or 11 if applicable)
FROM eclipse-temurin:21-jdk-alpine

# Set working directory in container
WORKDIR /app

# Copy built JAR file into the container
COPY target/Weather-*.jar app.jar

# Expose the application port
EXPOSE 8080

# Command to run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
