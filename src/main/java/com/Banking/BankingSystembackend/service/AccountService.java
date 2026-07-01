package com.Banking.BankingSystembackend.service;

import com.Banking.BankingSystembackend.dto.request.CreateAccountRequest;
import com.Banking.BankingSystembackend.dto.request.TransferRequest;
import com.Banking.BankingSystembackend.dto.response.AccountResponse;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    AccountResponse createAccount(CreateAccountRequest request);

    AccountResponse getAccountById(Long id);

    List<AccountResponse> getAllAccounts();

    AccountResponse deposit(Long id, BigDecimal amount);

    AccountResponse withdraw(Long id, BigDecimal amount);

    AccountResponse transfer(TransferRequest request);

    void deleteAccount(Long id);
}