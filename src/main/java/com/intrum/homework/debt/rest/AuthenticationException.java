package com.intrum.homework.debt.rest;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AuthenticationException {
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({BadCredentialsException.class})
    public void handleConflict() {
        // Nothing to do
    }
}