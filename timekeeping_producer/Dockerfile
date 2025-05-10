FROM openjdk:17-jdk-slim
WORKDIR /app

# Sao chép file .jar vào container
COPY ./target/timekeeping_producer-0.0.1.jar /app/timekeeping_producer.jar

# Cấu hình lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "/app/timekeeping_producer.jar"]
