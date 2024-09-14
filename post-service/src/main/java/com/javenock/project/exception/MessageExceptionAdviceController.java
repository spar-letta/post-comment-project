package com.javenock.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MessageExceptionAdviceController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MessageException.class)
    public Map<String, String> handleNoPostsAvailableException(MessageException exception){
        Map<String, String> mapError = new HashMap<>();
        mapError.put("System message", exception.getMessage());
        return mapError;
    }
}
