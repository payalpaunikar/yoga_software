package com.yoga.controller;



import com.yoga.datamodel.CustomLead;
import com.yoga.datamodel.LeadLog;
import com.yoga.dto.request.RemarkRequest;
import com.yoga.service.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lead")
@CrossOrigin(origins = "*")
@PreAuthorize("hasAnyRole('ADMIN',EMPLOYEE)")
public class LeadController {


    @Autowired
    private LeadService leadService;

    @PostMapping("/createNewLead")
    public ResponseEntity<CustomLead> addNewLead(@RequestBody CustomLead customLead) {
        CustomLead savedCustomLead = leadService.addNewLead(customLead);
        return ResponseEntity.ok(savedCustomLead);
    }

    @PutMapping("/{leadId}")
    public ResponseEntity<CustomLead> updateLead(@PathVariable("leadId")Long leadId, @RequestBody CustomLead customLead){
        return ResponseEntity.ok(leadService.updateLead(leadId, customLead));
    }

    @PostMapping("/{id}/addLogs")
    public ResponseEntity<CustomLead> addLogsToLead(@PathVariable Long id, @RequestBody List<LeadLog> leadLogs) {
        CustomLead updatedCustomLead = leadService.addLogsToLead(id, leadLogs);
        return ResponseEntity.ok(updatedCustomLead);
    }
    @GetMapping("/getlead/{id}")
    public ResponseEntity<CustomLead> getLeadById(@PathVariable Long id) {
        CustomLead customLead = leadService.getLeadById(id); // Fetch the lead with logs
        return ResponseEntity.ok(customLead);
    }
    @GetMapping("/getAllLeads")
    public ResponseEntity<List<CustomLead>> getAllLeads() {
        List<CustomLead> customLeads = leadService.getAllLeads();
        return ResponseEntity.ok(customLeads);
    }

    @PostMapping("/remark/{id}/remark")
    public ResponseEntity<CustomLead> addRemark(@PathVariable Long id, @RequestBody RemarkRequest remarkRequest) {
        CustomLead updatedCustomLead = leadService.addRemark(id, remarkRequest.getRemark(), remarkRequest.getRemarkdate());
        return ResponseEntity.ok(updatedCustomLead);
    }

    @DeleteMapping("/deleteLead/{id}")
    public ResponseEntity<String> deleteLead(@PathVariable Long id) {
        boolean isDeleted = leadService.deleteLead(id);
        if (isDeleted) {
            return ResponseEntity.ok("Lead deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lead not found.");
        }
    }
}

