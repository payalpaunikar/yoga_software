package com.yoga.repository;


import com.yoga.datamodel.CustomLead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadRepository extends JpaRepository<CustomLead,Long> {
}