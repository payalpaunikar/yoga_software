package com.yoga.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;


@Getter
@Setter
public class StudentFeesAlertDto {
    private Long studentId;
    private String firstName;
    private String fatherName;
    private String lastName;
    private String mobileNumber;
    private String joiningDate;
    private Long feeId;
    private Double totalStudentFee;
    private Double paidAmount;
    private Double remainingAmount;
    private String nextInstallmentDate;

    // Constructor that matches the query result
    public StudentFeesAlertDto(Long studentId, String firstName, String fatherName, String lastName,
                               String mobileNumber, Date joiningDate, Long feeId, Double totalStudentFee,
                               Double paidAmount, Double remainingAmount, Timestamp nextInstallmentDate) {

        this.studentId = studentId;
        this.firstName = firstName;
        this.fatherName = fatherName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;

        // Convert Timestamp to LocalDate
        this.joiningDate = (joiningDate != null) ? String.valueOf(joiningDate) : null;

        this.feeId = feeId;
        this.totalStudentFee = totalStudentFee;
        this.paidAmount = paidAmount;
        this.remainingAmount = remainingAmount;

        // Convert Timestamp to LocalDate
        this.nextInstallmentDate = (nextInstallmentDate != null) ? String.valueOf(nextInstallmentDate) : null;
    }


}

