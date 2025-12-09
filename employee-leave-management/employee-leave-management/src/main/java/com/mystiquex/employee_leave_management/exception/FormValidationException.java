package com.mystiquex.employee_leave_management.exception;

import lombok.Getter;

@Getter
public class FormValidationException extends RuntimeException{
    private final String errorCode;
    public FormValidationException(String message, String errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
