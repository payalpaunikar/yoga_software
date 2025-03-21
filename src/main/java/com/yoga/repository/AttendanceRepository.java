package com.yoga.repository;


import com.yoga.datamodel.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {
    @Query("SELECT a FROM Attendance a Where a.attendanceDate = :attendanceDate AND a.batch.batchId= :batchId")
    Optional<Attendance> findByAttendanceDateAndBatchId(
            @Param("attendanceDate") LocalDate attendanceDate, @Param("batchId")Long batchId);
}
