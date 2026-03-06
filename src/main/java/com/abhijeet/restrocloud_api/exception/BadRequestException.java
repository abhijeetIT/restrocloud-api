package com.abhijeet.restrocloud_api.exception;

//400
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
