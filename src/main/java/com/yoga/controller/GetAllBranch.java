package com.yoga.controller;


import com.yoga.datamodel.Student;
import com.yoga.dto.response.BranchResponse;
import com.yoga.dto.response.BranchWithBatchResponse;
import com.yoga.dto.response.StudentResponseWithBatch;
import com.yoga.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branches")
@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
@CrossOrigin("*")
public class GetAllBranch {

    private BranchService branchService;

    @Autowired
    public GetAllBranch(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping("/list")
    public List<BranchResponse> getAllBranch(){
        return branchService.getAllBranch();
    }


    @GetMapping("/{branchId}/student/list")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public List<StudentResponseWithBatch> getStudentListInBranch(@PathVariable("branchId")Long branchId){
        return branchService.getStudentsListInBranch(branchId);
    }

    // branch list with available batch i mean whose status is -> ongoing , upcoming show that batch with
    // branch
    @GetMapping("/available/batch")
    public List<BranchWithBatchResponse> getBranchListWithAvailableBatchList(){
        return branchService.getBranchListWithAvailableBatchList();
    }
}
