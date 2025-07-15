package com.jag.ExpenseTracker.persistance;

import com.jag.ExpenseTracker.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer> {


    Roles findByRole(String role);
}

