package nhanph.timekeeping.processor.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Package: nhanph.timekeeping.util
 * @author: nhanph
 * @date: 05/08/2025 2025
 * @Copyright: @nhanph
 */

@Component
public class Constants {

    @Value("${services.face_service}")
    private String faceService;

    @Value("${services.minio_service}")
    private String minioService;

    public static class FACE_SERVICE {
        public static String urlFace;
        public static String search = "/search";
        public static final String registerFace = "/register-face";
        public static String removeFace = "/api/v1/delete";
    }

//    @PostConstruct là một annotation trong Spring, dùng để chỉ định phương thức sẽ được gọi ngay sau khi
//    Spring hoàn tất việc inject dependencies vào bean. Trong trường hợp này,
//    bạn có thể sử dụng @PostConstruct để khởi tạo giá trị cho các thuộc tính static
//    sau khi Spring đã inject faceService.

    @PostConstruct
    public void init() {
        FACE_SERVICE.urlFace = faceService;
        MINIO_SERVICE.urlMinio = minioService;
    }

    public static final String DATA_SOURCE = "timekeeping_face";
    public static final String STATUS_SUCCESS = "SUCCESS";
    public static final String RECOGNITION_STATUS_A = "A";
    public static final String RECOGNITION_STATUS_B = "B";

    public static final String STATUS_ACTIVE = "2";
    public static final String STATUS_INACTIVE = "0";
    public static final String STATUS_WELCOME = "1";
    public static final String DESC_WELCOME = "Welcome";
    public static final String DESC_INSUFFICIENT = "Insufficient";
    public static final String DESC_DONE = "Done";

    public static class MINIO_SERVICE {
        public static String urlMinio;
        public static String GET = "/";
        public static String UPLOAD = "/upload";
        public static String UPLOAD_BASE64 = "/upload/base64";
    }
    public static final long TIME_WORK = 8 * 60 * 60 * 1000;
}

