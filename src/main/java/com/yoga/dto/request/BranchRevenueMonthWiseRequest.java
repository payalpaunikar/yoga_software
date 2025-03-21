package com.yoga.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchRevenueMonthWiseRequest {
   private Long branchId;
   private String startingDate;
   private String endingDate;
}
