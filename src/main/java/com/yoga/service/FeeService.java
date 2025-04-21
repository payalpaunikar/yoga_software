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

import java.lang.module.ResolutionException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;

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

        Student student = studentRepository.findById(studentId).orElseThrow(
                ()-> new ResolutionException("student with id : "+studentId+" is not found"));

        Optional<StudentFee> studentFee = studentFeeRepository.findStudentFeeByStudentId(studentId);

        if (studentFee.isEmpty()){
            StudentFee newStudentFees = new StudentFee(student,student.getTotalFee() ,
                    transactionRequest.getAmount(),LocalDate.now(),30);
         studentFeeRepository.save(newStudentFees);

         StudentFeeTransaction studentFeeTransaction = new StudentFeeTransaction();
         studentFeeTransaction.setStudent(student);
         studentFeeTransaction.setAmount(transactionRequest.getAmount());
         studentFeeTransaction.setPaymentMode(transactionRequest.getPaymentMode());
         studentFeeTransaction.setPaymentDate(LocalDate.now());

         studentFeeTransactionRepo.save(studentFeeTransaction);

         return "Payment is done";
        }

         StudentFee getStudentFee = studentFee.get();

          StudentFeeTransaction studentFeeTransaction = new StudentFeeTransaction();
          studentFeeTransaction.setAmount(transactionRequest.getAmount());
          studentFeeTransaction.setStudent(getStudentFee.getStudent());
          studentFeeTransaction.setPaymentMode(transactionRequest.getPaymentMode());
          studentFeeTransaction.setPaymentDate(LocalDate.now());

          studentFeeTransactionRepo.save(studentFeeTransaction);

          getStudentFee.setPaidAmount(getStudentFee.getPaidAmount()+transactionRequest.getAmount());
          getStudentFee.setRemainingAmount(getStudentFee.getTotalFee()-getStudentFee.getPaidAmount());

          if (getStudentFee.getRemainingAmount() > 0){
              Calendar calendar = Calendar.getInstance();
              calendar.setTime(getStudentFee.getNextInstallmentDate());
              calendar.add(Calendar.DAY_OF_MONTH,30);
              getStudentFee.setNextInstallmentDate(calendar.getTime());
              getStudentFee.setFeeStatus(FeeStatus.PENDING);
          }
          else{
              getStudentFee.setFeeStatus(FeeStatus.PAID);
              getStudentFee.setNextInstallmentDate(null);
          }

          studentFeeRepository.save(getStudentFee);

          return "Installment payment recorded";

     }



}
