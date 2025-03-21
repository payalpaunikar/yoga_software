package com.yoga.controller;


import com.yoga.datamodel.Employee;
import com.yoga.dto.StudentFeesAlertDto;
import com.yoga.dto.response.BranchResponse;
import com.yoga.service.AdminService;
import com.yoga.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@CrossOrigin("*")
@PreAuthorize("hasRole('EMPLOYEE')")
public class EmployeeController {

         private EmployeeService employeeService;

         @Autowired
         public EmployeeController(EmployeeService employeeService){
             this.employeeService = employeeService;
         }

         @GetMapping("/user/{userId}/branch")
         public BranchResponse getBranchByUserId(@PathVariable("userId")Long userId){
           return employeeService.getBranchByUserId(userId);
         }


    @GetMapping("/student/overdue/fee/alert/batch/{batchId}")
    public List<StudentFeesAlertDto> getBranchWiseStudentDueFeeAlert(@PathVariable("batchId")Long batchId){
      return employeeService.getBranchWiseStudentDueFeeAlert(batchId);
    }

}
