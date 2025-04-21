package com.yoga.dto.request;


import com.yoga.component.BatchShift;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {

    private String firstName;

    private String lastName;

    private String fatherName;

    private String dateOfBirth;

    private String birthTime;

    private String nationality;

    private String address;

    private String email;

    private String mobileNumber;

    private String phoneNumber;

    private String diseases;

    private  String medicineName;

    private String joiningDate;

    private String endingDate;

    private Double weight;  // weight store in kg

    private Double height;  //height store in ft

    private String program;

    private Double totalFees;

  //  private Double initialPayment;

}
