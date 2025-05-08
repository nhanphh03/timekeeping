package com.enroll.enroll_minio.service;

import com.enroll.enroll_minio.dto.UploadFileDTO;
import com.enroll.enroll_minio.dto.UploadResponse;
import io.micrometer.common.util.StringUtils;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.GetObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.UUID;

/**
 * {@code @Package:} com.enroll.enroll_minio.controller
 * {@code @author:} nhanph
 * {@code @date:} 3/24/2025 2025
 * {@code @Copyright:} @nhanph
 */
@Service
public class MinIOService {
    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;

    public MinIOService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public String uploadFile(MultipartFile file, String fileUrl, String fileName) {
        if (StringUtils.isBlank(fileName)){
            fileName = UUID.randomUUID().toString();
        }
        String objectKey = fileUrl + "/" + fileName;
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectKey)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("Error occurred while uploading file", e);
        }
        return objectKey;
    }

    public UploadResponse uploadFileBase64Img(UploadFileDTO uploadFileDTO) {
        String base64Img = uploadFileDTO.getBase64Img();
        String fileUrl = uploadFileDTO.getFileUrl();
        String fileName = uploadFileDTO.getFileName();

        String base64Data = base64Img.split(",").length > 1 ? base64Img.split(",")[1] : base64Img;
        byte[] decodedBytes = Base64.getDecoder().decode(base64Data);

        if (StringUtils.isBlank(fileName)) {
            fileName = UUID.randomUUID() + ".jpg";
        }

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedBytes)) {
            String objectKey = fileUrl + "/" + fileName;
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectKey)
                            .stream(inputStream, decodedBytes.length, -1)
                            .contentType("image/jpeg")
                            .build()
            );
            return new UploadResponse(objectKey);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error occurred while uploading base64 image", e);
        }
    }


    public InputStream getFile(String filePath) {
        String objectKey = filePath.replaceFirst("/api/v1/file/", "");
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectKey)
                            .build()
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to fetch file from MinIO: " + objectKey, e);
        }
    }
}