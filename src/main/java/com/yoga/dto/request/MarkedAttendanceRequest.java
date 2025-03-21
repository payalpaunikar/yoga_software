package com.yoga.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MarkedAttendanceRequest {
    private Long studentId;
    private Boolean isPresent;
}
