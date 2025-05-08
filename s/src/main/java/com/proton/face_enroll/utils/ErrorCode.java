package com.proton.face_enroll.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    REGISTER_FACE_SUCCESS(200, "Đăng ký khuôn mặt thành công", HttpStatus.OK),
    REGISTER_FACE_FAIL(1000, "Khuôn mặt chưa đủ điều kiện, vui lòng chọn ảnh khác", HttpStatus.BAD_REQUEST),
    REGISTER_FACE_EXISTED(1001, "Khuôn mặt đã tồn tại trong hệ thống", HttpStatus.BAD_REQUEST),
    REGISTER_FACE_PEOPLE_ID_EXISTED(1002, "PeopleId đã tồn tại ", HttpStatus.BAD_REQUEST),
    REGISTER_FACE_PEOPLE_PHONE_EXISTED(1003, "Số điện thoại đã tồn tại", HttpStatus.BAD_REQUEST);
    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;


}
