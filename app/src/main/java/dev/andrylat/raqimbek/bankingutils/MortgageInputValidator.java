package dev.andrylat.raqimbek.bankingutils;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MortgageInputValidator {
  private static final double MINIMUM_BORROWED_AMOUNT = 1.0;

  public MortgageInputValidationInfo validate(
      @NonNull String borrowedAmount,
      @NonNull String annualInterestRate,
      @NonNull String numberOfYears) {

    var errors = generateErrors(borrowedAmount, annualInterestRate, numberOfYears);

    if (errors.isEmpty()) {
      return new MortgageInputValidationInfo(true, errors);
    }

    return new MortgageInputValidationInfo(false, errors);
  }

  private List<String> generateErrors(@NonNull String... input) {
    var errors = new ArrayList<String>();
    var borrowedAmount = input[0];
    var annualInterestRate = input[1];
    var numberOfYears = input[2];

    if (!isPositiveDecimalNumbers(borrowedAmount, annualInterestRate, numberOfYears)) {
      errors.add("Only positive decimal numbers are allowed.");
    }

    if (!greaterThanMinimumBorrowedAmount(borrowedAmount)) {
      errors.add("Minimum borrowed amount must be greater than or equal to 1.");
    }

    return errors;
  }

  private static boolean isPositiveDecimalNumbers(@NonNull String... numbers) {
    return Arrays.stream(numbers).allMatch(MortgageInputValidator::isPositiveDecimalNumber);
  }

  private static boolean isPositiveDecimalNumber(@NonNull String number) {
    return number.matches("^((\\d+\\.\\d+)|(\\.\\d+))$");
  }

  private static boolean greaterThanMinimumBorrowedAmount(@NonNull String borrowedAmount) {
    return Integer.parseInt(borrowedAmount) >= MINIMUM_BORROWED_AMOUNT;
  }
}
