package com.yoga.service;

import com.yoga.datamodel.CustomLead;
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
    public CustomLead addNewLead(CustomLead customLead) {
        return leadRepository.save(customLead);
    }

    public CustomLead addLogsToLead(Long id, List<LeadLog> leadLogs) {
        // Find the lead by ID or throw an exception if not found
        CustomLead customLead = leadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lead not found with ID: " + id));
        // Set the lead for each LeadLog
        for (LeadLog log : leadLogs) {
            log.setLead(customLead); // Ensure Lead is set in each log
        }
        // Add the logs to the lead
        customLead.getLeadLogs().addAll(leadLogs);
        // Update the lead's status based on the latest LeadLog status
        if (!leadLogs.isEmpty()) {
            LeadLog latestLog = leadLogs.get(leadLogs.size() - 1); // Get the latest added log
            customLead.setStatus(latestLog.getStatus()); // Update the lead status
        }
        // Save the lead and the logs
        leadLogRepository.saveAll(leadLogs);
        return customLead; // Return the updated lead
    }

    public List<CustomLead> getAllLeads() {
        return leadRepository.findAll();
    }

    public CustomLead addRemark(Long id, String remark, LocalDate remarkDate) {
        Optional<CustomLead> optionalLead = leadRepository.findById(id);
        if (optionalLead.isPresent()) {
            CustomLead customLead = optionalLead.get();
            customLead.setRemark(remark);
            customLead.setRemarkdate(remarkDate); // Set the remark date
            return leadRepository.save(customLead);
        } else {
            throw new RuntimeException("Lead not found with id: " + id);
        }
    }

    @Transactional
    public boolean deleteLead(Long id) {
        Optional<CustomLead> lead = leadRepository.findById(id);
        if (lead.isPresent()) {
            // First, delete all LeadLogs associated with the Lead
            leadLogRepository.deleteByCustomLeadId(id); // Assuming you have this method in your LeadLog repository

            // Then, delete the Lead
            leadRepository.delete(lead.get());
            return true;
        }
        return false;
    }

    public CustomLead getLeadById(Long id) {
        return leadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lead not found with ID: " + id));
    }

    public CustomLead updateLead(Long leadId, CustomLead customLead){
        CustomLead getCustomLead =  leadRepository.findById(leadId)
                .orElseThrow(()-> new UserNotFoundException("lead with id : "+leadId+" is not found"));

        getCustomLead.setEmail(customLead.getEmail());
        getCustomLead.setName(customLead.getName());
        getCustomLead.setPhoneNumber(customLead.getPhoneNumber());
        getCustomLead.setWhatappNumber(customLead.getWhatappNumber());
        getCustomLead.setFoundOn(customLead.getFoundOn());
        getCustomLead.setRemark(customLead.getRemark());
        getCustomLead.setRemarkdate(customLead.getRemarkdate());
        getCustomLead.setStatus(customLead.getStatus());
        getCustomLead.setStatusMode(customLead.getStatusMode());

        CustomLead saveCustomLead = leadRepository.save(getCustomLead);
        return saveCustomLead;
    }
}