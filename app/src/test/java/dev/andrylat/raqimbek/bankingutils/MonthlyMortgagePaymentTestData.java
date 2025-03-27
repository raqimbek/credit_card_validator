package dev.andrylat.raqimbek.bankingutils;

public record MonthlyMortgagePaymentTestData(
    double borrowedAmount,
    double annualInterestRate,
    double numberOfYears,
    double expectedMonthlyPayment) {}
