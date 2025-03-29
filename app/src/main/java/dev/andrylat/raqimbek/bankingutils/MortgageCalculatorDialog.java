package dev.andrylat.raqimbek.bankingutils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public class MortgageCalculatorDialog implements Dialog {
  @NonNull private final UserInteraction userInteraction;

  public @NonNull String prompt() {
    return "Please enter information regarding your mortgage in the following order and separate lines:\n- amount of money you borrowed \n - annual interest rate\n - number of years you have to pay\n";
  }

  public void display(@NonNull String message) {
    userInteraction.write(message);
  }

  public void displayAll(@NonNull String... messages) {
    Arrays.stream(messages).forEach(this::display);
  }
}
