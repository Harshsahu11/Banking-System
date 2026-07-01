package com.Banking.BankingSystembackend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateAccountRequest {

    @NotBlank(message = "Account holder name is required")
    private String accountHolderName;

    @NotNull(message = "Balance is required")
    @PositiveOrZero(message = "Balance cannot be negative")
    private BigDecimal balance;

    @Email(message = "Invalid email address")
    @NotBlank(message = "Email is required")
    private String email;
}