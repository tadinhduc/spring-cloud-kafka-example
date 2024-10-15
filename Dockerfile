# Use an official OpenJDK runtime as the base image
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/spring-cloud-kafka-retry-0.0.1-SNAPSHOT.jar /app/spring-cloud-kafka-retry-0.0.1-SNAPSHOT.jar

# Expose the port that your Spring Boot app runs on
EXPOSE 8081

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/spring-cloud-kafka-retry-0.0.1-SNAPSHOT.jar"]
