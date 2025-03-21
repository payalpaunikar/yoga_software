package com.yoga.repository;

import com.yoga.datamodel.StudentFee;
import com.yoga.dto.StudentFeesAlertDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface StudentFeeRepository extends JpaRepository<StudentFee,Long> {

    @Query("SELECT s FROM StudentFee s WHERE s.student.studentId= :studentId")
    StudentFee findStudentFeeByStudentId(@Param("studentId")Long studentId);

    ;

    @Query(value = """
    SELECT s.student_id AS studentId, s.first_name AS firstName, s.father_name AS fatherName,
           s.last_name AS lastName, s.mobile_number AS mobileNumber, s.joining_date AS joiningDate,
           f.fee_id AS feeId, f.total_fee AS totalStudentFee, f.paid_amount AS paidAmount,
           f.remaining_amount AS remainingAmount, f.next_installment_date AS nextInstallmentDate
    FROM student_fee f
    JOIN student s ON s.student_id = f.student_id
    WHERE f.next_installment_date < :currentDate AND f.remaining_amount > 0
""", nativeQuery = true)
    List<Object[]> getStudentFeeAlert(@Param("currentDate") LocalDate currentDate);


    @Query(value = """
            SELECT s.student_id,s.first_name,s.father_name,s.last_name,s.mobile_number,s.joining_date,sf.fee_id,sf.total_fee,sf.paid_amount,sf.remaining_amount,sf.next_installment_date FROM student_fee sf\s
            JOIN student s on s.student_id = sf.student_id
            JOIN batch b on b.batch_id = s.batch_id
            WHERE (sf.next_installment_date < :currentDate AND sf.remaining_amount > 0) AND b.batch_id = :batchId;
            """,nativeQuery = true)
    List<Object[]> getStudentFeeAlertBranchWise(@Param("currentDate")LocalDate currentDate,@Param("batchId")Long batchId);
}
