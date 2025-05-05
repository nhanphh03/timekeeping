package com.enroll.enroll_minio.config;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@code @Package:} com.enroll.enroll_minio.controller
 * {@code @author:} nhanph
 * {@code @date:} 3/24/2025 2025
 * {@code @Copyright:} @nhanph
 */
@Configuration
public class MinIOConfig {

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Bean
    protected MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

}

