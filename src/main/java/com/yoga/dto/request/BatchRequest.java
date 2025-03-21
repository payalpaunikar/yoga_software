package com.yoga.dto.request;

import com.yoga.component.BatchShift;
import com.yoga.component.BatchStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BatchRequest {
    private String batchName;
    private String startTiming;
    private String startDate;
    private String endingDate;
    private BatchStatus batchStatus;
    private BatchShift batchShift;
}
