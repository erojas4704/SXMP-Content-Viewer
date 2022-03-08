package com.eddiejrojas.SXMproject.users.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id){
        super(String.format("User %d not found", id));
    }
}
