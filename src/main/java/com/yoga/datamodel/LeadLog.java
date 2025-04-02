package com.yoga.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yoga.component.LeadStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class LeadLog {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "log_date")
    private LocalDate logDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private LeadStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "leadLogs" }, allowSetters = true)
    private CustomLead customLead;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDate logDate) {
        this.logDate = logDate;
    }

    public LeadStatus getStatus() {
        return status;
    }

    public void setStatus(LeadStatus status) {
        this.status = status;
    }

    public CustomLead getLead() {
        return customLead;
    }

    public void setLead(CustomLead customLead) {
        this.customLead = customLead;
    }
}