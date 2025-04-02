package dev.andrylat.raqimbek.bankingutils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MortgageCalculatorDialog implements Dialog {
  @NonNull private final UserInteraction userInteraction;

  public void run() {
    var validator = new MortgageInputValidator();
    var calculator = new MortgageCalculator();
    var inputList = promptForMonthlyMortgagePaymentCalculatorData();
    var validationInfo = validator.validate(inputList);

    if (validationInfo.isValid()) {
      var monthlyPayment = calculator.calculateMonthlyMortgagePayment(inputList);
      var message =
          new StringBuilder("Your monthly mortgage payment is ").append(monthlyPayment).toString();
      userInteraction.write(message);
    } else {
      userInteraction.write("Input data is incorrect. Errors:");
      userInteraction.writeAll(validationInfo.errors());
    }
  }

  private @NonNull List<String> promptForMonthlyMortgagePaymentCalculatorData() {
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
