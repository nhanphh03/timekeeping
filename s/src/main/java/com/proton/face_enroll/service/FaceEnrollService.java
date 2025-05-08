package com.proton.face_enroll.service;

import com.proton.face_enroll.dto.odooreq.RegisterFaceRequest;
import com.proton.face_enroll.dto.odooreq.RemoveFaceRequest;
import com.proton.face_enroll.dto.odooreq.SearchFaceRequest;
import com.proton.face_enroll.dto.odoores.RegisterFaceResponse;
import com.proton.face_enroll.dto.odoores.RemoveFaceResponse;
import com.proton.face_enroll.dto.odoores.SearchFaceResponse;

/**
 * {@code @Package:} com.proton.face_enroll.dto.odoores
 * {@code @author:} nhanph
 * {@code @date:} 3/18/2025 2025
 * {@code @Copyright:} @nhanph
 */
public interface FaceEnrollService {

    RegisterFaceResponse registerFace(RegisterFaceRequest registerFaceRequest, String authorization);

    SearchFaceResponse searchFace(SearchFaceRequest searchFaceRequest, String authorization);

    RemoveFaceResponse removeFace(RemoveFaceRequest removeFaceRequest, String authorization);

}
