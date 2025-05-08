package nhanph.timekeeping.processor.util;

import org.springframework.beans.factory.annotation.Value;

/**
 * @Package: nhanph.timekeeping.util
 * @author: nhanph
 * @date: 05/08/2025 2025
 * @Copyright: @nhanph
 */

public class Constants {

    @Value("${services.face_service}")
    public static String faceService;

    @Value("${services.minio_service}")
    private static String minioService;

    public static class SERVICE_FACE {
        public static String URL_FACE = faceService;
        public static String SEARCH = "/search";
        public static String REGISTER_FACE = "/register-face";
        public static String REMOVE_FACE = "/api/v1/delete";
    }

    public static final String DATA_SOURCE = "timekeeping_face";

    public static final String STATUS_SUCCESS = "SUCCESS";
    public static final String RECOGNITION_STATUS_A = "A";
    public static final String RECOGNITION_STATUS_B = "B";

    public static class SERVICE_MINIO {
        public static String URL_MINIO = minioService;
        public static String GET = "/";
        public static String UPLOAD = "/upload";
        public static String UPLOAD_BASE64 = "/upload/base64";
    }



}
