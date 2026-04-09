FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY build/libs/java-gradle-k8s-app-1.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]