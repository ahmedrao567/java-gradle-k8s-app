# Use lightweight Java 17 runtime
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY build/libs/java-gradle-k8s-app-1.0.jar app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]
