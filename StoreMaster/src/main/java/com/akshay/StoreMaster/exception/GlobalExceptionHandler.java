package com.akshay.StoreMaster.exception;

import com.akshay.StoreMaster.entity.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleProductNotFoundException(ProductNotFoundException e) {
        ErrorResponse productNotFound = new ErrorResponse(
                LocalDateTime.now(),
                e.getMessage(),
                e.toString()
        );
        return new ResponseEntity<>(productNotFound, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<?> handleInvalidCredentialException(InvalidCredentialException e) {
        ErrorResponse invalidCredential = new ErrorResponse(
                LocalDateTime.now(),
                e.getMessage(),
                e.toString()
        );
        return new ResponseEntity<>(invalidCredential, HttpStatus.UNAUTHORIZED);
    }
}
