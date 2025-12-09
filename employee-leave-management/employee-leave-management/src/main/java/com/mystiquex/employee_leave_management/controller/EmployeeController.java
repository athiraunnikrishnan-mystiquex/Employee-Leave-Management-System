package com.mystiquex.employee_leave_management.controller;

import com.mystiquex.employee_leave_management.dto.EmployeesDto;
import com.mystiquex.employee_leave_management.entity.Employees;
import com.mystiquex.employee_leave_management.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/add")
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeesDto employeesDto){
        employeeService.createEmployee(employeesDto);
        return ResponseEntity.ok("Employee Saved Successfully");
    }

    @GetMapping("/{employeeId}")
    public Employees getEmployeeById(@PathVariable UUID employeeId){
        return employeeService.getEmployeeById(employeeId);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Employees>> getEmployeesByFilter(
            @RequestParam(required = false) String employeeStatus,
            @RequestParam(required = false) LocalDate joinedAfter,
            @RequestParam(required = false) LocalDate joinedBefore,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(employeeService.getEmployeesByFilter(employeeStatus, joinedAfter, joinedBefore, page, size));
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<?> updateEmployeeDetails(@PathVariable UUID employeeId, @RequestBody EmployeesDto employeesDto){
         employeeService.updateEmployeeDetails(employeeId, employeesDto);
         return ResponseEntity.ok("Employee Details Changed Successfully");
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<?> EmployeeActiveToInactive(@PathVariable UUID employeeId){
         employeeService.EmployeeActiveToInactive(employeeId);
        return ResponseEntity.ok("Employee Deleted Successfully");
    }

    @GetMapping("")
    public List<Employees> getAllEmployee(){
        return employeeService.getAllEmployee();
    }
}
