package com.bilgeadam;

import lombok.*;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Employee {
    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Timestamp hireDate;
    private String jobId;
    private Double salary;
    private Double commission;
    private Integer managerId;
    private Integer departmentId;
}
