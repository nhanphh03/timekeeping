package com.proton.face_enroll.service;

import com.proton.face_enroll.dto.response.CaptureImageListImageResponse;
import com.proton.face_enroll.model.CapturedImage;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CapturedImageService {
    Long insertCapturedImages(CapturedImage capturedImage);
    List<CaptureImageListImageResponse> getAllCapturedImages();
}

