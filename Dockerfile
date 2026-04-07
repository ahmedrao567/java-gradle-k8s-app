# Use lightweight Java 17 runtime
FROM eclipse-temurin:17-jdk-jammy

# Set working directory
WORKDIR /app

# Copy the built JAR from Gradle build output
COPY build/libs/java-gradle-k8s-app-1.0.jar app.jar

# Run the application
ENTRYPOINT ["java","-jar","/app/app.jar"]