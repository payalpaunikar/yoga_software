package com.yoga.controller;

import com.yoga.datamodel.Student;
import com.yoga.dto.request.BatchRequest;
import com.yoga.dto.response.BatchResponse;
import com.yoga.service.BatchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/batch")
@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
@CrossOrigin(origins = "*")
public class BatchController {

      private BatchService batchService;

      @Autowired
      public BatchController(BatchService batchService){
          this.batchService = batchService;
      }

      @PostMapping("/branch/{branchId}")
      public BatchResponse addBatch(@PathVariable("branchId")Long branchId,@RequestBody BatchRequest batchRequest){
          return batchService.addBatch(branchId,batchRequest);
      }

      @DeleteMapping("/{batchId}")
      public ResponseEntity<?> deleteBatch(@PathVariable("batchId")Long batchId){
          return ResponseEntity.ok(batchService.deleteBatch(batchId));
      }

      @PutMapping("/{batchId}")
      public BatchResponse updateBatchDetails(@PathVariable("batchId")Long batchId,@RequestBody BatchRequest batchRequest){
          return batchService.updateBatchDetails(batchId,batchRequest);
      }


      @GetMapping("/{batchId}")
      public BatchResponse getBatchById(@PathVariable("batchId")Long batchId){
          return batchService.getBatchById(batchId);
      }

      @PatchMapping("/{batchId}/branch/{branchId}")
      public BatchResponse updateBatchBranch(@PathVariable("batchId")Long batchId,@PathVariable("branchId")Long branchId){
        return batchService.updateBatchBranch(batchId,branchId);
      }


      //get List of batch from the branch whose status is "UPCOMING" OR "ONGOING"
      @GetMapping("/available/branch/{branchId}")
      public List<BatchResponse> getAvailableBatchListInBranch(@PathVariable("branchId")Long branchId){
        return batchService.getAvailableBatchListInBranch(branchId);
      }


      @GetMapping("/list/branch/{branchId}")
      public List<BatchResponse> getAllBatchesInTheBranch(@PathVariable("branchId")Long branchId){
        return batchService.getAllBatchesFromBranch(branchId);
      }

      @GetMapping("/{batchId}/student/list")
      public List<Student> getStudentInBatch(@PathVariable("batchId")Long batchId){
           return batchService.getStudentInBatch(batchId);
      }


}
