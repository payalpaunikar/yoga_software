package com.yoga.repository;

import com.yoga.datamodel.Branch;
import com.yoga.dto.RevenueSummaryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch,Long> {


    @Query(value = """
            SELECT
                (SELECT COUNT(*) FROM branch) AS totalBranches,
                (SELECT COALESCE(SUM(sft.amount), 0)
                 FROM student_fee_transaction sft
                 WHERE YEAR(sft.payment_date) = YEAR(CURRENT_DATE)) AS totalRevenue,
                (SELECT COALESCE(SUM(sft.amount), 0)
                 FROM student_fee_transaction sft
                 WHERE YEAR(sft.payment_date) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH)
                 AND MONTH(sft.payment_date) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH)
                ) AS lastMonthRevenue
            """, nativeQuery = true)
    List<Object[]> getRevenueSummary();



    @Query(value = """
            SELECT
                b.branch_id AS branchId, 
                b.branch_name AS branchName,
                DATE_FORMAT(sft.payment_date, '%b') AS month,
                COALESCE(SUM(sft.amount), 0) AS totalCollection
            FROM branch b
            JOIN batch ba ON ba.branch_id = b.branch_id 
            JOIN student s ON s.batch_id = ba.batch_id
            LEFT JOIN student_fee_transaction sft ON sft.student_id = s.student_id
            WHERE sft.payment_date BETWEEN :startingDate And :endingDate  
            AND b.branch_id = :branchId
            GROUP BY b.branch_id, b.branch_name, DATE_FORMAT(sft.payment_date, '%b'), MONTH(sft.payment_date)
            ORDER BY b.branch_id, MONTH(sft.payment_date);
            """,nativeQuery = true)
    List<Object[]> getBranchRevenueSummayMonthWise(@Param("branchId")Long branchId, @Param("startingDate") LocalDate startingDate, @Param("endingDate")LocalDate endingDate);


    @Query(value = """
            SELECT 
            br.branch_id,br.branch_name,br.address,b.batch_id,b.batch_name,b.batch_shift,b.batch_status,b.start_date,b.ending_date\s
            FROM branch br 
            JOIN batch b ON br.branch_id = b.branch_id
            WHERE b.batch_status='ONGOING' OR b.batch_status='UPCOMING';
            """,nativeQuery = true)
    List<Object[]> getBranchListWithAvailableBatchList();

}
