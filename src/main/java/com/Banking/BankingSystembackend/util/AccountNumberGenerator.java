package com.Banking.BankingSystembackend.util;

import java.security.SecureRandom;

public class AccountNumberGenerator {

    private static final SecureRandom random = new SecureRandom();

    private AccountNumberGenerator() {
    }

    public static String generateAccountNumber() {

        long number = 1000000000L + random.nextInt(900000000);

        return "ACC" + number;
    }

}