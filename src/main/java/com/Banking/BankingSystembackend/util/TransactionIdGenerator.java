package com.Banking.BankingSystembackend.util;

public class TransactionIdGenerator {

    private TransactionIdGenerator() {
    }

    public static String generateTransactionId() {

        return "TXN" + System.currentTimeMillis();

    }

}