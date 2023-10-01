package com.eddiejrojas.sxmproject.service.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super(String.format("User %d not found", id));
    }

    public UserNotFoundException(String username) {
        super(String.format("User %s not found", username));
    }
}
