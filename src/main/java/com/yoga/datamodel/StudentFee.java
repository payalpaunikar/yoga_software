package com.yoga.datamodel;

import com.yoga.component.FeeStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class StudentFee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feeId;

    @OneToOne
    @JoinColumn(name = "student_id",nullable = false,unique = true)
    private Student student;

    private Double totalFee;

    private Double paidAmount=0.0;

    private Double remainingAmount;

    private LocalDate firstPaymentDate;

    private Date nextInstallmentDate;

     @Enumerated(EnumType.STRING)
     private FeeStatus feeStatus;


    public StudentFee(Student student, Double totalFee, Double initialPayment, LocalDate firstPaymentDate, int installmentGapDays) {
        this.student = student;
        this.totalFee = totalFee;
        this.paidAmount = initialPayment;
        this.remainingAmount = totalFee - initialPayment;
        this.firstPaymentDate = firstPaymentDate;
        this.nextInstallmentDate = calculateNextInstallmentDate(Date.from(student.getJoiningDate().atStartOfDay(ZoneId.systemDefault()).toInstant()), installmentGapDays);
        this.feeStatus = (remainingAmount == 0) ? FeeStatus.PAID : FeeStatus.PENDING;
    }


    private Date calculateNextInstallmentDate(Date startDate, int days){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_MONTH,days);
        return calendar.getTime();
    }
}
