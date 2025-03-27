package dev.andrylat.raqimbek.bankingutils;

import java.util.Scanner;

public class BankingUtilsApp {
  private static final CommandLineUserInteraction commandLineUserInteraction =
      new CommandLineUserInteraction(new Scanner(System.in), System.out);
  private static final CardValidator cardValidator = new CardValidator();
  private static final PaymentSystemDeterminer paymentSystemDeterminer =
      new PaymentSystemDeterminer();

  public static void main(String[] args) {
    run();
  }

  private static void run() {
    StringBuilder greetingMessage =
        new StringBuilder("Hello. Please type the index of the service you need:\n");
    var services = BankingService.values();

    for (var i = 0; i < services.length; i++) {
      greetingMessage.append("[").append(i).append("] - ").append(services[i]).append("\n");
    }

    commandLineUserInteraction.write(greetingMessage.toString());

    var selectedService = 0;

    try {
      selectedService = Integer.parseInt(commandLineUserInteraction.read());
    } catch (NumberFormatException e) {
      commandLineUserInteraction.write(
          "Please write only an integer number representing an index of a service.");
    }

    switch (services[selectedService]) {
      case BankingService.CARD_VALIDATOR:
        commandLineUserInteraction.write(BankingService.CARD_VALIDATOR.getGreetingMessage());

        var userInput = commandLineUserInteraction.read();
        var cardValidationInfo = cardValidator.checkCardNumber(userInput);

        if (!cardValidationInfo.isValid()) {
          var errors = cardValidationInfo.errors();

          commandLineUserInteraction.write("Card is not valid. Errors:");
          commandLineUserInteraction.writeAll(errors);
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

            commandLineUserInteraction.write(message);
          }
        }
        break;
      case BankingService.MORTGAGE_CALCULATOR:
        commandLineUserInteraction.write(BankingService.MORTGAGE_CALCULATOR.getGreetingMessage());

        var borrowedAmount = commandLineUserInteraction.read();
        var annualInterestRate = commandLineUserInteraction.read();
        var numberOfYears = commandLineUserInteraction.read();

        //         mortgageInputValidator.validate(borrowedAmount, annualInterestRate,
        // numberOfYears);
        break;
    }
  }
}
