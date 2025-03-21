package com.yoga.controller;

import com.yoga.dto.request.StudentRequest;
import com.yoga.dto.response.BatchResponse;
import com.yoga.dto.response.StudentDetailsResponse;
import com.yoga.dto.response.StudentResponse;
import com.yoga.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
@CrossOrigin(origins = "*")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }


    @PostMapping("/register/batch/{batchId}")
    public StudentResponse registerStudent(@PathVariable("batchId")Long batchId,@RequestBody StudentRequest studentRequest){
        return studentService.registerStudent(batchId,studentRequest);
    }


    @PutMapping("/{studentId}")
    public StudentResponse updateStudent(@PathVariable("studentId")Long studentId,@RequestBody  StudentRequest studentRequest){
         return studentService.updateStudentDetails(studentId,studentRequest);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable("studentId") Long studentId){
        return ResponseEntity.ok(studentService.deleteStudent(studentId));
    }

    @GetMapping("/{studentId}")
    public StudentDetailsResponse getStudentById(@PathVariable("studentId")Long studentId){
        return studentService.getStudentById(studentId);
    }


    @GetMapping("/{studentId}/batch")
    public BatchResponse getStudentBatch(@PathVariable("studentId")Long studentId){
        return studentService.getStudentBatch(studentId);
    }


    @PostMapping("/{studentId}/assign/batch/{batchId}")
    public String updateStudentBatch(@PathVariable("studentId")Long studentId,@PathVariable("batchId")Long batchId){
        return studentService.updateStudentBatch(studentId,batchId);
    }


}
