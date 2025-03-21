package com.yoga.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReceiptRequest {

    private String studentName;

    private String rupeesInWords;

    private String checkNo;

    private String bankName;

    private String startingDate;

    private String endingDate;

    private String balanceDue;

    private Double amount;
}
