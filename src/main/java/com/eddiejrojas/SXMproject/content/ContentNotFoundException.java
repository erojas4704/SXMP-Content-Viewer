package com.eddiejrojas.SXMproject.content;

class ContentNotFoundException extends RuntimeException {
    ContentNotFoundException(Long id){
        super(String.format("Could not find content with ID: %d", id));
    }
}
