package dev.andrylat.raqimbek.bankingutils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MortgageCalculatorDialog implements Dialog {
  @NonNull private final UserInteraction userInteraction;

  public @NonNull List<String> prompt() {
    @NonNull
    String promptMessage =
        "Please enter information regarding your mortgage in the following order and separate lines:\n-amount of money you borrowed \n- annual interest rate\n- number of years you have to pay\n";

    userInteraction.write(promptMessage);

    var borrowedAmount = userInteraction.read();
    var annualInterestRate = userInteraction.read();
    var numberOfYears = userInteraction.read();

    return List.of(borrowedAmount, annualInterestRate, numberOfYears);
  }
}
