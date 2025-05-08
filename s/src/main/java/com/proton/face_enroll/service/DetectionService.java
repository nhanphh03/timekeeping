package com.proton.face_enroll.service;

import com.proton.face_enroll.model.Detection;
import com.proton.face_enroll.model.DetectionItem;
import java.util.List;
import org.springframework.stereotype.Service;

public interface DetectionService {
    List<DetectionItem> getDetectionItem(String key);

    Long insertDetection(Detection detect);
}

