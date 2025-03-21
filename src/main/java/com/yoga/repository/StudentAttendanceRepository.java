package com.yoga.repository;

import com.yoga.datamodel.Attendance;
import com.yoga.datamodel.StudentAttendance;
import com.yoga.dto.request.GetAttendanceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentAttendanceRepository extends JpaRepository<StudentAttendance,Long> {

    @Query("SELECT sa FROM StudentAttendance sa WHERE sa.attendance = :attendance")
    List<StudentAttendance> findByAttendance(@Param("attendance") Attendance attendance);


}
