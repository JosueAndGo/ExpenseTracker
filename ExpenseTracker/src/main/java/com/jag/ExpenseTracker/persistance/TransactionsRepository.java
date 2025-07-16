package com.jag.ExpenseTracker.persistance;

import com.jag.ExpenseTracker.commons.TransactionType;
import com.jag.ExpenseTracker.models.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {

        List<Transactions> findByType(TransactionType type);
        List<Transactions> findByUserId(Integer userId);

}
