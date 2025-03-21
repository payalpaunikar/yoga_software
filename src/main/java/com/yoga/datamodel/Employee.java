package com.yoga.datamodel;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Employee {

          @Id
          @GeneratedValue(strategy = GenerationType.IDENTITY)
          private Long employeeId;

          private String firstName;

          private String fatherName;

          private String lastName;

          private String mobileNumber;

          private String address;

          @OneToOne
          @JoinColumn(name = "user_id",nullable = false,unique = true)
          @JsonIgnore
          private User user;

          @ManyToOne
          @JoinColumn(name = "branch_id")
          @JsonIgnore
          private Branch branch;

          private Double salary;

          private LocalDate joiningDate;

}
