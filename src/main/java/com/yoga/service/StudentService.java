package com.yoga.service;


import com.yoga.component.FeeStatus;
import com.yoga.component.PaymentMode;
import com.yoga.datamodel.Batch;
import com.yoga.datamodel.Student;
import com.yoga.datamodel.StudentFee;
import com.yoga.datamodel.StudentFeeTransaction;
import com.yoga.dto.request.StudentRequest;
import com.yoga.dto.response.BatchResponse;
import com.yoga.dto.response.StudentDetailsResponse;
import com.yoga.dto.response.StudentResponse;
import com.yoga.dto.response.TransactionResponse;
import com.yoga.exception.UserNotFoundException;
import com.yoga.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    private StudentFeeRepository studentFeeRepository;

    private BatchRepository batchRepository;

    private StudentFeeTransactionRepo studentFeeTransactionRepo;

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    public StudentService(StudentRepository studentRepository,StudentFeeRepository studentFeeRepository,BatchRepository batchRepository,StudentFeeTransactionRepo studentFeeTransactionRepo){
        this.studentRepository = studentRepository;
        this.studentFeeRepository = studentFeeRepository;
        this.batchRepository = batchRepository;
        this.studentFeeTransactionRepo = studentFeeTransactionRepo;

    }


    @Transactional
    public StudentResponse registerStudent(Long batchId,StudentRequest studentRequest){
       Batch existingBatch = batchRepository.findById(batchId)
               .orElseThrow(()-> new UserNotFoundException("batch with id : "+batchId+" is not found in the system"));

        Student newStudent =  new Student();
        newStudent = convertStudentRequestToStudent(studentRequest,newStudent);
        newStudent.setBatch(existingBatch);
        Student saveStudent =  studentRepository.save(newStudent);

//        StudentFee studentFee = new StudentFee(saveStudent, studentRequest.getTotalFees(),0.0,LocalDate.now(),30);
//         studentFeeRepository.save(studentFee);

//         StudentFeeTransaction studentFeeTransaction = new StudentFeeTransaction();
//         studentFeeTransaction.setStudent(saveStudent);
//         studentFeeTransaction.setAmount(0.0);
//         studentFeeTransaction.setPaymentMode(PaymentMode.BY_CASH);
//         studentFeeTransaction.setPaymentDate(LocalDate.now());
//
//         studentFeeTransactionRepo.save(studentFeeTransaction);

        StudentResponse studentResponse = new StudentResponse();
        studentResponse = convertStudentToStudentResponse(saveStudent,studentResponse);
        studentResponse.setTotalFees(studentRequest.getTotalFees());
      //  studentResponse.setInitialPayment(0.0);
        return studentResponse;
    }



    @Transactional
    public StudentResponse updateStudentDetails(Long studentId,StudentRequest studentRequest){
        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(()-> new UserNotFoundException("Student with : "+studentId+" id is not exit in the system"));

        existingStudent = convertStudentRequestToStudent(studentRequest,existingStudent);
        Student saveStudent = studentRepository.save(existingStudent);

//        StudentFee studentFee= studentFeeRepository.findStudentFeeByStudentId(existingStudent.getStudentId());
//
//        List<StudentFeeTransaction> studentFeeTransactionList = studentFeeTransactionRepo.findStudentFeeTransactionByStudentId(studentId);
//
//        if (studentFeeTransactionList.size()==1){
//            //logger.info("Student transaction is equal to 1");
//            studentFee.setPaidAmount(0.0);
//            studentFee.setTotalFee(studentRequest.getTotalFees());
//            studentFee.setRemainingAmount(studentRequest.getTotalFees()-0.0);
//            studentFeeRepository.save(studentFee);
//
//          studentFeeTransactionList = studentFeeTransactionList.stream().map(studentFeeTransaction -> {
//                studentFeeTransaction.setAmount(0.0);
//                return studentFeeTransaction;
//            }).toList();
//
//          studentFeeTransactionRepo.saveAll(studentFeeTransactionList);
//        }
//        else {
//
//            studentFee.setTotalFee(studentRequest.getTotalFees());
//
//            Double feesPaid = 0.0;
//
//            for (StudentFeeTransaction studentFeeTransaction : studentFeeTransactionList) {
//                feesPaid = feesPaid+studentFeeTransaction.getAmount();
//                //logger.info("fees paid : " + feesPaid);
//            }
//
//            studentFee.setRemainingAmount(studentRequest.getTotalFees() - feesPaid);
//           // logger.info("student total fees : "+studentRequest.getTotalFees());
//           // logger.info("Student fee is : "+studentFee.getRemainingAmount());
//            studentFee.setFeeStatus(studentFee.getRemainingAmount() == 0.0 ? FeeStatus.PAID : FeeStatus.PENDING);

//            //logger.info("student fees : " + studentFee);
//            studentFeeRepository.save(studentFee);
    //    }

        StudentResponse studentResponse = new StudentResponse();
        studentResponse = convertStudentToStudentResponse(saveStudent,studentResponse);
//        studentResponse.setTotalFees(studentRequest.getTotalFees());
       // studentResponse.setInitialPayment(0.0);
        return studentResponse;
    }


    public String deleteStudent(Long studentId){
        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(()-> new UserNotFoundException("Student with : "+studentId+" id is not found in the system"));

        studentRepository.delete(existingStudent);

        return "Student deleted succeffuly";
    }


    public StudentDetailsResponse getStudentById(Long studentId){
        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(()-> new UserNotFoundException("Student with : "+studentId+" id is not found in the system"));

        StudentDetailsResponse studentDetailsResponse = new StudentDetailsResponse();
        studentDetailsResponse.setStudentId(existingStudent.getStudentId());
        studentDetailsResponse.setFirstName(existingStudent.getFirstName());
        studentDetailsResponse.setLastName(existingStudent.getLastName());
        studentDetailsResponse.setFatherName(existingStudent.getFatherName());
        studentDetailsResponse.setDateOfBirth(String.valueOf(existingStudent.getDateOfBirth()));
        studentDetailsResponse.setBirthTime(String.valueOf(existingStudent.getBirthTime()));
        studentDetailsResponse.setDiseases(existingStudent.getDiseases());
        studentDetailsResponse.setMedicineName(existingStudent.getMedicineName());
        studentDetailsResponse.setMobileNumber(existingStudent.getMobileNumber());
        studentDetailsResponse.setPhoneNumber(existingStudent.getPhoneNumber());
        studentDetailsResponse.setAddress(existingStudent.getAddress());
        studentDetailsResponse.setEmail(existingStudent.getEmail());
        studentDetailsResponse.setJoiningDate(String.valueOf(existingStudent.getJoiningDate()));
        studentDetailsResponse.setProgram(existingStudent.getProgram());
        studentDetailsResponse.setNationality(existingStudent.getNationality());
        studentDetailsResponse.setHeight(existingStudent.getHeight());
        studentDetailsResponse.setWeight(existingStudent.getWeight());
        studentDetailsResponse.setEndingDate(String.valueOf(existingStudent.getEndingDate()));
       studentDetailsResponse.setTotalFees(existingStudent.getTotalFee());

        Optional<StudentFee> studentFee = studentFeeRepository.findStudentFeeByStudentId(studentId);

        if (studentFee.isEmpty()){
            studentDetailsResponse.setRemaingAmount(existingStudent.getTotalFee());
            Date nextPaymentDate = calculateNextInstallmentDate(Date.from(existingStudent.getJoiningDate().atStartOfDay(ZoneId.systemDefault()).toInstant()),30);

            // ✅ Format the date properly
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = outputFormat.format(nextPaymentDate);

            studentDetailsResponse.setNextPaymentDate(formattedDate);
            studentDetailsResponse.setTransaction(new ArrayList<>());

            return studentDetailsResponse;
        }

        StudentFee getStudentFee = studentFee.get();

        //studentDetailsResponse.setTotalFees(studentFee.getTotalFee());
        studentDetailsResponse.setRemaingAmount(getStudentFee.getRemainingAmount());

        if (!getStudentFee.getTotalFee().equals(null)){
            studentDetailsResponse.setNextPaymentDate(String.valueOf(getStudentFee.getNextInstallmentDate()));
        }



        List<StudentFeeTransaction> studentFeeTransactionList = studentFeeTransactionRepo.findStudentFeeTransactionByStudentId(studentId);
        List<TransactionResponse> transactionResponseList = studentFeeTransactionList.stream()
                .map(studentFeeTransaction -> {
                    TransactionResponse transactionResponse = new TransactionResponse();
                    transactionResponse.setAmountPaid(studentFeeTransaction.getAmount());
                    transactionResponse.setPaymentDate(String.valueOf(studentFeeTransaction.getPaymentDate()));
                    transactionResponse.setPaymentMode(studentFeeTransaction.getPaymentMode());

//                    if (studentFeeTransaction.getPaymentDate().equals(getStudentFee.getFirstPaymentDate())){
//                        studentDetailsResponse.setInitialPayment(studentFeeTransaction.getAmount());
//                    }

                    return transactionResponse;
                }).toList();

        studentDetailsResponse.setTransaction(transactionResponseList);


        return studentDetailsResponse;
    }


    public BatchResponse getStudentBatch(Long studentId){
        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(()-> new UserNotFoundException("Student not found exit with : "+studentId+" id"));

        Batch getBatch = existingStudent.getBatch();

        BatchResponse batchResponse = new BatchResponse();
        batchResponse.setBatchId(getBatch.getBatchId());
        batchResponse.setBatchShift(getBatch.getBatchShift());
        batchResponse.setBatchStatus(getBatch.getBatchStatus());
        batchResponse.setBatchName(getBatch.getBatchName());
        batchResponse.setStartDate(String.valueOf(getBatch.getStartTiming()));
        batchResponse.setStartTiming(String.valueOf(getBatch.getStartTiming()));

        return batchResponse;
    }


    public String updateStudentBatch(Long studentId,Long batchId){
        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(()-> new UserNotFoundException("student with id : "+studentId+" is not found"));

        Batch existingBatch = batchRepository.findById(batchId)
                .orElseThrow(()-> new UserNotFoundException("batch with id : "+batchId+" is not found"));

        existingStudent.setBatch(existingBatch);
        studentRepository.save(existingStudent);

        return "batch : "+batchId+" is assign to "+studentId+" id student";
    }


    private Student convertStudentRequestToStudent(StudentRequest studentRequest,Student newStudent){

        newStudent.setEmail(studentRequest.getEmail());
        newStudent.setAddress(studentRequest.getAddress());
        newStudent.setDiseases(studentRequest.getDiseases());
        newStudent.setHeight(studentRequest.getHeight());
        newStudent.setWeight(studentRequest.getWeight());
        newStudent.setFirstName(studentRequest.getFirstName());
        newStudent.setLastName(studentRequest.getLastName());
        newStudent.setFatherName(studentRequest.getFatherName());
        newStudent.setNationality(studentRequest.getNationality());
        newStudent.setBirthTime(LocalTime.parse(studentRequest.getBirthTime()));
        newStudent.setDateOfBirth(LocalDate.parse(studentRequest.getDateOfBirth()));
        newStudent.setMobileNumber(studentRequest.getMobileNumber());
        newStudent.setPhoneNumber(studentRequest.getPhoneNumber());
        newStudent.setMedicineName(studentRequest.getMedicineName());
        newStudent.setJoiningDate(LocalDate.parse(studentRequest.getJoiningDate()));
        newStudent.setEndingDate(LocalDate.parse(studentRequest.getEndingDate()));
        newStudent.setWeight(studentRequest.getWeight());
        newStudent.setHeight(studentRequest.getHeight());
        newStudent.setProgram(studentRequest.getProgram());
        newStudent.setTotalFee(studentRequest.getTotalFees());

        return newStudent;
    }

    private StudentResponse convertStudentToStudentResponse(Student student,StudentResponse newStudentResponse){

        newStudentResponse.setStudentId(student.getStudentId());
        newStudentResponse.setFirstName(student.getFirstName());
        newStudentResponse.setLastName(student.getLastName());
        newStudentResponse.setFatherName(student.getFatherName());
        newStudentResponse.setDateOfBirth(String.valueOf(student.getDateOfBirth()));
        newStudentResponse.setBirthTime(String.valueOf(student.getBirthTime()));
        newStudentResponse.setDiseases(student.getDiseases());
        newStudentResponse.setMedicineName(student.getMedicineName());
        newStudentResponse.setMobileNumber(student.getMobileNumber());
        newStudentResponse.setPhoneNumber(student.getPhoneNumber());
        newStudentResponse.setAddress(student.getAddress());
        newStudentResponse.setEmail(student.getEmail());
        newStudentResponse.setJoiningDate(String.valueOf(student.getJoiningDate()));
        newStudentResponse.setProgram(student.getProgram());
        newStudentResponse.setNationality(student.getNationality());
        newStudentResponse.setHeight(student.getHeight());
        newStudentResponse.setWeight(student.getWeight());
        newStudentResponse.setEndingDate(String.valueOf(student.getEndingDate()));
        return newStudentResponse;
    }



    public Date calculateNextInstallmentDate(Date startDate, int days){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_MONTH,days);
        return calendar.getTime();
    }

}
