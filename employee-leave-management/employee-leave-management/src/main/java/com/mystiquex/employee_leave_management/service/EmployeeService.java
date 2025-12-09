package com.mystiquex.employee_leave_management.service;

import com.mystiquex.employee_leave_management.dto.EmployeesDto;
import com.mystiquex.employee_leave_management.entity.Employees;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface EmployeeService {
    void createEmployee(EmployeesDto employeesDto);
    Employees getEmployeeById(UUID employeeId);
    void updateEmployeeDetails(UUID employeeId, EmployeesDto employeesDto);
    void EmployeeActiveToInactive(UUID employeeId);
    List<Employees> getEmployeesByFilter(String employeeStatus, LocalDate joinAfter, LocalDate joinBefore, int page, int size);
    List<Employees> getAllEmployee();
}
