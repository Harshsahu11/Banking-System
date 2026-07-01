package com.Banking.BankingSystembackend.controller;

import com.Banking.BankingSystembackend.dto.request.AmountRequest;
import com.Banking.BankingSystembackend.dto.request.CreateAccountRequest;
import com.Banking.BankingSystembackend.dto.request.TransferRequest;
import com.Banking.BankingSystembackend.dto.response.AccountResponse;
import com.Banking.BankingSystembackend.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Tag(name = "Account Management", description = "APIs for managing bank accounts")
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "Create Bank Account")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Account Created Successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid Request")
    })
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
            @Valid @RequestBody CreateAccountRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(accountService.createAccount(request));
    }

    @Operation(summary = "Get Account By Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Account Not Found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(

            @Parameter(description = "Account Id", example = "1")
            @PathVariable Long id) {

        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @Operation(summary = "Get All Accounts")
    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {

        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @Operation(summary = "Deposit Money")
    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountResponse> deposit(

            @Parameter(description = "Account Id", example = "1")
            @PathVariable Long id,

            @Valid
            @RequestBody AmountRequest request) {

        return ResponseEntity.ok(
                accountService.deposit(id, request.getAmount()));
    }

    @Operation(summary = "Withdraw Money")
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountResponse> withdraw(

            @Parameter(description = "Account Id", example = "1")
            @PathVariable Long id,

            @Valid
            @RequestBody AmountRequest request) {

        return ResponseEntity.ok(
                accountService.withdraw(id, request.getAmount()));
    }

    @Operation(summary = "Transfer Money")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transfer Successful"),
            @ApiResponse(responseCode = "400", description = "Invalid Request"),
            @ApiResponse(responseCode = "404", description = "Account Not Found")
    })
    @PutMapping("/transfer")
    public ResponseEntity<AccountResponse> transfer(

            @Valid
            @RequestBody TransferRequest request) {

        return ResponseEntity.ok(
                accountService.transfer(request));
    }

    @Operation(summary = "Delete Account")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deleted Successfully"),
            @ApiResponse(responseCode = "404", description = "Account Not Found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(

            @Parameter(description = "Account Id", example = "1")
            @PathVariable Long id) {

        accountService.deleteAccount(id);

        return ResponseEntity.noContent().build();
    }
}