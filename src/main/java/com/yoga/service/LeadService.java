package com.yoga.service;

import com.yoga.datamodel.Lead;
import com.yoga.datamodel.LeadLog;
import com.yoga.exception.UserNotFoundException;
import com.yoga.repository.LeadLogRepository;
import com.yoga.repository.LeadRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LeadService {
    @Autowired
    private LeadRepository leadRepository;

    @Autowired
    private LeadLogRepository leadLogRepository;
    public Lead addNewLead(Lead lead) {
        return leadRepository.save(lead);
    }

    public Lead addLogsToLead(Long id, List<LeadLog> leadLogs) {
        // Find the lead by ID or throw an exception if not found
        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lead not found with ID: " + id));
        // Set the lead for each LeadLog
        for (LeadLog log : leadLogs) {
            log.setLead(lead); // Ensure Lead is set in each log
        }
        // Add the logs to the lead
        lead.getLeadLogs().addAll(leadLogs);
        // Update the lead's status based on the latest LeadLog status
        if (!leadLogs.isEmpty()) {
            LeadLog latestLog = leadLogs.get(leadLogs.size() - 1); // Get the latest added log
            lead.setStatus(latestLog.getStatus()); // Update the lead status
        }
        // Save the lead and the logs
        leadLogRepository.saveAll(leadLogs);
        return lead; // Return the updated lead
    }

    public List<Lead> getAllLeads() {
        return leadRepository.findAll();
    }

    public Lead addRemark(Long id, String remark, LocalDate remarkDate) {
        Optional<Lead> optionalLead = leadRepository.findById(id);
        if (optionalLead.isPresent()) {
            Lead lead = optionalLead.get();
            lead.setRemark(remark);
            lead.setRemarkdate(remarkDate); // Set the remark date
            return leadRepository.save(lead);
        } else {
            throw new RuntimeException("Lead not found with id: " + id);
        }
    }

    @Transactional
    public boolean deleteLead(Long id) {
        Optional<Lead> lead = leadRepository.findById(id);
        if (lead.isPresent()) {
            // First, delete all LeadLogs associated with the Lead
            leadLogRepository.deleteByLeadId(id); // Assuming you have this method in your LeadLog repository

            // Then, delete the Lead
            leadRepository.delete(lead.get());
            return true;
        }
        return false;
    }

    public Lead getLeadById(Long id) {
        return leadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lead not found with ID: " + id));
    }

    public Lead updateLead(Long leadId,Lead lead){
        Lead getLead =  leadRepository.findById(leadId)
                .orElseThrow(()-> new UserNotFoundException("lead with id : "+leadId+" is not found"));

        getLead.setEmail(lead.getEmail());
        getLead.setName(lead.getName());
        getLead.setPhoneNumber(lead.getPhoneNumber());
        getLead.setWhatappNumber(lead.getWhatappNumber());
        getLead.setFoundOn(lead.getFoundOn());
        getLead.setRemark(lead.getRemark());
        getLead.setRemarkdate(lead.getRemarkdate());
        getLead.setStatus(lead.getStatus());
        getLead.setStatusMode(lead.getStatusMode());

        Lead saveLead = leadRepository.save(getLead);
        return saveLead;
    }
}