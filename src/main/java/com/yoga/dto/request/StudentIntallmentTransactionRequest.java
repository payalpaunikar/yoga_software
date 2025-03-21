package com.yoga.dto.request;


import com.yoga.component.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentIntallmentTransactionRequest {

    private Double amount;
    private PaymentMode paymentMode;
}
