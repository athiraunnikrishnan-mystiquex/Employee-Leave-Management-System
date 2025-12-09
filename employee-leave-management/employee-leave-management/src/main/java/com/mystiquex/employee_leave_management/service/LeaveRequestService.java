package com.mystiquex.employee_leave_management.service;

import com.mystiquex.employee_leave_management.dto.LeaveRequestDto;
import com.mystiquex.employee_leave_management.entity.LeaveRequests;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LeaveRequestService {

    void saveLeaveRequest(LeaveRequestDto leaveRequestDto);
    LeaveRequests getLeaveRequestsById(UUID leaveRequestId);
    void updateDetails(UUID leaveRequestId,LeaveRequestDto leaveRequestDto);
    void updateLeaveStatus(UUID leaveRequestId, String status);
    void deleteLeaveRequest(UUID leaveRequestId);
    List<LeaveRequests> getLeaveRequestByFilter(UUID employeeId, String status, LocalDate fromDate, LocalDate toDate);
}
