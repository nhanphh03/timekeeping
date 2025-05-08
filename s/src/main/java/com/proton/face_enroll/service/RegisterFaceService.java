package com.proton.face_enroll.service;

import com.proton.face_enroll.dto.request.RegisterFaceRequest;
import org.springframework.stereotype.Service;

@Service
public interface RegisterFaceService {
    String registerFace(RegisterFaceRequest registerFaceRequest);
}
