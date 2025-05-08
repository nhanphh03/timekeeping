FROM openjdk:23-jdk-slim

WORKDIR /app/

COPY /target/enroll_backend_app-0.0.1.jar /app/enroll_backend_app.jar

# Define the command to run the application
CMD ["java", "-jar", "/app/enroll_backend_app.jar"]