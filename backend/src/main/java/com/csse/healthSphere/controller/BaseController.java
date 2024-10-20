package com.csse.healthSphere.controller;

import com.csse.healthSphere.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class BaseController {
    /**
     * Handle ResourceNotFoundException
     *
     * @param e the exception
     * @return a response containing an error message
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(Map.of("message",e.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * Handle all exceptions thrown by the server
     *
     * @param e the exception
     * @return a response containing an error message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>> handleExceptions(Exception e){
        return new ResponseEntity<>(Map.of("message", "Internal server error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
