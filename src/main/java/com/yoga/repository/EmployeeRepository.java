package com.yoga.repository;


import com.yoga.datamodel.Employee;
import com.yoga.datamodel.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE e.branch.branchId= :branchId")
    List<Employee> findByEmployeeAccordindBranch(@Param("branchId")Long branchId);

    @Query("SELECT e FROM Employee e JOIN e.user u  WHERE u.id = :userId ")
    Employee findEmployeeByUserId(@Param("userId") Long userId);
}
