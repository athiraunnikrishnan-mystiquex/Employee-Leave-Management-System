package com.mystiquex.employee_leave_management.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class EmployeesDto {
    private UUID employeeId;

    @NotBlank(message = "First name should provide")
    private String firstName;

    @NotBlank(message = "Last name should provide")
    private String lastName;

    @Email(message = "Email not in correct format")
    @Pattern( regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
    message = "Email is not in correct format")
    private String email;

    private String employeeStatus;
    private LocalDate dateOfJoining;

}
