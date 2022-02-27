package com.eddiejrojas.SXMproject.users;

public class UserNotFoundException extends RuntimeException{
    UserNotFoundException(Long id){
        super(String.format("User %d not found", id));
    }
}
