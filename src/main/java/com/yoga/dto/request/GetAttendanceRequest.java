package com.yoga.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetAttendanceRequest {
    private Long studentId;
    private String firstName;
    private String fatherName;
    private String lastName;
   // private String joiningDate;
    private String mobileNumber;
    private Boolean isPresent;
}
