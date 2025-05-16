package com.lottefn.collateral.domain.exceptions;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
@Log4j2
public class GlobalHandleException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> globalExceptionHandler(
            Exception ex, WebRequest request) {

        if (ex instanceof BusinessException businessException) {

            ExceptionResponse exceptionResponse =
                    new ExceptionResponse(
                            businessException.status,
                            new Date(),
                            businessException.getMessage(),
                            ex.getMessage(),
                            ((ServletWebRequest) request).getRequest().getServletPath());
            return new ResponseEntity<>(exceptionResponse, businessException.status);
        }
        log.error(ex.getMessage());
        ex.printStackTrace();
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(
                        INTERNAL_SERVER_ERROR,
                        new Date(),
                        "Đã có lỗi xảy ra.",
                        ex.getMessage(),
                        ((ServletWebRequest) request).getRequest().getServletPath());
        return new ResponseEntity<>(exceptionResponse, INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ValidDetails validDetails = new ValidDetails();
        Map<String, String> message = new HashMap<>();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) message.put(fieldError.getField(), fieldError.getDefaultMessage());
        validDetails.setMessage(message);
        validDetails.setStatus(HttpStatus.BAD_REQUEST.value());
        validDetails.setTimestamp(new Date());
        validDetails.setError("Not valid exception");
        validDetails.setPath(((ServletWebRequest) request).getRequest().getServletPath());
        return new ResponseEntity<>(validDetails, status);
    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<ExceptionResponse> notFoundException(
            ChangeSetPersister.NotFoundException ex, WebRequest request) {
        ExceptionResponse errorDetails =
                new ExceptionResponse(
                        HttpStatus.NOT_FOUND,
                        new Date(),
                        ex.getMessage(),
                        ex.getMessage(),
                        ((ServletWebRequest) request).getRequest().getServletPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.OK);
    }
}