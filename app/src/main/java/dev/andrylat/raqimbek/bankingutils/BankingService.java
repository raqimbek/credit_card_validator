package dev.andrylat.raqimbek.bankingutils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BankingService {
  CARD_VALIDATOR("Hello. Enter card number for validation:"),
  MORTGAGE_CALCULATOR(
      "Please enter information regarding your mortgage in the following order and separate lines:\n- amount of money you borrowed \n - annual interest rate\n - number of years you have to pay\n");

  private final String greetingMessage;
}
