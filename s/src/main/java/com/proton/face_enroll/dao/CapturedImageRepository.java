package com.proton.face_enroll.dao;


import com.proton.face_enroll.dto.response.CaptureImageListImageResponse;
import com.proton.face_enroll.dto.response.CapturedImageDetailResponse;
import com.proton.face_enroll.model.CapturedImage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CapturedImageRepository {
    long insertCapturedImages(CapturedImage capturedImage);
    List<CaptureImageListImageResponse> getAllCapturedImages();
    CapturedImageDetailResponse getCapturedImageById(long id);
}
