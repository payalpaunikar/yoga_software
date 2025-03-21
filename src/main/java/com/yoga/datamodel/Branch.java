package com.yoga.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "branch")
public class Branch {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long branchId;

      private String branchName;

      private String address;

      private String city;

      @OneToMany(mappedBy = "branch",cascade = CascadeType.ALL)
      private List<Batch> batches;

      @OneToMany(mappedBy = "branch",cascade = CascadeType.ALL,orphanRemoval = true)
      private List<Employee> employees;

}
