package com.yoga.repository;

import com.yoga.datamodel.StudentFee;
import com.yoga.datamodel.StudentFeeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentFeeTransactionRepo extends JpaRepository<StudentFeeTransaction,Long> {
    @Query("SELECT s FROM StudentFeeTransaction s WHERE s.student.studentId= :studentId")
    List<StudentFeeTransaction> findStudentFeeTransactionByStudentId(@Param("studentId")Long studentId);
}
