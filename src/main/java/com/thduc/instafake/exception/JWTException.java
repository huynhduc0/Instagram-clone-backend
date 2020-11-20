package com.thduc.instafake.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;

public class JWTException extends RuntimeException  {
    HttpStatus httpStatus= HttpStatus.INTERNAL_SERVER_ERROR;;
    String message;

    public JWTException() {

    }

    public JWTException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
