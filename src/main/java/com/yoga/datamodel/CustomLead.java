package com.yoga.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.yoga.component.LeadStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
   // @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
   // @JsonIgnoreProperties(value = { "customLead" }, allowSetters = true)
    @JsonIgnoreProperties(value = { "customLead" }) // Prevent recursion
    private List<LeadLog> leadLogs = new ArrayList<>();


}
