package com.student.internship.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PhoneNumberPresentException extends RuntimeException {

    public PhoneNumberPresentException(String message) {
        super(message);
    }
}
