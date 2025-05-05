# Sử dụng base image là OpenJDK 17
FROM openjdk:17-jdk-slim

# Thiết lập thư mục làm việc trong container
WORKDIR /app

# Copy file WAR vào thư mục làm việc
COPY ./target/proton.face.enroll-0.0.1.war /app/proton.face.enroll.war

# Chạy ứng dụng Spring Boot từ file WAR
CMD ["java", "-jar", "proton.face.enroll.war"]