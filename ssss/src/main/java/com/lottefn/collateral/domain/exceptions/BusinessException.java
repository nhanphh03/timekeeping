package com.lottefn.collateral.domain.exceptions;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {
    HttpStatus status;

    public BusinessException(HttpStatus status, ErrorMessage msg) {
        super(msg.val);
        this.status = status;
    }

    public BusinessException(HttpStatus status, String msg) {
        super(msg);
        this.status = status;
    }
}
