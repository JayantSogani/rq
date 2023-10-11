package com.example.rqchallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleRecordNotFoundException(RecordNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(TooManyRequestsException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    @ResponseBody
    public String handleTooManyRequestsException(TooManyRequestsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(DefaultFallbackException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleDefaultException(DefaultFallbackException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(RecordCreationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleDefaultException(RecordCreationException ex) {
        return ex.getMessage();
    }
}
