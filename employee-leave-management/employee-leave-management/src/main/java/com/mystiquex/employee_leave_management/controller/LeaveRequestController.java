package com.mystiquex.employee_leave_management.controller;

import com.mystiquex.employee_leave_management.dto.LeaveRequestDto;
import com.mystiquex.employee_leave_management.entity.LeaveRequests;
import com.mystiquex.employee_leave_management.service.LeaveRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/leave")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    //create leave request in to database
    @PostMapping("/create")
    public ResponseEntity<?> createLeaveRequest(@RequestBody LeaveRequestDto leaveRequestDto) {
        leaveRequestService.saveLeaveRequest(leaveRequestDto);
        return ResponseEntity.ok("Leave Request Saved Successfully");
    }

    //Fetch Leave request by ID
    @GetMapping("/fetch/{leaveRequestId}")
    public LeaveRequests getLeaveRequestsById(@PathVariable UUID leaveRequestId) {
        return leaveRequestService.getLeaveRequestsById(leaveRequestId);
    }

    //Filter the leave requests based on employeeId, Leave status, and start date
    @GetMapping("/filter")
    public ResponseEntity<List<LeaveRequests>> getLeaveRequestByFilter(
            @RequestParam(required = false) UUID employeeId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate) {
        return ResponseEntity.ok(leaveRequestService.getLeaveRequestByFilter(employeeId, status, fromDate, toDate));
    }

    //Update leave requests details like Leave type, reason, start date, end date using leaveRequestId
    @PutMapping("update/{leaveRequestId}")
    public ResponseEntity<?> updateDetails(@PathVariable UUID leaveRequestId, @RequestBody LeaveRequestDto leaveRequestDto) {
         leaveRequestService.updateDetails(leaveRequestId, leaveRequestDto);
        return ResponseEntity.ok("Details Updated Successfully");
    }

    //Update leave status to APPROVED OR REJECTED
    @PatchMapping("/{leaveRequestId}/leaveStatus")
    public ResponseEntity<?> updateLeaveStatus(@PathVariable UUID leaveRequestId, @RequestParam("status") String leaveStatus) {
        leaveRequestService.updateLeaveStatus(leaveRequestId, leaveStatus);
        return ResponseEntity.ok("Leave Status Updated Successfully");
    }

    //Delete leave request by ID
    @DeleteMapping("delete/{leaveRequestId}")
    public ResponseEntity<?> deleteLeaveRequest(@PathVariable UUID leaveRequestId) {
        leaveRequestService.deleteLeaveRequest(leaveRequestId);
        return ResponseEntity.ok("Leave Request Deleted Successfully");
    }

}
