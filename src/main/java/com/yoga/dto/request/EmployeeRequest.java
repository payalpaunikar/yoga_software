package com.yoga.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
    private String email;
    private String password;
    private String firstName;
    private String fatherName;
    private String lastName;
    private String mobileNumber;
    private String address;
    private Double salary;
    private String joiningDate;
}
