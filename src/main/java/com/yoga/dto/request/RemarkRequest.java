package com.yoga.dto.request;

import java.time.LocalDate;

public class RemarkRequest {
    private String remark;
    private LocalDate remarkdate;

    // Getters and Setters
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDate getRemarkdate() {
        return remarkdate;
    }

    public void setRemarkdate(LocalDate remarkdate) {
        this.remarkdate = remarkdate;
    }
}