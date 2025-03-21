package com.yoga.dto;

import java.time.LocalDate;

public interface RevenueReportDTO {
    Long getBatchId();
    String getBatchName();
    LocalDate getStartDate();
    LocalDate getEndingDate();
    String getBatchStatus();
    Integer getTotalStudents();
    Double getTotalStudentFees();
    Double getCollectedFees();
    Double getPendingFees();
}
