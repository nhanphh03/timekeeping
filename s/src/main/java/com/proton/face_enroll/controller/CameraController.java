package com.proton.face_enroll.controller;

import com.proton.face_enroll.model.Camera;
import java.util.List;

import com.proton.face_enroll.service.CameraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/camera"})
public class CameraController {
    final CameraService cameraService;

    public CameraController(CameraService cameraService) {
        this.cameraService = cameraService;
    }

    @GetMapping({"/getAll"})
    public ResponseEntity<List<Camera>> getCameraList() {
        return ResponseEntity.ok(this.cameraService.getCameraList());
    }
}