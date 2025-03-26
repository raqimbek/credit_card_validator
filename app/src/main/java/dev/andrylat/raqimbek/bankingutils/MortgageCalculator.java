package dev.andrylat.raqimbek.bankingutils;

public class MortgageCalculator {
  public double calculateMortgagePaymentWithInterest(
      double borrowedAmount, double annualInterestRate, double numberOfYears) {
    annualInterestRate /= 100;

    var payBackForEveryCurrencyUnit =
        1
            + annualInterestRate * numberOfYears / 2
            + Math.pow(annualInterestRate * numberOfYears, 2) / 12;
    var numberOfMonthlyPayments = numberOfYears * 12;
    return Math.round(borrowedAmount * payBackForEveryCurrencyUnit / numberOfMonthlyPayments);
  }
}
