package com.proton.face_enroll.utils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BEException extends Exception {
    private static final long serialVersionUID = 1L;
    private String errorCode;
    private String message;

    public BEException(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

}
