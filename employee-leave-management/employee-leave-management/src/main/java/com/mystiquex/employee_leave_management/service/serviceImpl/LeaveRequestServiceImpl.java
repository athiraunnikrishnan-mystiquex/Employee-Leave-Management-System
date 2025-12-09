package com.mystiquex.employee_leave_management.service.serviceImpl;

import com.mystiquex.employee_leave_management.dto.LeaveRequestDto;
import com.mystiquex.employee_leave_management.entity.Employees;
import com.mystiquex.employee_leave_management.entity.LeaveRequests;
import com.mystiquex.employee_leave_management.entity.enums.LeaveStatus;
import com.mystiquex.employee_leave_management.exception.BusinessException;
import com.mystiquex.employee_leave_management.repository.EmployeeRepository;
import com.mystiquex.employee_leave_management.repository.LeaveRequestsRepository;
import com.mystiquex.employee_leave_management.service.LeaveRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LeaveRequestServiceImpl implements LeaveRequestService {

    public final LeaveRequestsRepository leaveRequestsRepository;
    public final EmployeeRepository employeeRepository;

    public void saveLeaveRequest(LeaveRequestDto leaveRequestDto) {
        //Find Employee using employeeId
        Employees employees = employeeRepository.findEmployeeByIdAndActive(leaveRequestDto.getEmployeeId()).orElseThrow(() -> new RuntimeException("Employee Not found for ID: "+leaveRequestDto.getEmployeeId()));

        //Checking Start date is after the end date
        if (leaveRequestDto.getStartDate().isAfter(leaveRequestDto.getEndDate())) {
            throw new RuntimeException("Start date must before end date");
        }

        //Save Leave Request
        LeaveRequests leaveRequests = new LeaveRequests(leaveRequestDto.getStartDate(),leaveRequestDto.getEndDate(),
                leaveRequestDto.getLeaveType(),LeaveStatus.PENDING,leaveRequestDto.getLeaveReason(),Timestamp.valueOf(LocalDateTime.now()),employees);
        leaveRequestsRepository.save(leaveRequests);
    }

    @Override
    public LeaveRequests getLeaveRequestsById(UUID leaveRequestId) {
        //Fetch leave request by id
        return leaveRequestsRepository.findById(leaveRequestId).orElseThrow(() -> new RuntimeException("No Leave Found for ID: "+leaveRequestId));
    }

    @Override
    public void updateDetails(UUID leaveRequestId, LeaveRequestDto leaveRequestDto) {

        //Update fields like start date, end date,Leave type,leave reason
        LeaveRequests leaveRequest = leaveRequestsRepository.findById(leaveRequestId).orElseThrow(() -> new RuntimeException("Leave Not Found for ID: "+leaveRequestId));
        if (LeaveStatus.PENDING.equals(leaveRequest.getLeaveStatus())) {
            leaveRequest.setStartDate(leaveRequestDto.getStartDate());
            leaveRequest.setEndDate(leaveRequestDto.getEndDate());
            leaveRequest.setLeaveType(leaveRequestDto.getLeaveType());
            leaveRequest.setLeaveReason(leaveRequestDto.getLeaveReason());
            leaveRequestsRepository.save(leaveRequest);
        }else{
            throw new BusinessException("The Leave Status is not Pending","STATUS_NOT_PENDING");
        }
    }

    @Override
    public void updateLeaveStatus(UUID leaveRequestId, String leaveStatus) {

        //Changing leave status to APPROVED or PENDING
        LeaveRequests leaveRequest = leaveRequestsRepository.findById(leaveRequestId).orElseThrow(() -> new RuntimeException("Leave Not Found for ID: "+leaveRequestId));
        if (LeaveStatus.valueOf(leaveStatus).equals(LeaveStatus.APPROVED) && leaveRequest.getLeaveStatus().equals(LeaveStatus.APPROVED)) {
            throw new BusinessException("Already employeeStatus is APPROVED", "STATUS_ALREADY_APPROVED");
        }

        //Cannot change the status ot Pendng if it already approved or rejected
        if (!leaveRequest.getLeaveStatus().equals(LeaveStatus.PENDING) && LeaveStatus.valueOf(leaveStatus).equals(LeaveStatus.PENDING)) {
            throw new BusinessException("Status cannot be change to Pending if it once changed to APPROVED/REJECTED","STATUS_IS_APPROVED_OR_REJECTED");
        } else {
            leaveRequest.setLeaveStatus(LeaveStatus.valueOf(leaveStatus));
        }
        leaveRequestsRepository.save(leaveRequest);
    }

    @Override
    public void deleteLeaveRequest(UUID leaveRequestId) {

        //Delete Leave Request by ID
        LeaveRequests leaveRequest = leaveRequestsRepository.findById(leaveRequestId).orElseThrow(() ->new RuntimeException("There is no leave request for this id"+leaveRequestId));
        leaveRequestsRepository.deleteById(leaveRequestId);
    }

    @Override
    public List<LeaveRequests> getLeaveRequestByFilter(UUID employeeId, String leaveStatus, LocalDate fromDate, LocalDate toDate) {
        //Filtering Leave request based on Leave status, Start date
        List<LeaveRequests> leaveRequestsList = leaveRequestsRepository.findAll().stream().filter(leaveRequests -> employeeId == null || leaveRequests.getEmployees().getEmployeeId().equals(employeeId))
                .filter(leaveRequests -> leaveStatus == null || leaveStatus.isBlank() || leaveRequests.getLeaveStatus().equals(LeaveStatus.valueOf(leaveStatus)))
                .filter(leaveRequests -> fromDate == null || fromDate.toString().isBlank() || leaveRequests.getStartDate().isAfter(fromDate))
                .filter(leaveRequests -> toDate == null || toDate.toString().isBlank() || leaveRequests.getStartDate().isBefore(toDate)).toList();
        return leaveRequestsList;
    }
}
