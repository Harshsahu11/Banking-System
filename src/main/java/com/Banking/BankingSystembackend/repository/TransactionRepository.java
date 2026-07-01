package com.Banking.BankingSystembackend.repository;

import com.Banking.BankingSystembackend.entity.Transaction;
import com.Banking.BankingSystembackend.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountId(Long accountId);

    List<Transaction> findByTransactionType(TransactionType transactionType);

    List<Transaction> findByReceiverAccountId(Long receiverAccountId);
}