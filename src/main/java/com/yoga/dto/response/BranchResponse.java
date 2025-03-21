package com.yoga.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BranchResponse {
    private Long branchId;
    private String branchName;
    private String address;
    private String city;
}
