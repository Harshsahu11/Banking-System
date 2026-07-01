package com.Banking.BankingSystembackend.validation;

import java.math.BigDecimal;

public class AccountValidation {

    private AccountValidation() {
    }

    public static void validateAmount(BigDecimal amount) {

        if (amount == null ||
                amount.compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "Amount must be greater than zero.");
        }
    }

}