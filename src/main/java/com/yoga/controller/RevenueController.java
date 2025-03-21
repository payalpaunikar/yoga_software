package com.yoga.controller;


import com.yoga.dto.RevenueReportDTO;
import com.yoga.dto.RevenueSummaryDTO;
import com.yoga.dto.request.BranchRevenueMonthWiseRequest;
import com.yoga.dto.response.BranchRevenueMonthWiseResponse;
import com.yoga.service.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/revenue")
@PostAuthorize("hasRole('ADMIN')")
@CrossOrigin("*")
public class RevenueController {

           private RevenueService revenueService;

           @Autowired
           public RevenueController(RevenueService revenueService){
               this.revenueService = revenueService;
           }

           @GetMapping("/summary")
           public RevenueSummaryDTO getRevenueSummary(){
              return revenueService.getRevenueSummary();
           }


           @PostMapping("/branch")
           public List<BranchRevenueMonthWiseResponse> getBranchRevenueSummaryMonthWise(
                   @RequestBody BranchRevenueMonthWiseRequest branchRevenueMonthWiseRequest){
               return revenueService.getBranchRevenueMonthWise(branchRevenueMonthWiseRequest.getBranchId(), branchRevenueMonthWiseRequest.getStartingDate(),branchRevenueMonthWiseRequest.getEndingDate());
           }

           @GetMapping
           public List<RevenueReportDTO> getRevenueReportAcccordingDate(@RequestParam("startDate") String startDate,
                                                                        @RequestParam("endDate") String endDate){
               return revenueService.getRevenueAccordingDate(LocalDate.parse(startDate),LocalDate.parse(endDate));
           }
}
