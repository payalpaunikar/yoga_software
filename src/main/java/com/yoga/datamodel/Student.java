package com.yoga.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String fatherName;

    private LocalDate dateOfBirth;

    private LocalTime birthTime;

    private String nationality;

    private String address;

    private String email;

    @Column(nullable = false)
    private String mobileNumber;

    private String phoneNumber;

    private String diseases;

    private  String medicineName;

    @Column(nullable = false)
    private LocalDate joiningDate;

    private LocalDate endingDate;

    private Double totalFee;

//    private LocalTime  timePreference;

//    @Enumerated(EnumType.STRING)
//    private BatchShift batchPreference;

    private Double weight;  // weight store in kg

    private Double height;  //height store in ft

    private String program;

   // private Long studentFees;  //student fees is varrie

    @ManyToOne
    @JoinColumn(name = "batch_id")
    @JsonIgnore
    private Batch batch;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<StudentFee> studentFees;


    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<StudentFeeTransaction> studentFeeTransactions;


    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<StudentAttendance> studentAttendances;
}
