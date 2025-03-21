package com.yoga.repository;

import ch.qos.logback.core.model.conditional.IfModel;
import com.yoga.component.BatchStatus;
import com.yoga.datamodel.Batch;
import com.yoga.dto.RevenueReportDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch,Long> {


           @Query("SELECT b FROM Batch b WHERE b.branch.branchId= :branchId AND (b.batchStatus= :batchStatus1 OR b.batchStatus= :batchStatus2)")
           List<Batch> findAvailableBatchesInBranch(
                   @Param("branchId") Long branchId,
                   @Param("batchStatus1") BatchStatus batchStatus1,
                   @Param("batchStatus2") BatchStatus batchStatus2
                   );



           @Query("SELECT b FROM Batch b WHERE b.branch.branchId= :branchId")
           List<Batch> findAllBatchesByBranchId(@Param("branchId")Long branchId);

    @Query(value = """
        SELECT 
            b.batch_id AS batchId, 
            b.batch_name AS batchName, 
            b.start_date AS startDate, 
            b.ending_date AS endingDate, 
            b.batch_status AS batchStatus, 
            COUNT(s.student_id) AS totalStudents, 
            COALESCE(SUM(f.total_fee), 0) AS totalStudentFees, 
            COALESCE(SUM(f.paid_amount), 0) AS collectedFees, 
            COALESCE(SUM(f.remaining_amount), 0) AS pendingFees
        FROM batch b 
        LEFT JOIN student s ON b.batch_id = s.batch_id 
        LEFT JOIN student_fee f ON s.student_id = f.student_id
        WHERE b.start_date <= :endDate  
        AND b.ending_date >= :startDate 
        GROUP BY b.batch_id, b.batch_name, b.start_date, b.ending_date, b.batch_status
        """, nativeQuery = true)
    List<RevenueReportDTO> getRevenueReport(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

}
