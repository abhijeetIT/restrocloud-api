package com.abhijeet.restrocloud_api.exception;

//404
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
