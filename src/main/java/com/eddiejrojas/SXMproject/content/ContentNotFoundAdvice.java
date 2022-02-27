package com.eddiejrojas.SXMproject.content;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ContentNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(ContentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String contentNotFoundHandler(ContentNotFoundException err){
        return err.getMessage();
    }
}
