package com.mystiquex.employee_leave_management.service.serviceImpl;

import com.mystiquex.employee_leave_management.dto.EmployeesDto;
import com.mystiquex.employee_leave_management.entity.Employees;
import com.mystiquex.employee_leave_management.exception.FormValidationException;
import com.mystiquex.employee_leave_management.repository.EmployeeRepository;
import com.mystiquex.employee_leave_management.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public void createEmployee(EmployeesDto employeesDto) {
        //Joining date not in future
        if (employeesDto.getDateOfJoining().isAfter(LocalDate.now())) {
            throw new FormValidationException("Date of joining must be in past", "INCORRECT_DATE_OF_JOINING");
        }
        //Add Employee
        Employees employees = new Employees(employeesDto.getFirstName(), employeesDto.getLastName(), employeesDto.getEmail(), "ACTIVE",
                employeesDto.getDateOfJoining(), Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()));
        employeeRepository.save(employees);
    }

    @Override
    public Employees getEmployeeById(UUID employeeId) {
        //Fetch Employee Details using ID
        return employeeRepository.findEmployeeByIdAndActive(employeeId).orElseThrow(() -> new RuntimeException("There is no employee with this ID: "+employeeId));
    }

    @Override
    public void updateEmployeeDetails(UUID employeeId, EmployeesDto employeesDto) {
        //Update Employee Details
        Employees employees = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("There is no employee with this ID: "+ employeeId));
        employees.setFirstName(employeesDto.getFirstName());
        employees.setLastName(employeesDto.getLastName());
        employees.setEmail(employeesDto.getEmail());
        employees.setEmployeeStatus(employeesDto.getEmployeeStatus());
        employees.setDateOfJoining(employeesDto.getDateOfJoining());
        employees.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        employeeRepository.save(employees);
    }

    @Override
    public void EmployeeActiveToInactive(UUID employeeId) {
        //Employee Moving to INACTIVE(Soft Delete)
        Employees employees = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("There is no employee with ID: " + employeeId));
        employees.setEmployeeStatus("INACTIVE");
        employees.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        employeeRepository.save(employees);
    }

    @Override
    public List<Employees> getEmployeesByFilter(String employeeStatus, LocalDate joinAfter, LocalDate joinBefore, int page, int size) {
        //Filtering Employee Details based on Employee status, Joining date
        List<Employees> employeesList = employeeRepository.findAll().stream().filter(u -> employeeStatus == null || employeeStatus.isBlank() || u.getEmployeeStatus().equals(employeeStatus))
                .filter(u -> joinAfter == null || joinAfter.toString().isBlank() || u.getDateOfJoining().isAfter(joinAfter))
                .filter(u -> joinBefore == null || joinBefore.toString().isBlank() || u.getDateOfJoining().isBefore(joinBefore)).toList();

        //Pagination
        int start = page * size;
        int end = Math.min(start + size, employeesList.size());

        if (start >= employeesList.size()) {
            return List.of();
        }
        return employeesList.subList(start, end);
    }

    @Override
    public List<Employees> getAllEmployee() {
        //Fetch All employees
        return employeeRepository.findAll();
    }
}
