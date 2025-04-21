package com.yoga.repository;


import com.yoga.component.Role;
import com.yoga.datamodel.Employee;
import com.yoga.datamodel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    User getByEmail(String email);
    List<User> findAllByRole(Role role);

    @Query("SELECT u FROM User u WHERE u.role = :role")
    Optional<User> findByRole(@Param("role") Role role);
}
