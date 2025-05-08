package com.proton.face_enroll.utils;

import com.proton.face_enroll.base.BaseResponse;
import com.proton.face_enroll.dto.odoores.RegisterFaceResponse;
import com.proton.face_enroll.dto.searchface.SearchFaceRequestPy;
import com.proton.face_enroll.dto.searchface.SearchFaceResponsePy;
import lombok.extern.slf4j.Slf4j;

/**
 * {@code @Package:} com.proton.face_enroll.controller
 * {@code @author:} nhanph
 * {@code @date:} 3/18/2025 2025
 * {@code @Copyright:} @nhanph
 */
@Slf4j
public class ResponseUtil {
    public static <T extends BaseResponse> T buildErrorResponse(T response, String resCode, String resMsg) {
        response.setResCode(resCode);
        response.setResMsg(resMsg);
        return response;
    }

    public static boolean isAuthorizationInvalid(String authorization) {
        return authorization == null || authorization.isEmpty();
    }

    public static String getBase64Image(String imagePath) {
        try {
            return ApiClient.downloadFileEncodeToBase64(imagePath);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public static SearchFaceResponsePy searchFace(String base64Image, String source, ApiClient apiClient, String urlFaceSearch) {
        SearchFaceRequestPy searchRequest = SearchFaceRequestPy.builder().image(base64Image).source(source).build();
        return apiClient.sendPostRequest(urlFaceSearch, searchRequest, SearchFaceResponsePy.class);
    }
}
