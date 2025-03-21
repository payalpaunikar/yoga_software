package com.yoga.datamodel;


import com.yoga.component.PaymentMode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class StudentFeeTransaction {

       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long studentFeeTransactionId;

       @ManyToOne
       @JoinColumn(name = "student_id",nullable = false)
       private Student student;

       private Double amount;

       @Enumerated(EnumType.STRING)
       private PaymentMode paymentMode;

       private LocalDate paymentDate;
}
