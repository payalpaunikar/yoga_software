package com.yoga.controller;



import com.yoga.datamodel.Lead;
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
    public ResponseEntity<Lead> addNewLead(@RequestBody Lead lead) {
        Lead savedLead = leadService.addNewLead(lead);
        return ResponseEntity.ok(savedLead);
    }

    @PutMapping("/{leadId}")
    public ResponseEntity<Lead> updateLead(@PathVariable("leadId")Long leadId,@RequestBody Lead lead){
        return ResponseEntity.ok(leadService.updateLead(leadId,lead));
    }

    @PostMapping("/{id}/addLogs")
    public ResponseEntity<Lead> addLogsToLead(@PathVariable Long id, @RequestBody List<LeadLog> leadLogs) {
        Lead updatedLead = leadService.addLogsToLead(id, leadLogs);
        return ResponseEntity.ok(updatedLead);
    }
    @GetMapping("/getlead/{id}")
    public ResponseEntity<Lead> getLeadById(@PathVariable Long id) {
        Lead lead = leadService.getLeadById(id); // Fetch the lead with logs
        return ResponseEntity.ok(lead);
    }
    @GetMapping("/getAllLeads")
    public ResponseEntity<List<Lead>> getAllLeads() {
        List<Lead> leads = leadService.getAllLeads();
        return ResponseEntity.ok(leads);
    }

    @PostMapping("/remark/{id}/remark")
    public ResponseEntity<Lead> addRemark(@PathVariable Long id, @RequestBody RemarkRequest remarkRequest) {
        Lead updatedLead = leadService.addRemark(id, remarkRequest.getRemark(), remarkRequest.getRemarkdate());
        return ResponseEntity.ok(updatedLead);
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

