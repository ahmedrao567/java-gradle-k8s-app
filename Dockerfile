# Use lightweight Java 21 runtime
FROM eclipse-temurin:21-jdk-jammy

# Set working directory
WORKDIR /app

# Copy the built JAR from Gradle build output
COPY build/libs/java-gradle-k8s-app-1.0.jar app.jar

# Expose the port (informational, not mandatory but good practice)
EXPOSE 8080

# Run the app
CMD ["java", "-jar", "/app/app.jar"]