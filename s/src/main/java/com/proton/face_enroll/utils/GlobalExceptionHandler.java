package com.proton.face_enroll.utils;

import com.proton.face_enroll.dto.response.BasicResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RegisterFaceException.class)
    public ResponseEntity<?> handleEntityNotFoundException(RegisterFaceException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        BasicResponse response = new BasicResponse();
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        response.setStatus("ERROR");
        return ResponseEntity.status(errorCode.getStatusCode()).body(response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return BasicResponse.RESPONSE_ERROR(1500, ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
}
