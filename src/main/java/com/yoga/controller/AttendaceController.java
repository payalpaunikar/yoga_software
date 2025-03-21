package com.yoga.controller;


import com.yoga.datamodel.Student;
import com.yoga.dto.request.GetAttendanceRequest;
import com.yoga.dto.request.MarkedAttendanceRequest;
import com.yoga.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
@CrossOrigin(origins = "*")
public class AttendaceController {


           private AttendanceService attendanceService;


           @Autowired
           public AttendaceController(AttendanceService attendanceService){
               this.attendanceService = attendanceService;
           }


    @GetMapping("/batch/{batchId}/attendanceDate/{attendanceDate}")
    public List<GetAttendanceRequest> getBatchWiseStudentAttendanceList(@PathVariable("batchId")Long batchId, @PathVariable("attendanceDate")String sttendanceDate){
      return attendanceService.getBatchWiseStudentAttendanceList(batchId,sttendanceDate);
    }


    @PostMapping("/batch/{batchId}/attendanceDate/{attendanceDate}")
    public String markedAttendance(@PathVariable("batchId")Long batchId,@PathVariable("attendanceDate")String attendanceDate,@RequestBody List<MarkedAttendanceRequest> markedAttendance){
       return attendanceService.markedAttendace(batchId,attendanceDate,markedAttendance);
    }

}
