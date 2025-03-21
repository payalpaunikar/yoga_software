package com.yoga.controller;



import com.yoga.datamodel.Branch;
import com.yoga.datamodel.Employee;
import com.yoga.dto.StudentFeesAlertDto;
import com.yoga.dto.request.BranchRequest;
import com.yoga.dto.request.EmployeeRequest;
import com.yoga.dto.response.EmployeeResponse;
import com.yoga.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PostAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "*")
public class AdminController {

       private AdminService adminService;

       @Autowired
       public AdminController(AdminService adminService){
           this.adminService = adminService;
       }

       @PostMapping("/register/emp")
       public EmployeeResponse addEmployee(@RequestBody EmployeeRequest employeeRequest){
         return adminService.addEmployee(employeeRequest);
       }

       @DeleteMapping("/emp/{employeeId}")
       public ResponseEntity<?> deleteEmployee(@PathVariable("employeeId") Long employeeId){
           return ResponseEntity.ok(adminService.deleteEmployee(employeeId));
       }

      @PutMapping("/emp/{empId}")
      public EmployeeResponse updateEmployee(@PathVariable("empId")Long empId,@RequestBody EmployeeRequest employeeRequest){
           return adminService.updateEmployee(empId,employeeRequest);
      }


     @GetMapping("/employees")
     public List<EmployeeResponse> getListOfEmployee(){
           return adminService.getListOfEmployee();
     }

     @GetMapping("/emp/{empId}")
     public EmployeeResponse getEmployeeById(@PathVariable("empId")Long empId){
           return adminService.getEmployeeById(empId);
     }

     @PostMapping("/branch/{branchId}/assign/emp/{employeeId}")
     public String assignBranchToEmployee(@PathVariable("branchId")Long branchId,@PathVariable("employeeId")Long employeeId){
           return adminService.assignBranchToEmployee(branchId,employeeId);
     }


     @GetMapping("/student/overdue/fees/alert")
     public List<StudentFeesAlertDto> getStudentFeesAlert(){
        return adminService.getStudentFeeAlert();
     }

}
