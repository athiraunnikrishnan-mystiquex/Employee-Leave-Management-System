package com.mystiquex.employee_leave_management.entity;

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
@Table(name = "employees")
public class Employees {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID employeeId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "employeeStatus")
    private String employeeStatus;

    @Column(name = "date_of_joining", nullable = false)
    private LocalDate dateOfJoining;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public Employees(){}

    public Employees(String firstName, String lastName, String email, String employeeStatus, LocalDate dateOfJoining, Timestamp createdAt, Timestamp updatedAt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.employeeStatus = employeeStatus;
        this.dateOfJoining = dateOfJoining;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

