package com.yoga.repository;


import com.yoga.datamodel.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE e.branch.branchId= :branchId")
    List<Employee> findByEmployeeAccordindBranch(@Param("branchId")Long branchId);

    @Query("SELECT e FROM Employee e JOIN e.user u  WHERE u.id = :userId ")
    Employee findEmployeeByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE Employee e SET e.branch = NULL WHERE e.branch.id = :branchId")
    void updateBranchToNull(@Param("branchId") Long branchId);

}
