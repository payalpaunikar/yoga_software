package com.yoga.exception;

public class EmployeeAlreadyExitException extends RuntimeException{
    public EmployeeAlreadyExitException(String message) {
        super(message);
    }
}
