package com.yoga.service;


import com.yoga.component.BatchStatus;
import com.yoga.datamodel.Batch;
import com.yoga.datamodel.Branch;
import com.yoga.datamodel.Student;
import com.yoga.dto.request.BatchRequest;
import com.yoga.dto.response.BatchResponse;
import com.yoga.dto.response.BranchResponse;
import com.yoga.exception.BatchNotFoundException;
import com.yoga.exception.UserNotFoundException;
import com.yoga.repository.BatchRepository;
import com.yoga.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class BatchService {

      private BatchRepository batchRepository;
      private BranchRepository branchRepository;

      @Autowired
      public BatchService(BatchRepository batchRepository,BranchRepository branchRepository){
          this.batchRepository = batchRepository;
          this.branchRepository = branchRepository;
      }

    public BatchResponse addBatch(Long branchId,BatchRequest batchRequest){
        Branch existingBranch = branchRepository.findById(branchId)
                .orElseThrow(()-> new UserNotFoundException("Branch is not exit with : "+branchId+" id"));

        Batch newBatch = convertBatchRequestToBatch(batchRequest);
        newBatch.setBranch(existingBranch);
        Batch saveBatch = batchRepository.save(newBatch);
        BatchResponse batchResponse = convertBatchToBatchResponse(saveBatch);

        return batchResponse;

    }


    public String deleteBatch(Long batchId){
        Batch existingBatch = batchRepository.findById(batchId)
                .orElseThrow(()-> new BatchNotFoundException("Batch with "+batchId+" id is not found in our system"));

        batchRepository.delete(existingBatch);

        return "batch is deleted succefully";
    }


    public BatchResponse updateBatchDetails(Long batchId,BatchRequest batchRequest){
          Batch existingBatch = batchRepository.findById(batchId)
                  .orElseThrow(()-> new BatchNotFoundException("Batch not found with :"+batchId+" batch id"));

          existingBatch.setBatchName(batchRequest.getBatchName());
          existingBatch.setStartDate(LocalDate.parse(batchRequest.getStartDate()));
          existingBatch.setStartTiming(LocalTime.parse(batchRequest.getStartTiming()));
          existingBatch.setBatchShift(batchRequest.getBatchShift());
          existingBatch.setBatchStatus(batchRequest.getBatchStatus());

          Batch saveBatch = batchRepository.save(existingBatch);
          BatchResponse batchResponse = convertBatchToBatchResponse(saveBatch);
          return batchResponse;
    }


    public BatchResponse getBatchById(Long batchId){
        Batch existingBatch = batchRepository.findById(batchId)
                .orElseThrow(()-> new UserNotFoundException("batch with : "+batchId+" id is not exit"));

        BatchResponse batchResponse = convertBatchToBatchResponse(existingBatch);
        return batchResponse;
    }


    public List<BatchResponse> getAllBatchesFromBranch(Long branchId){
     Branch existingBranch = branchRepository.findById(branchId)
             .orElseThrow(()-> new UserNotFoundException("branch with : "+branchId+" not found in the system"));

      List<Batch> existingBatchesAccordingToBranch = batchRepository.findAllBatchesByBranchId(existingBranch.getBranchId());

      List<BatchResponse> batchResponseList = existingBatchesAccordingToBranch.stream()
              .map(batch->convertBatchToBatchResponse(batch))
              .toList();

      return batchResponseList;
    }

    public BatchResponse updateBatchBranch(Long batchId,Long branchId){
        Batch existingBatch = batchRepository.findById(batchId)
                .orElseThrow(()-> new BatchNotFoundException("Batch not found with :"+batchId+" batch id"));

        Branch existingBranch = branchRepository.findById(branchId)
                .orElseThrow(()-> new UserNotFoundException("Branch not found with : "+branchId+" id"));

        existingBatch.setBranch(existingBranch);
        batchRepository.save(existingBatch);

        BatchResponse batchResponse = convertBatchToBatchResponse(existingBatch);
        return batchResponse;
    }

    public List<BatchResponse> getAvailableBatchListInBranch(Long branchId){
     List<Batch> batches = batchRepository.findAvailableBatchesInBranch(branchId,BatchStatus.UPCOMING,BatchStatus.ONGOING);
     List<BatchResponse> batchResponseList = batches.stream()
             .map(batch -> convertBatchToBatchResponse(batch))
             .toList();
     return batchResponseList;
    }


    public List<Student> getStudentInBatch(Long batchId){
        Batch existingBatch = batchRepository.findById(batchId)
                .orElseThrow(()-> new UserNotFoundException("batch with : "+batchId+" is not found in the system"));

      List<Student> studentList = existingBatch.getStudent();

      return studentList;
    }


    private Batch convertBatchRequestToBatch(BatchRequest batchRequest){
        Batch newBatch = new Batch();
        newBatch.setBatchName(batchRequest.getBatchName());
        newBatch.setStartDate(LocalDate.parse(batchRequest.getStartDate()));
        newBatch.setEndingDate(LocalDate.parse(batchRequest.getEndingDate()));
        newBatch.setStartTiming(LocalTime.parse(batchRequest.getStartTiming()));
        newBatch.setBatchStatus(batchRequest.getBatchStatus());
        newBatch.setBatchShift(batchRequest.getBatchShift());
        return newBatch;
    }



    private BatchResponse convertBatchToBatchResponse(Batch batch){
        BatchResponse newBatchRespose = new BatchResponse();
        newBatchRespose.setBatchId(batch.getBatchId());
        newBatchRespose.setBatchName(batch.getBatchName());
        newBatchRespose.setStartDate(String.valueOf(batch.getStartDate()));
        newBatchRespose.setEndingDate(String.valueOf(batch.getEndingDate()));
        newBatchRespose.setStartTiming(String.valueOf(batch.getStartTiming()));
        newBatchRespose.setBatchShift(batch.getBatchShift());
        newBatchRespose.setBatchStatus(batch.getBatchStatus());

        return  newBatchRespose;
    }
}
