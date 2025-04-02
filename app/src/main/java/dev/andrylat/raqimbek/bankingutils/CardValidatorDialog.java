package dev.andrylat.raqimbek.bankingutils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CardValidatorDialog implements Dialog {
  @NonNull private final UserInteraction userInteraction;

  public void run() {
    var paymentSystemDeterminer = new PaymentSystemDeterminer();
    var validator = new CardValidator();
    var inputList = promptForCardNumber();
    var validationInfo = validator.validate(inputList);
    if (validationInfo.isValid()) {
      var paymentSystem = paymentSystemDeterminer.determinePaymentSystem(inputList);
      var message =
          new StringBuilder("Card number is valid. Payment System: ")
              .append(paymentSystem)
              .toString();
      userInteraction.write(message);
    } else {
      userInteraction.write("Card number is not valid. Errors:");
      userInteraction.writeAll(validationInfo.errors());
    }
  }

  private @NonNull List<String> promptForCardNumber() {
    @NonNull String promptMessage = "Enter card number for validation:";
    userInteraction.write(promptMessage);
    return List.of(userInteraction.read());
  }
}
