package com.mystiquex.employee_leave_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class LeaveRequestDto {

    private UUID employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String leaveType;
    private String leaveReason;


}
