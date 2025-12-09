package com.mystiquex.employee_leave_management.entity;

import com.mystiquex.employee_leave_management.entity.enums.LeaveStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor

@Table(name = "leave_requests")
public class LeaveRequests {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID leaveRequestId;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "type")
    private String leaveType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private LeaveStatus leaveStatus;

    @Column(name = "leave_reason")
    private String leaveReason;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employees employees;

    public LeaveRequests(){}

    public LeaveRequests(LocalDate startDate, LocalDate endDate, String leaveType, LeaveStatus leaveStatus, String leaveReason, Timestamp createdAt, Employees employees) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaveType = leaveType;
        this.leaveStatus = leaveStatus;
        this.leaveReason = leaveReason;
        this.createdAt = createdAt;
        this.employees = employees;
    }
}
