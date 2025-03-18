package dev.andrylat.raqimbek.bankingutils;

import java.util.Scanner;
import java.util.List;

public class BankingUtilsApp {
  private static final UserInteraction userInteraction =
      new UserInteraction(new Scanner(System.in), System.out);
  private static final CardValidator cardValidator = new CardValidator();
  private static final PaymentSystemDeterminer paymentSystemDeterminer =
      new PaymentSystemDeterminer();

  public static void main(String[] args) {
    run();
  }

  private static void run() {
    userInteraction.write(false, List.of("Hello. Enter card number for validation:"));

    var userInput = userInteraction.read();
    var cardValidationInfo = cardValidator.checkCardNumber(userInput);

    if (!cardValidationInfo.getIsValid()) {
      var errors = cardValidationInfo.getErrors();

      userInteraction.write(true, errors);
    } else {
      var paymentSystemOptional =
          paymentSystemDeterminer.determinePaymentSystemByCardNumber(userInput);

      if (paymentSystemOptional.isPresent()) {
        var paymentSystem = paymentSystemOptional.get();
        var message =
            new StringBuilder("Card is valid. Payment System is \"")
                .append(paymentSystem)
                .append("\"")
                .toString();

        userInteraction.write(false, List.of(message));
      }
    }
  }
}
