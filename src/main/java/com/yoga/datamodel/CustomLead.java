package com.yoga.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.yoga.component.LeadStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "custom_lead")
public class CustomLead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "whatapp_number")
    private String whatappNumber;

    @Column(name = "found_on")
    private LocalDate foundOn;
    @Column(name = "remark")
    private String remark;

    @Column(name = "remark_date")
    private LocalDate remarkdate;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private LeadStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "statusMode")
    private LeadStatus statusMode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customLead")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
   // @JsonIgnoreProperties(value = { "customLead" }, allowSetters = true)
    @JsonIgnore
    private List<LeadLog> leadLogs = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWhatappNumber() {
        return whatappNumber;
    }

    public void setWhatappNumber(String whatappNumber) {
        this.whatappNumber = whatappNumber;
    }

    public LocalDate getFoundOn() {
        return foundOn;
    }

    public void setFoundOn(LocalDate foundOn) {
        this.foundOn = foundOn;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDate getRemarkdate() {
        return remarkdate;
    }

    public void setRemarkdate(LocalDate remarkdate) {
        this.remarkdate = remarkdate;
    }

    public LeadStatus getStatus() {
        return status;
    }

    public void setStatus(LeadStatus status) {
        this.status = status;
    }

    public LeadStatus getStatusMode() {
        return statusMode;
    }

    public void setStatusMode(LeadStatus statusMode) {
        this.statusMode = statusMode;
    }

    public List<LeadLog> getLeadLogs() {
        return leadLogs;
    }

    public void setLeadLogs(List<LeadLog> leadLogs) {
        this.leadLogs = leadLogs;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
