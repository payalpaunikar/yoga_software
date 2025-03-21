package com.yoga.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yoga.component.BatchShift;
import com.yoga.component.BatchStatus;
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
@Table(name = "batch")
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchId;

    @Column(nullable = false)
    private String batchName;


    private LocalTime startTiming;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endingDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BatchShift batchShift;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BatchStatus batchStatus;

    @ManyToOne
    @JoinColumn(name = "branch_id",nullable = false)
    private Branch branch;

   @OneToMany(mappedBy = "batch",cascade = CascadeType.ALL)
   private List<Student> student;

   @OneToMany(mappedBy = "batch",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
   private List<Attendance> attendance;


}
