package com.yoga.service;


import com.yoga.component.FeeStatus;
import com.yoga.component.PaymentMode;
import com.yoga.datamodel.Student;
import com.yoga.datamodel.StudentFee;
import com.yoga.datamodel.StudentFeeTransaction;
import com.yoga.dto.request.StudentIntallmentTransactionRequest;
import com.yoga.exception.UserNotFoundException;
import com.yoga.repository.StudentFeeRepository;
import com.yoga.repository.StudentFeeTransactionRepo;
import com.yoga.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Calendar;

@Service
public class FeeService {

    private StudentFeeRepository studentFeeRepository;

    private StudentFeeTransactionRepo studentFeeTransactionRepo;

    private StudentRepository studentRepository;

    @Autowired
    public FeeService(StudentFeeRepository studentFeeRepository,StudentFeeTransactionRepo studentFeeTransactionRepo,
                      StudentRepository studentRepository){
        this.studentFeeRepository = studentFeeRepository;
        this.studentFeeTransactionRepo = studentFeeTransactionRepo;
        this.studentRepository = studentRepository;
    }


      @Transactional
      public String payInstallment(Long studentId, StudentIntallmentTransactionRequest transactionRequest){
        StudentFee studentFee = studentFeeRepository.findStudentFeeByStudentId(studentId);

          StudentFeeTransaction studentFeeTransaction = new StudentFeeTransaction();
          studentFeeTransaction.setAmount(transactionRequest.getAmount());
          studentFeeTransaction.setStudent(studentFee.getStudent());
          studentFeeTransaction.setPaymentMode(transactionRequest.getPaymentMode());
          studentFeeTransaction.setPaymentDate(LocalDate.now());

          studentFeeTransactionRepo.save(studentFeeTransaction);

          studentFee.setPaidAmount(studentFee.getPaidAmount()+transactionRequest.getAmount());
          studentFee.setRemainingAmount(studentFee.getTotalFee()-studentFee.getPaidAmount());

          if (studentFee.getRemainingAmount() > 0){
              Calendar calendar = Calendar.getInstance();
              calendar.setTime(studentFee.getNextInstallmentDate());
              calendar.add(Calendar.DAY_OF_MONTH,30);
              studentFee.setNextInstallmentDate(calendar.getTime());
              studentFee.setFeeStatus(FeeStatus.PENDING);
          }
          else{
              studentFee.setFeeStatus(FeeStatus.PAID);
              studentFee.setNextInstallmentDate(null);
          }

          studentFeeRepository.save(studentFee);

          return "Installment payment recorded";

     }



}
