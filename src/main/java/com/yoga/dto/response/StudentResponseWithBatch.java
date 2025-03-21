package com.yoga.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseWithBatch {

    private Long studentId;

    private String firstName;

    private String lastName;

    private String email;

    private String mobileNumber;

    private String nationality;

    private Long batchId;

    private String batchName;

}
