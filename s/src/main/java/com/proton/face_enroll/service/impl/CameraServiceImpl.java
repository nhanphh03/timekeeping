package com.proton.face_enroll.service.impl;


import com.proton.face_enroll.dao.CameraRepository;
import com.proton.face_enroll.model.Camera;
import java.util.List;

import com.proton.face_enroll.service.CameraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CameraServiceImpl implements CameraService {

    final
    CameraRepository cameraRepo;

    public CameraServiceImpl( CameraRepository cameraRepo) {
        this.cameraRepo = cameraRepo;
    }

    public List<Camera> getCameraList() {
        return this.cameraRepo.getCameraList();
    }
}
