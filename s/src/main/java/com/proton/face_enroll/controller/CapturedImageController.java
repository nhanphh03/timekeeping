package com.proton.face_enroll.controller;

import com.proton.face_enroll.dto.response.BasicResponse;
import com.proton.face_enroll.model.CapturedImage;
import com.proton.face_enroll.service.CapturedImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/capturedImage"})
public class CapturedImageController {

    final CapturedImageService capturedImageService;

    public CapturedImageController(CapturedImageService capturedImageService) {
        this.capturedImageService = capturedImageService;
    }

    @PostMapping({"/create"})
    public ResponseEntity<Long> insertCapturedImages(@RequestBody CapturedImage capturedImage) {
        return ResponseEntity.ok(this.capturedImageService.insertCapturedImages(capturedImage));
    }

    @GetMapping({"/get-all"})
    public ResponseEntity<?> getAllCapturedImages(@RequestHeader(value = "Authorization") String authorization) {
        if (authorization == null || authorization.isEmpty()) {
            return BasicResponse.RESPONSE_ERROR(2000, "Mã xác thực là bắt buộc");
        } else if (!authorization.equals("dwKddrv9HhHMU0ylpsxpuoZPMkr6KaxDWORJPmss")) {
            return BasicResponse.RESPONSE_ERROR(2001, "Sai mã xác thực");
        } else return ResponseEntity.ok(this.capturedImageService.getAllCapturedImages());
    }


}