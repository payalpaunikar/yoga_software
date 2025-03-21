package com.yoga.dto.response;

import com.yoga.component.BatchShift;
import com.yoga.component.BatchStatus;
import com.yoga.datamodel.Branch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BatchResponse {
    private Long batchId;
    private String batchName;
    private String startTiming;
    private String startDate;
    private String endingDate;
    private BatchShift batchShift;
    private BatchStatus batchStatus;
}
