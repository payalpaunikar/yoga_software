package com.yoga.repository;

import com.yoga.datamodel.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    @Query("SELECT s FROM Student s JOIN s.batch b JOIN b.branch br WHERE br.id = :branchId")
    List<Student> findStudentByBranchId(@Param("branchId")Long branchId);






    @Query("SELECT s FROM Student s WHERE (s.joiningDate <= :currentDate AND s.endingDate >= :currentDate) AND s.batch.batchId = :batchId" )
    List<Student> findActiveStudentListAccordingDate(@Param("currentDate") LocalDate currentDate, @Param("batchId")Long batchId);
}
