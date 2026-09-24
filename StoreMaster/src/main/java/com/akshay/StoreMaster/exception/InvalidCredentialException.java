package com.akshay.StoreMaster.exception;

public class InvalidCredentialException extends IllegalArgumentException {
    public InvalidCredentialException(String s) {
        super(s);
    }
}
