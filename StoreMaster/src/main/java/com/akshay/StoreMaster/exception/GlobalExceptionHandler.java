package com.akshay.StoreMaster.exception;

import com.akshay.StoreMaster.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

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

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> handleUserAlreadyExistException(UserAlreadyExistException e) {
        ErrorResponse userExists = new ErrorResponse(
                LocalDateTime.now(),
                e.getMessage(),
                e.toString()
        );
        return new ResponseEntity<>(userExists, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce((e1, e2) -> e1 + ", " + e2)
                .orElse("Validation failed");

        ErrorResponse validationError = new ErrorResponse(
                LocalDateTime.now(),
                errorMessage,
                e.toString()
        );
        return new ResponseEntity<>(validationError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
