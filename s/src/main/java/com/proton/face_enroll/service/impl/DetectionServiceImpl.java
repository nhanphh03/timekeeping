package com.proton.face_enroll.service.impl;

import com.proton.face_enroll.dao.DetectionRepository;
import com.proton.face_enroll.model.Detection;
import com.proton.face_enroll.model.DetectionItem;
import java.util.List;

import com.proton.face_enroll.service.DetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DetectionServiceImpl implements DetectionService {
    @Autowired
    DetectionRepository detectionRepo;

    public DetectionServiceImpl() {
    }

    public List<DetectionItem> getDetectionItem(String key) {
        return this.detectionRepo.getDetectionItem(key);
    }

    public Long insertDetection(Detection detect) {
        return this.detectionRepo.insertDetection(detect);
    }
}

