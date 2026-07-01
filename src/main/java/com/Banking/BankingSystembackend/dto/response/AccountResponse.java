package com.Banking.BankingSystembackend.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AccountResponse {

    private Long id;

    private String accountHolderName;

    private String accountNumber;

    private String email;

    private BigDecimal balance;
}