package com.proton.face_enroll.controller;

import com.proton.face_enroll.dto.odooreq.RegisterFaceRequest;
import com.proton.face_enroll.dto.odooreq.RemoveFaceRequest;
import com.proton.face_enroll.dto.odooreq.SearchFaceRequest;
import com.proton.face_enroll.dto.odoores.RegisterFaceResponse;
import com.proton.face_enroll.dto.odoores.RemoveFaceResponse;
import com.proton.face_enroll.dto.odoores.SearchFaceResponse;
import com.proton.face_enroll.dto.response.BasicResponse;
import com.proton.face_enroll.service.FaceEnrollService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code @Package:} com.proton.face_enroll.controller
 * {@code @author:} nhanph
 * {@code @date:} 3/18/2025 2025
 * {@code @Copyright:} @nhanph
 */

@RestController
@RequestMapping("/api/face")
@RequiredArgsConstructor
public class FaceEnrollController {

    private final FaceEnrollService faceEnrollService;

    @PostMapping("/create")
    public ResponseEntity<RegisterFaceResponse> registerFace(@Valid @RequestBody RegisterFaceRequest request,
                                                             @RequestHeader(value = "Authorization") String authorization) {

        return ResponseEntity.ok(faceEnrollService.registerFace(request, authorization));
    }

    @PostMapping("/remove")
    public ResponseEntity<RemoveFaceResponse> removeFace(@Valid @RequestBody RemoveFaceRequest request,
                                                         @RequestHeader(value = "Authorization") String authorization) {
        return ResponseEntity.ok(faceEnrollService.removeFace(request, authorization));
    }

    @PostMapping("/search")
    public ResponseEntity<SearchFaceResponse> searchFace(@Valid @RequestBody SearchFaceRequest request,
                                                         @RequestHeader(value = "Authorization") String authorization) {
        return ResponseEntity.ok(faceEnrollService.searchFace(request, authorization));
    }
}
