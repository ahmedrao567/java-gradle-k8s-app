FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
ARG JAR_FILE=build/libs/java-gradle-k8s-app-1.0.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/app.jar"]