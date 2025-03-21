package com.yoga.datamodel;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StudentAttendance {

          @Id
          @GeneratedValue(strategy = GenerationType.IDENTITY)
          private Long studentAttendanceId;

          @ManyToOne(fetch = FetchType.LAZY)
          @JoinColumn(name = "student_id",nullable = false)
          private Student student;

          @Column(nullable = false)
          private Boolean isPresent;

          @ManyToOne(fetch = FetchType.LAZY)
          @JoinColumn(name = "attendance_id")
          private Attendance attendance;
}
