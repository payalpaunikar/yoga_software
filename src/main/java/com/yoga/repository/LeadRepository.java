package com.yoga.repository;


import com.yoga.datamodel.CustomLead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeadRepository extends JpaRepository<CustomLead,Long> {

    @Query("SELECT l FROM CustomLead l WHERE l.user.userId=:userId")
    List<CustomLead> findLeadByUserId(Long userId);
}