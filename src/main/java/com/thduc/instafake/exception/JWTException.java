package com.thduc.instafake.exception;

import org.springframework.http.HttpStatus;

public class JWTException extends RuntimeException  {
    HttpStatus httpStatus;
    String message;

    public JWTException() {

    }

    public JWTException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
