package com.mystiquex.employee_leave_management.repository;

import com.mystiquex.employee_leave_management.entity.LeaveRequests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LeaveRequestsRepository extends JpaRepository<LeaveRequests, UUID> {
}
