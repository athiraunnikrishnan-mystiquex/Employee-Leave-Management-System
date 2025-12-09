package com.mystiquex.employee_leave_management.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

    private Timestamp time;
    private String path;
    private String errorMessage;
    private String errorCode;

}
