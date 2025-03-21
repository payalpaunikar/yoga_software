package com.yoga.dto.response;

import com.yoga.component.BatchShift;
import com.yoga.component.BatchStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchWithBatchResponse {
    private Long branchId;
    private String branchName;
    private String branchAddress;
    private Long batchId;
    private String batchName;
    private String batchShift;
    private String batchStatus;
    private String startDate;
    private String endingDate;
}
