package dev.andrylat.raqimbek.bankingutils.services.mortgagecalculator;

import java.util.List;

public class MortgageCalculator {
  public Long calculateMonthlyMortgagePayment(List<String> inputList) {
    var borrowedAmount = Double.parseDouble(inputList.get(0));
    var annualInterestRate = Double.parseDouble(inputList.get(1));
    var numberOfYears = Double.parseDouble(inputList.get(2));
    annualInterestRate /= 100;

    var payBackForEveryCurrencyUnit =
        1
            + annualInterestRate * numberOfYears / 2
            + Math.pow(annualInterestRate * numberOfYears, 2) / 12;
    var numberOfMonthlyPayments = numberOfYears * 12;

    return Math.round(borrowedAmount * payBackForEveryCurrencyUnit / numberOfMonthlyPayments);
  }
}
