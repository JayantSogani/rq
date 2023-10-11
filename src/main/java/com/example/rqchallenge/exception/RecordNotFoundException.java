package com.example.rqchallenge.exception;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String string) {
        super(string);
    }
}
