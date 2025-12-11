package com.mystiquex.employee_leave_management.repository;

import com.mystiquex.employee_leave_management.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employees, UUID> {

    @Query(value = "SELECT e from Employees e WHERE e.employeeId = :employeeId AND e.employeeStatus= 'ACTIVE'")
    Optional<Employees> findEmployeeByIdAndActive(@Param("employeeId") UUID employeeId);

}
