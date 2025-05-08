package com.proton.face_enroll.dao;


import com.proton.face_enroll.model.Camera;
import java.util.List;

public interface CameraRepository {
    List<Camera> getCameraList();
}

