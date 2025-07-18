package com.jag.ExpenseTracker.persistance;

import com.jag.ExpenseTracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface  UserRepository extends JpaRepository <User, Integer> {

        Optional<User> findByEmail (String email);

     User findByUsername (String username);

}
