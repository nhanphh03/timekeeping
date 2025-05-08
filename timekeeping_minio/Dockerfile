FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/enroll_minio-0.0.1.jar /app/app.jar
CMD ["java", "-jar", "app.jar"]