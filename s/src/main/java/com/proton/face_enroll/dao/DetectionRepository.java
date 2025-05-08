package com.proton.face_enroll.dao;


import com.proton.face_enroll.model.Detection;
import com.proton.face_enroll.model.DetectionItem;
import java.util.List;

public interface DetectionRepository {
    List<DetectionItem> getDetectionItem(String key);

    long insertDetection(Detection detect);
}

