package com.yoga.repository;

import com.yoga.datamodel.LeadLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadLogRepository extends JpaRepository<LeadLog, Long> {
    void deleteByLeadId(Long id);
}