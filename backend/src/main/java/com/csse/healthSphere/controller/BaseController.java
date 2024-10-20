package com.csse.healthSphere.controller;

import com.csse.healthSphere.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class BaseController {
    private final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * Handle ResourceNotFoundException
     *
     * @param e the exception
     * @return a response containing an error message
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleResourceNotFoundException(ResourceNotFoundException e) {
        logger.error("Resource not found", e);
        return new ResponseEntity<>(Map.of("message",e.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * Handle all exceptions authentication errors
     *
     * @param e the exception
     * @return a response containing an error message
     */
    @ExceptionHandler({UserPrincipalNotFoundException.class, BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity<Map<String,String>> handleAuthExceptions(Exception e){
        logger.error("User is not authenticated", e);
        return new ResponseEntity<>(Map.of("message", "Unauthorized"), HttpStatus.UNAUTHORIZED);
    }


    /**
     * Handle all exceptions thrown by the server
     *
     * @param e the exception
     * @return a response containing an error message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>> handleExceptions(Exception e){
        logger.error("Request Failed", e);
        return new ResponseEntity<>(Map.of("message", "Internal server error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
