FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/enroll_minio-0.0.1.jar /app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]