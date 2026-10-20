package com.akshay.StoreMaster.exception;

import com.akshay.StoreMaster.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String SAFE_DETAILS = "See server logs for details.";

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleProductNotFoundException(ProductNotFoundException e) {
        log.warn("Product not found", e);
        ErrorResponse productNotFound = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND,
                e.getMessage(),
                SAFE_DETAILS);
        return new ResponseEntity<>(productNotFound, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<?> handleInvalidCredentialException(InvalidCredentialException e) {
        log.warn("Invalid credentials", e);
        ErrorResponse invalidCredential = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                SAFE_DETAILS);
        return new ResponseEntity<>(invalidCredential, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> handleUserAlreadyExistException(UserAlreadyExistException e) {
        log.warn("User already exists", e);
        ErrorResponse userExists = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT,
                e.getMessage(),
                SAFE_DETAILS);
        return new ResponseEntity<>(userExists, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e) {
        log.warn("Request validation failed", e);
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce((e1, e2) -> e1 + ", " + e2)
                .orElse("Validation failed");

        ErrorResponse validationError = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                errorMessage,
                SAFE_DETAILS);
        return new ResponseEntity<>(validationError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<?> handleCartNotFound(CartNotFoundException ex) {
        log.warn("Cart not found", ex);
        ErrorResponse cartNotFound = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                SAFE_DETAILS);
        return new ResponseEntity<>(cartNotFound, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {
        log.warn("Resource not found", ex);
        ErrorResponse resourceNotFound = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                SAFE_DETAILS);
        return new ResponseEntity<>(resourceNotFound, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception ex) {
        log.error("Unexpected server error", ex);
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred",
                SAFE_DETAILS);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
