package com.proton.face_enroll.dao;

import com.proton.face_enroll.dto.odooreq.RegisterFaceRequest;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterFaceRepository {
    void registerFace(RegisterFaceRequest registerFaceRequest);
}
