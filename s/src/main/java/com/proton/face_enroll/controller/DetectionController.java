package com.proton.face_enroll.controller;

import com.proton.face_enroll.model.Detection;
import com.proton.face_enroll.model.DetectionItem;
import com.proton.face_enroll.service.DetectionService;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/detection"})
public class DetectionController {

    private final DetectionService detectionService;

    public DetectionController( DetectionService detectionService) {
        this.detectionService = detectionService;
    }

    @GetMapping({"/"})
    public ResponseEntity<List<DetectionItem>> getDetectionItem(@RequestParam(required = false,name = "key") String key) {
        return ResponseEntity.ok(this.detectionService.getDetectionItem(key));
    }

    @PostMapping({"/create"})
    public ResponseEntity<Long> insertDetection(@RequestBody Detection detect) {
        return ResponseEntity.ok(this.detectionService.insertDetection(detect));
    }
}
