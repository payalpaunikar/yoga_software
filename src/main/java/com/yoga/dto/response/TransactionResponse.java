package com.yoga.dto.response;


import com.yoga.component.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionResponse {
    private String paymentDate;
    private PaymentMode paymentMode;
    private  Double amountPaid;
}
