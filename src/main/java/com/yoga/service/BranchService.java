package com.yoga.service;

import com.yoga.component.BatchShift;
import com.yoga.component.BatchStatus;
import com.yoga.datamodel.Branch;
import com.yoga.datamodel.Student;
import com.yoga.dto.request.BranchRequest;
import com.yoga.dto.response.BranchResponse;
import com.yoga.dto.response.BranchWithBatchResponse;
import com.yoga.dto.response.StudentResponseWithBatch;
import com.yoga.exception.UserNotFoundException;
import com.yoga.repository.BranchRepository;
import com.yoga.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BranchService {

    private BranchRepository branchRepository;

    private StudentRepository studentRepository;

    @Autowired
    public BranchService(BranchRepository branchRepository,StudentRepository studentRepository){
        this.branchRepository = branchRepository;
        this.studentRepository = studentRepository;
    }

    public BranchResponse addBranch(BranchRequest branchRequest){
        Branch newBranch = new Branch();
        newBranch = convertBrachRequestToBranch(branchRequest,newBranch);
        Branch saveBranch = branchRepository.save(newBranch);

        BranchResponse newBranchResponse = new BranchResponse();
         newBranchResponse = convertBranchToBranchResponse(newBranchResponse,saveBranch);

        return newBranchResponse;
    }

    public String deleteBranch(Long branchId){
        Branch existingBranch = branchRepository.findById(branchId)
                .orElseThrow(()-> new UserNotFoundException("Branch is not found with : "+branchId+" id"));


        branchRepository.delete(existingBranch);

        return "Branch is delete successfully";
    }

    public BranchResponse getBranchById(Long branchId){
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(()->new UserNotFoundException("branch not exit with : "+branchId+" id"));
        BranchResponse branchResponse = new BranchResponse();

        branchResponse = convertBranchToBranchResponse(branchResponse,branch);

        return branchResponse;
    }

    public List<BranchResponse> getAllBranch(){
      List<Branch> branchList = branchRepository.findAll();
      List<BranchResponse> branchResponseList = branchList.stream()
              .map(branch -> {
                  BranchResponse branchResponse = new BranchResponse();
                   branchResponse = convertBranchToBranchResponse(branchResponse,branch);
                  return branchResponse;
              }).toList();

      return branchResponseList;
    }

    public BranchResponse updateBranch(Long branchId,BranchRequest branchRequest){
        Branch existingBranch = branchRepository.findById(branchId)
                .orElseThrow(()-> new UserNotFoundException("branch with : "+branchId+" id is not exit"));

        existingBranch = convertBrachRequestToBranch(branchRequest,existingBranch);

        Branch saveBranch = branchRepository.save(existingBranch);

        BranchResponse branchResponse = new BranchResponse();

        branchResponse = convertBranchToBranchResponse(branchResponse,saveBranch);

        return branchResponse;
    }



      public List<StudentResponseWithBatch> getStudentsListInBranch(Long branchId){
        Branch existingBranch = branchRepository.findById(branchId)
                .orElseThrow(()-> new UserNotFoundException("Branch is not found with : "+branchId+" id in the system"));

         List<Student> studentList= studentRepository.findStudentByBranchId(branchId);
         List<StudentResponseWithBatch> studentResponseWithBatches = studentList.stream()
                 .map(student -> {
                     StudentResponseWithBatch studentResponseWithBatch = new StudentResponseWithBatch();
                     studentResponseWithBatch.setStudentId(student.getStudentId());
                     studentResponseWithBatch.setFirstName(student.getFirstName());
                     studentResponseWithBatch.setLastName(student.getLastName());
                     studentResponseWithBatch.setEmail(student.getEmail());
                     studentResponseWithBatch.setMobileNumber(student.getMobileNumber());
                     studentResponseWithBatch.setNationality(student.getNationality());
                     studentResponseWithBatch.setBatchId(student.getBatch().getBatchId());
                     studentResponseWithBatch.setBatchName(student.getBatch().getBatchName());
                     return studentResponseWithBatch;
                 }).toList();
         return  studentResponseWithBatches;
      }


      public List<BranchWithBatchResponse> getBranchListWithAvailableBatchList(){
        List<Object[]> existingBranch = branchRepository.getBranchListWithAvailableBatchList();

        List<BranchWithBatchResponse> branchWithBatchResponseList = new ArrayList<>();

        for(Object[] branchList:existingBranch){
           BranchWithBatchResponse branchWithBatchResponse = new BranchWithBatchResponse();
           branchWithBatchResponse.setBranchId((Long) branchList[0]);
           branchWithBatchResponse.setBranchName((String) branchList[1]);
           branchWithBatchResponse.setBranchAddress((String) branchList[2]);
           branchWithBatchResponse.setBatchId((Long) branchList[3]);
           branchWithBatchResponse.setBatchName((String) branchList[4]);
           branchWithBatchResponse.setBatchShift((String) branchList[5]);
           branchWithBatchResponse.setBatchStatus((String) branchList[6]);
           branchWithBatchResponse.setStartDate(String.valueOf(branchList[7]));
           branchWithBatchResponse.setEndingDate(String.valueOf(branchList[8]));

           branchWithBatchResponseList.add(branchWithBatchResponse);
        }

          return branchWithBatchResponseList;
      }

      public Branch convertBrachRequestToBranch(BranchRequest branchRequest,Branch branch){
        branch.setBranchName(branchRequest.getBranchName());
        branch.setCity(branchRequest.getCity());
        branch.setAddress(branchRequest.getAddress());

        return branch;
      }

      public BranchResponse convertBranchToBranchResponse(BranchResponse branchResponse,Branch branch){
        branchResponse.setCity(branch.getCity());
        branchResponse.setAddress(branch.getAddress());
        branchResponse.setBranchName(branch.getBranchName());
        branchResponse.setBranchId(branch.getBranchId());
        return branchResponse;
      }
}
