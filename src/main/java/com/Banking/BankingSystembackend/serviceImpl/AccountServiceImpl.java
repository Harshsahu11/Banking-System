package com.Banking.BankingSystembackend.serviceImpl;

import com.Banking.BankingSystembackend.dto.request.CreateAccountRequest;
import com.Banking.BankingSystembackend.dto.request.TransferRequest;
import com.Banking.BankingSystembackend.dto.response.AccountResponse;
import com.Banking.BankingSystembackend.entity.Account;
import com.Banking.BankingSystembackend.exception.ResourceNotFoundException;
import com.Banking.BankingSystembackend.repository.AccountRepository;
import com.Banking.BankingSystembackend.repository.TransactionRepository;
import com.Banking.BankingSystembackend.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.Banking.BankingSystembackend.entity.Transaction;
import com.Banking.BankingSystembackend.entity.TransactionType;
import com.Banking.BankingSystembackend.exception.InsufficientBalanceException;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    @Override
    public AccountResponse createAccount(CreateAccountRequest request) {

        if(accountRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exists.");
        }

        String accountNumber = generateAccountNumber();

        while(accountRepository.existsByAccountNumber(accountNumber)){
            accountNumber = generateAccountNumber();
        }

        Account account = Account.builder()
                .accountHolderName(request.getAccountHolderName())
                .email(request.getEmail())
                .balance(request.getBalance())
                .accountNumber(accountNumber)
                .build();

        account = accountRepository.save(account);

        return mapToResponse(account);
    }

    @Override
    public AccountResponse getAccountById(Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Account not found with id : " + id));

        return mapToResponse(account);
    }

    @Override
    public List<AccountResponse> getAllAccounts() {

        return accountRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteAccount(Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Account not found with id : " + id));

        accountRepository.delete(account);
    }


    private AccountResponse mapToResponse(Account account){

        return AccountResponse.builder()
                .id(account.getId())
                .accountHolderName(account.getAccountHolderName())
                .accountNumber(account.getAccountNumber())
                .email(account.getEmail())
                .balance(account.getBalance())
                .build();
    }

    private String generateAccountNumber(){

        Random random = new Random();

        long number = 1000000000L + random.nextInt(900000000);

        return "ACC" + number;
    }

    @Override
    public AccountResponse deposit(Long id, BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        }

        Account account = accountRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account not found with id : " + id));

        account.setBalance(account.getBalance().add(amount));

        accountRepository.save(account);

        saveTransaction(
                account.getId(),
                null,
                TransactionType.DEPOSIT,
                amount,
                account.getBalance()
        );

        return mapToResponse(account);
    }

    @Override
    public AccountResponse withdraw(Long id, BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
        }

        Account account = accountRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account not found with id : " + id));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient account balance.");
        }

        account.setBalance(account.getBalance().subtract(amount));

        accountRepository.save(account);

        saveTransaction(
                account.getId(),
                null,
                TransactionType.WITHDRAW,
                amount,
                account.getBalance()
        );

        return mapToResponse(account);
    }

    @Override
    @Transactional
    public AccountResponse transfer(TransferRequest request) {

        Account sender = accountRepository.findByAccountNumber(
                        request.getFromAccountNumber())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Sender account not found."));

        Account receiver = accountRepository.findByAccountNumber(
                        request.getToAccountNumber())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Receiver account not found."));

        if (sender.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient account balance.");
        }

        sender.setBalance(sender.getBalance().subtract(request.getAmount()));

        receiver.setBalance(receiver.getBalance().add(request.getAmount()));

        accountRepository.save(sender);

        accountRepository.save(receiver);

        saveTransaction(
                sender.getId(),
                receiver.getId(),
                TransactionType.TRANSFER,
                request.getAmount(),
                sender.getBalance()
        );

        return mapToResponse(sender);
    }

    private void saveTransaction(

            Long accountId,

            Long receiverAccountId,

            TransactionType transactionType,

            BigDecimal amount,

            BigDecimal balanceAfterTransaction) {

        Transaction transaction = Transaction.builder()

                .transactionId(generateTransactionId())

                .transactionType(transactionType)

                .accountId(accountId)

                .receiverAccountId(receiverAccountId)

                .amount(amount)

                .balanceAfterTransaction(balanceAfterTransaction)

                .build();

        transactionRepository.save(transaction);
    }

    private String generateTransactionId() {

        return "TXN" + System.currentTimeMillis();
    }

}