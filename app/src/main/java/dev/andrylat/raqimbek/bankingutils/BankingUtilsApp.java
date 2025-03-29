package dev.andrylat.raqimbek.bankingutils;

import lombok.NonNull;

import java.util.Scanner;

public class BankingUtilsApp {
  private static final CommandLineUserInteraction commandLineUserInteraction =
      new CommandLineUserInteraction(new Scanner(System.in), System.out);
  private static final CardValidator cardValidator = new CardValidator();
  private static final PaymentSystemDeterminer paymentSystemDeterminer =
      new PaymentSystemDeterminer();
  private static final MortgageInputValidator mortgageInputValidator = new MortgageInputValidator();

  public static void main(String[] args) {
    run();
  }

  private static void run() {
    promptForSelectedBankingServiceInput(greet());
  }

  private static void promptForMortgageInput() {

    commandLineUserInteraction.write(BankingService.MORTGAGE_CALCULATOR.getPromptingMessage());

    var isMortgageInputValid = false;

    do {

      var borrowedAmount = commandLineUserInteraction.read();
      var annualInterestRate = commandLineUserInteraction.read();
      var numberOfYears = commandLineUserInteraction.read();

      var mortgageInputValidationInfo =
          mortgageInputValidator.validate(borrowedAmount, annualInterestRate, numberOfYears);

      isMortgageInputValid = mortgageInputValidationInfo.isValid();

      if (!isMortgageInputValid) {
        commandLineUserInteraction.write("Input data is incorrect. Errors: ");
        commandLineUserInteraction.writeAll(mortgageInputValidationInfo.errors());
        commandLineUserInteraction.write(BankingService.MORTGAGE_CALCULATOR.getPromptingMessage());
      }
    } while (isMortgageInputValid);
  }

  private static void promptForCardValidatorInput() {

    commandLineUserInteraction.write(BankingService.CARD_VALIDATOR.getPromptingMessage());

    var userInput = commandLineUserInteraction.read();
    var cardValidationInfo = cardValidator.checkCardNumber(userInput);

    if (!cardValidationInfo.isValid()) {
      commandLineUserInteraction.write("Card is not valid. Errors:");
      commandLineUserInteraction.writeAll(cardValidationInfo.errors());
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
  }

  private static void promptForSelectedBankingServiceInput(
      @NonNull BankingService selectedService) {

    switch (selectedService) {
      case BankingService.CARD_VALIDATOR:
        promptForCardValidatorInput();
        break;
      case BankingService.MORTGAGE_CALCULATOR:
        promptForMortgageInput();
        break;
    }
  }

  private static BankingService greet() {
    StringBuilder greetingMessage =
        new StringBuilder("Hello. Please type the index of the service you need:\n");
    var services = BankingService.values();

    for (var i = 0; i < services.length; i++) {
      greetingMessage.append("[").append(i).append("] - ").append(services[i]).append("\n");
    }

    commandLineUserInteraction.write(greetingMessage.toString());

    var selectedService = -1;

    do {
      try {
        selectedService = Integer.parseInt(commandLineUserInteraction.read());
      } catch (NumberFormatException e) {
        commandLineUserInteraction.write(
            "Please write only a number representing an index of a service.");
      }
    } while (selectedService < 0);

    return services[selectedService];
  }
}
