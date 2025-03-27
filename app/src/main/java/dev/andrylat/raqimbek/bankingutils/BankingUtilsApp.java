package dev.andrylat.raqimbek.bankingutils;

import java.util.Scanner;
import java.util.List;

public class BankingUtilsApp {
  private static final CommandLineUserInteraction COMMAND_LINE_USER_INTERACTION =
      new CommandLineUserInteraction(new Scanner(System.in), System.out);
  private static final CardValidator CARD_VALIDATOR = new CardValidator();
  private static final PaymentSystemDeterminer PAYMENT_SYSTEM_DETERMINER =
      new PaymentSystemDeterminer();

  public static void main(String[] args) {
    run();
  }

  private static void run() {
    COMMAND_LINE_USER_INTERACTION.write(false, List.of("Hello. Enter card number for validation:"));

    var userInput = COMMAND_LINE_USER_INTERACTION.read();
    var cardValidationInfo = CARD_VALIDATOR.checkCardNumber(userInput);

    if (!cardValidationInfo.isValid()) {
      var errors = cardValidationInfo.errors();

      COMMAND_LINE_USER_INTERACTION.write(true, errors);
    } else {
      var paymentSystemOptional =
          PAYMENT_SYSTEM_DETERMINER.determinePaymentSystemByCardNumber(userInput);

      if (paymentSystemOptional.isPresent()) {
        var paymentSystem = paymentSystemOptional.get();
        var message =
            new StringBuilder("Card is valid. Payment System is \"")
                .append(paymentSystem)
                .append("\"")
                .toString();

        COMMAND_LINE_USER_INTERACTION.write(false, List.of(message));
      }
    }
  }
}
