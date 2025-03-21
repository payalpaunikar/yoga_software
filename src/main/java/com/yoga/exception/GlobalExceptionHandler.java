package com.yoga.exception;

import com.yoga.dto.response.BatchResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleUserNotFoundException(UserNotFoundException exception){
      return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(EmployeeAlreadyExitException.class)
    public ResponseEntity<Map<String,Object>> handleEmployeeAlreadyExitException(EmployeeAlreadyExitException exception){
        return buildResponse(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<Map<String,Object>> handleInvalidCredentialException(InvalidCredentialException exception){
        return buildResponse(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String,Object>> handleUnauthorizedException(UnauthorizedException exception){
        return buildResponse(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(BatchNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleBatchNotFoundException(BatchNotFoundException exception){
        return buildResponse(HttpStatus.CONFLICT, exception.getMessage());
    }

    // Handle validation errors (e.g., @NotNull, @NotBlank)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleRuntimeException(MethodArgumentNotValidException exception){
        String message = "";
        for(FieldError error : exception.getBindingResult().getFieldErrors()){
           message = error.getDefaultMessage();
        }
        return buildResponse(HttpStatus.BAD_REQUEST,message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong: " + ex.getMessage());
    }

    public ResponseEntity<Map<String,Object>> buildResponse(HttpStatus httpStatus, String message){
        Map<String,Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status",httpStatus.value());
        response.put("error",httpStatus.getReasonPhrase());
        response.put("message",message);
        return new ResponseEntity<>(response,httpStatus);
    }

}
