package dev.andrylat.raqimbek.bankingutils;

import lombok.NonNull;

import java.util.List;

public class MortgageCalculator implements BankingService<Long, Double> {
  @NonNull
  public Long run(@NonNull List<Double> inputList) {
    var borrowedAmount = inputList.get(0);
    var annualInterestRate = inputList.get(1);
    var numberOfYears = inputList.get(2);
    annualInterestRate /= 100;

    var payBackForEveryCurrencyUnit =
        1
            + annualInterestRate * numberOfYears / 2
            + Math.pow(annualInterestRate * numberOfYears, 2) / 12;
    var numberOfMonthlyPayments = numberOfYears * 12;
    return Math.round(borrowedAmount * payBackForEveryCurrencyUnit / numberOfMonthlyPayments);
  }

  public double calculateTotalInterestPayment(
      double borrowedAmount, double annualInterestRate, double numberOfYears) {
    return borrowedAmount
        * ((annualInterestRate * numberOfYears / 2)
            + (Math.pow(annualInterestRate * numberOfYears, 2) / 12));
  }
}
