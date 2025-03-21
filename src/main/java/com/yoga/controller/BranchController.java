package com.yoga.controller;


import com.yoga.datamodel.Branch;
import com.yoga.datamodel.Employee;
import com.yoga.datamodel.Student;
import com.yoga.dto.request.BranchRequest;
import com.yoga.dto.response.BranchResponse;
import com.yoga.dto.response.EmployeeResponse;
import com.yoga.service.BranchService;
import com.yoga.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branch")
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "*")
public class BranchController {

    private BranchService branchService;

    private EmployeeService employeeService;

    @Autowired
    public BranchController(BranchService branchService,EmployeeService employeeService){
        this.branchService = branchService;
        this.employeeService = employeeService;
    }

    @PostMapping("/")
    public ResponseEntity<?> addBranch(@RequestBody BranchRequest branchRequest){
        return ResponseEntity.ok(branchService.addBranch(branchRequest));
    }

    @DeleteMapping("/{branchId}")
    public ResponseEntity<?> deleteBranch(@PathVariable("branchId")Long branchId){
        return ResponseEntity.ok(branchService.deleteBranch(branchId));
    }


    @PutMapping("/{branchId}")
    public ResponseEntity<?> updateBranch(@PathVariable("branchId")Long branchId,@RequestBody  BranchRequest branchRequest){
        return ResponseEntity.ok(branchService.updateBranch(branchId,branchRequest));
    }

    @GetMapping("/{branchId}")
    public BranchResponse getBranchById(@PathVariable("branchId")Long branchId){
        return branchService.getBranchById(branchId);
    }


    @GetMapping("/{branchId}/list/employee")
    public List<EmployeeResponse> getListOfEmployeeAccordingBranch(@PathVariable("branchId")Long branchId){
        return employeeService.getListOfEmployeeAccordingBranch(branchId);
    }

}
