package com.enroll.enroll_minio.controller;
import com.enroll.enroll_minio.dto.UploadFileDTO;
import com.enroll.enroll_minio.service.MinIOService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;

/**
 * {@code @Package:} com.enroll.enroll_minio.controller
 * {@code @author:} nhanph
 * {@code @date:} 3/24/2025 2025
 * {@code @Copyright:} @nhanph
 */
@RestController
@RequestMapping("/api/v1/file")
public class FileController {
    private final MinIOService minioService;

    public FileController(MinIOService minioService) {
        this.minioService = minioService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file,
                                              @RequestParam("file_url") String fileUrl,
                                              @RequestParam("file_name") @Nullable  String fileName ) {
        try {
            return ResponseEntity.ok(minioService.uploadFile(file, fileUrl, fileName));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to upload file: " + e.getMessage());
        }
    }

    @PostMapping("/upload/base64")
    public ResponseEntity<String> uploadImageBase64(@RequestBody UploadFileDTO uploadFileDTO) {
        try {
            return ResponseEntity.ok(minioService.uploadFileBase64Img(uploadFileDTO));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to upload file: " + e.getMessage());
        }
    }

    @GetMapping("/**")
    public ResponseEntity<InputStreamResource> getImage(HttpServletRequest request) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(minioService.getFile(request.getRequestURI())));
        }  catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}