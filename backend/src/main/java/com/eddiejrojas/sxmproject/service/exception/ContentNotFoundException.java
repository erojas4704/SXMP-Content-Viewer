package com.eddiejrojas.sxmproject.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ContentNotFoundException extends RuntimeException {
    public ContentNotFoundException(Long id){
        super(String.format("Could not find content with ID: %d", id));
    }
}
