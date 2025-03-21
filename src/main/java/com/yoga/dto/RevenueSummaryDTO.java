package com.yoga.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RevenueSummaryDTO {
    private Long totalBranches;
    private Double totalRevenue;
    private Double lastMonthRevenue;
}
