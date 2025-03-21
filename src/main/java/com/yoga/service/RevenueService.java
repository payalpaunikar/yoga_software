package com.yoga.service;


import com.yoga.datamodel.StudentFee;
import com.yoga.datamodel.StudentFeeTransaction;
import com.yoga.dto.RevenueReportDTO;
import com.yoga.dto.RevenueSummaryDTO;
import com.yoga.dto.response.BranchRevenueMonthWiseResponse;
import com.yoga.repository.BatchRepository;
import com.yoga.repository.BranchRepository;
import com.yoga.repository.StudentFeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RevenueService {

    private StudentFeeRepository studentFeeRepository;

    private BatchRepository batchRepository;

    private BranchRepository branchRepository;

    Logger logger = LoggerFactory.getLogger(RevenueService.class);


    @Autowired
    public RevenueService(StudentFeeRepository studentFeeRepository,BatchRepository batchRepository,BranchRepository branchRepository){
        this.studentFeeRepository = studentFeeRepository;
        this.batchRepository = batchRepository;
        this.branchRepository = branchRepository;
    }


     public List<RevenueReportDTO> getRevenueAccordingDate(LocalDate startingDate,LocalDate endingDate){

         return batchRepository.getRevenueReport(startingDate,endingDate);
     }


     public List<BranchRevenueMonthWiseResponse> getBranchRevenueMonthWise(Long branchId,String startingDate,String endingDate){
        List<Object[]> branchRevenueSummayMonthWiseObject= branchRepository.getBranchRevenueSummayMonthWise(branchId,LocalDate.parse(startingDate),LocalDate.parse(endingDate));

         List<BranchRevenueMonthWiseResponse> branchRevenueMonthWiseResponseList = new ArrayList<>();

        for (Object[] branchRevenue : branchRevenueSummayMonthWiseObject){
            BranchRevenueMonthWiseResponse branchRevenueMonthWiseResponse = new BranchRevenueMonthWiseResponse();
            branchRevenueMonthWiseResponse.setBranchId((Long) branchRevenue[0]);
            branchRevenueMonthWiseResponse.setBranchName((String) branchRevenue[1]);
            branchRevenueMonthWiseResponse.setMonth((String) branchRevenue[2]);
            branchRevenueMonthWiseResponse.setTotalCollection((Double) branchRevenue[3]);

            branchRevenueMonthWiseResponseList.add(branchRevenueMonthWiseResponse);
        }

         return branchRevenueMonthWiseResponseList;

     }


     public RevenueSummaryDTO getRevenueSummary(){
        List<Object[]> revenueSummary = branchRepository.getRevenueSummary();
         RevenueSummaryDTO revenueSummaryDTO = new RevenueSummaryDTO();
        for (Object[] revenue : revenueSummary){
            revenueSummaryDTO.setTotalBranches((Long) revenue[0]);
            revenueSummaryDTO.setTotalRevenue((Double) revenue[1]);
            revenueSummaryDTO.setLastMonthRevenue((Double) revenue[2]);
        }
        return revenueSummaryDTO;

     }

}
