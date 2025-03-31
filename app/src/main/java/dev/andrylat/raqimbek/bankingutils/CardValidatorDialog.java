package dev.andrylat.raqimbek.bankingutils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CardValidatorDialog implements Dialog {
  @NonNull private final UserInteraction userInteraction;

  public @NonNull List<String> prompt() {
    @NonNull String promptMessage = "Enter card number for validation:";
    userInteraction.write(promptMessage);
    return List.of(userInteraction.read());
  }
}
