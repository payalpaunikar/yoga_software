package com.yoga.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {

    private Long employeeId;
    private String email;
    private String firstName;
    private String fatherName;
    private String lastName;
    private String mobileNumber;
    private String address;
    private Double salary;
    private String joiningDate;
    private String branchName;
}
