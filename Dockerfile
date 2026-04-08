# Use lightweight Java 17 runtime
FROM eclipse-temurin:21-jdk-jammy

# Set working directory
WORKDIR /app

# Copy the built JAR from Gradle build output
COPY build/libs/java-gradle-k8s-app-1.0.jar app.jar

EXPOSE 8080

# Run the application and keep container alive
CMD ["sh", "-c", "java -jar /app/app.jar; tail -f /dev/null"]