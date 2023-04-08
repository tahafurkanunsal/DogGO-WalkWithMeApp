package com.tahafurkan.sandbox.Walk.With.Me.App.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ReservationNotFinishableException extends RuntimeException{
    public ReservationNotFinishableException(String message) {
        super(message);
    }
}
