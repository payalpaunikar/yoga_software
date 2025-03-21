package com.yoga.controller;


import com.yoga.component.PaymentMode;
import com.yoga.dto.request.StudentIntallmentTransactionRequest;
import com.yoga.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fee")
@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
@CrossOrigin("*")
public class FeeController {

    private FeeService feeService;

    @Autowired
    public FeeController(FeeService feeService){
        this.feeService = feeService;
    }


    @PostMapping("/pay-installment/student/{studentId}")
    public String  payInstallment(
            @PathVariable("studentId")Long studentId,
            @RequestBody StudentIntallmentTransactionRequest studentIntallmentTransactionRequest
            ){
       return feeService.payInstallment(studentId,studentIntallmentTransactionRequest);
    }




}
