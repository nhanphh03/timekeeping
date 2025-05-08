package com.proton.face_enroll.utils;


import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import java.io.ByteArrayInputStream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * @author nhanph
 */
@Slf4j
@Repository
public class MinioUtil {

    public String uploadImage(String fileName, byte[] data) throws Exception {
        String endpoint = "http://192.168.1.19:9000";
        String bucketName = "face-enroll";
        String contentType = "image/jpg";
        String accessKey = "Nhan21..";
        String secretKey = "Nhan21..";

        try {
            MinioClient minioClient = MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey).build();
            ByteArrayInputStream bs = new ByteArrayInputStream(data);
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(bs, data.length, -1L)
                    .contentType(contentType).build());
        } catch (Exception var9) {
            log.error("Failed to upload image to minio", var9);
        }

        return bucketName;
    }
}
