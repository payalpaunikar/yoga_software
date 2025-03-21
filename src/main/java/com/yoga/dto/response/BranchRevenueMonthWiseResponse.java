package com.yoga.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchRevenueMonthWiseResponse {
    private Long branchId;
    private String branchName;
    private String month;
    private Double totalCollection;
}
