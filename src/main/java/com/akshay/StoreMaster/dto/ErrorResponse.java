package com.akshay.StoreMaster.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {
    private LocalDateTime timeStamp;
    private HttpStatus status;
    private String message;
    private String details;

    public ErrorResponse(LocalDateTime timeStamp, HttpStatus status, String message, String details) {
        this.timeStamp = timeStamp;
        this.status = status;
        this.message = message;
        this.details = details;
    }
}
