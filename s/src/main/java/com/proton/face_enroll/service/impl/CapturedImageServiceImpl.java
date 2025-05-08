package com.proton.face_enroll.service.impl;

import com.proton.face_enroll.dao.CapturedImageRepository;
import com.proton.face_enroll.dto.response.CaptureImageListImageResponse;
import com.proton.face_enroll.model.CapturedImage;
import com.proton.face_enroll.service.CapturedImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CapturedImageServiceImpl implements CapturedImageService {


    private final CapturedImageRepository capturedImageRepo;

    public CapturedImageServiceImpl(CapturedImageRepository capturedImageRepo) {
        this.capturedImageRepo = capturedImageRepo;
    }

    public Long insertCapturedImages(CapturedImage capturedImage) {
        return this.capturedImageRepo.insertCapturedImages(capturedImage);
    }

    @Override
    public List<CaptureImageListImageResponse> getAllCapturedImages() {
        return this.capturedImageRepo.getAllCapturedImages();
    }
}