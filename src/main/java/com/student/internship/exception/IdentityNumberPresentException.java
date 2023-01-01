package com.student.internship.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IdentityNumberPresentException extends RuntimeException {

    public IdentityNumberPresentException(String message) {
        super(message);
    }
}