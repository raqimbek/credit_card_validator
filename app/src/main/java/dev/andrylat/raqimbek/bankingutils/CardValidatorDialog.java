package dev.andrylat.raqimbek.bankingutils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public class CardValidatorDialog implements Dialog {
  @NonNull private final UserInteraction userInteraction;

  @NonNull
  public String prompt() {
    return "Enter card number for validation:";
  }

  public void display(@NonNull String message) {
    userInteraction.write(message);
  }

  public void displayAll(@NonNull String... messages) {
    Arrays.stream(messages).forEach(this::display);
  }
}
