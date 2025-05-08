package com.proton.face_enroll.service;

import com.proton.face_enroll.model.Camera;
import java.util.List;
import org.springframework.stereotype.Service;

public interface CameraService {
    List<Camera> getCameraList();
}
