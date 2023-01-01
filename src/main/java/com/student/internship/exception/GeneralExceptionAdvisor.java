package com.student.internship.exception;

import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GeneralExceptionAdvisor  {

    @ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error ->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<?> studentNotFoundExceptionHandler(StudentNotFoundException exception)  {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(PhoneNumberPresentException.class)
    public ResponseEntity<?> phoneNumberPresentExceptionHandler(PhoneNumberPresentException exception)  {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IdentityNumberPresentException.class)
    public ResponseEntity<?> phoneNumberPresentExceptionHandler(IdentityNumberPresentException exception)  {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> generalExceptionHandler(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
