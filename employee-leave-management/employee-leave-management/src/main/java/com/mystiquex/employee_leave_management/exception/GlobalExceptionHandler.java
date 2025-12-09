package com.mystiquex.employee_leave_management.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FormValidationException.class)
    public ResponseEntity<ErrorResponse> handleFormValidationException(FormValidationException ex, HttpServletRequest request){
        ErrorResponse error = new ErrorResponse(
                Timestamp.valueOf(LocalDateTime.now()),
                request.getRequestURI(),
                ex.getMessage(),
                ex.getErrorCode()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex, HttpServletRequest request){
        ErrorResponse error = new ErrorResponse(
                Timestamp.valueOf(LocalDateTime.now()),
                request.getRequestURI(),
                ex.getMessage(),
                ex.getErrorCode()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,HttpServletRequest request){

        StringBuilder message = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            message.append(error.getField()).append(": ")
                    .append(error.getDefaultMessage())
                    .append("; ");
        });

        ErrorResponse error = new ErrorResponse(
                Timestamp.valueOf(LocalDateTime.now()),
                request.getRequestURI(),
                message.toString(),
                "VALIDATION_ERROR"
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex, HttpServletRequest request){
        ErrorResponse error = new ErrorResponse(
                Timestamp.valueOf(LocalDateTime.now()),
                request.getRequestURI(),
                ex.getMessage(),
                "NOT_FOUND"
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}