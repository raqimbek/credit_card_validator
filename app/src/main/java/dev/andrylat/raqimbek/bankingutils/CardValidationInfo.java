package dev.andrylat.raqimbek.bankingutils;

import java.util.List;

public class CardValidationInfo {
  private final boolean isValid;
  private final List<String> errors;

  public CardValidationInfo(boolean isValid, List<String> errors) {
    this.isValid = isValid;
    this.errors = errors;
  }

  public boolean getIsValid() {
    return isValid;
  }

  public List<String> getErrors() {
    return errors;
  }
}
