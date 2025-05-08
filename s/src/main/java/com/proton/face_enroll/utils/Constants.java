package com.proton.face_enroll.utils;

import org.springframework.beans.factory.annotation.Value;

public class Constants {
    public static final String URL_FACE_CHECK_LIVELINESS = "http://10.36.126.159:8080/check_liveness";
    public static final int CODE_CHECK_LIVELESS_SUCCESS = 2;


    public static final String URL_IMAGE = "https://s3.protontech.vn/face-enroll-proton/";
    public static final String SOURCE = "protonenroll";
    public static class SERVICE_FACE {
        public static String SEARCH = "search";
        public static String REGISTER_FACE = "register-face";
    }

    public static class STATUS {
        public static final String STATUS_SUCCESS = "SUCCESS";
        public static final String STATUS_PEOPLE_DO_NOT_EXITS_ERROR = "PEOPLE_DO_NOT_EXITS_ERROR";
        public static final String STATUS_PEOPLE_NOT_FOUND_ERROR = "PEOPLE_NOT_FOUND_ERROR";
    }
}
