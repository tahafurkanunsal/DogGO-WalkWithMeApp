package com.tahafurkan.sandbox.Walk.With.Me.App.exception;

import org.springframework.http.HttpStatus;

public class APIException extends RuntimeException{
    private HttpStatus httpStatus;
    private String message;

    public APIException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
