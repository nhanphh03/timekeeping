package com.proton.face_enroll.utils;

import com.proton.face_enroll.base.BaseResponse;

/**
 * @Package: com.proton.face_enroll.utils
 * @author: nhanph
 * @date: 3/18/2025 2025
 * @Copyright: @nhanph
 */
public class FaceServiceUtil {

    public static <T extends BaseResponse> T validateAndProcessRequest(
            String authorization,
            String baseAuthorization,
            String imagePath,
            T response) {

        // Kiểm tra authorization
        if (ResponseUtil.isAuthorizationInvalid(authorization)) {
            return ResponseUtil.buildErrorResponse(response, "401", "Nhập mã xác thực");
        }

        if (!baseAuthorization.equals(authorization)) {
            return ResponseUtil.buildErrorResponse(response, "403", "Sai mã xác thực");
        }

        // Lấy base64 image
        String base64Image = ResponseUtil.getBase64Image(imagePath);
        if (base64Image == null) {
            return ResponseUtil.buildErrorResponse(response, "400", "Không lấy được dữ liệu hình ảnh");
        }
        response.setResCode("200");
        return response;
    }
}
