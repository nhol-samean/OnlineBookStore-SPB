package com.example.project_bsdb.exception;

public class ResourceNotFoundException extends RuntimeException {
    public  ResourceNotFoundException(String message) {
        super(message);
    }
}
