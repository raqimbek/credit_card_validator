package dev.andrylat.raqimbek.bankingutils.services.mortgagecalculator;

public record MonthlyMortgagePaymentTestData(
    double borrowedAmount,
    double annualInterestRate,
    double numberOfYears,
    long expectedMonthlyPayment) {}
