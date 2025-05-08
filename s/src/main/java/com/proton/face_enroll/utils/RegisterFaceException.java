package com.proton.face_enroll.utils;

import lombok.Getter;

@Getter
public class RegisterFaceException extends RuntimeException {

    public RegisterFaceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    private ErrorCode errorCode;
}
