package dev.andrylat.raqimbek.bankingutils;

import lombok.NonNull;

import java.util.Map;
import java.util.Scanner;

public class BankingUtilsApp {
  private static final CommandLineUserInteraction commandLineUserInteraction =
      new CommandLineUserInteraction(new Scanner(System.in), System.out);
  private static final BankingService<PaymentSystem, String> paymentSystemDeterminer =
      new PaymentSystemDeterminer();
  private static final BankingService<Long, Double> mortgageCalculator = new MortgageCalculator();
  private static final Map<Integer, BankingServiceinfo> bankingServiceMap =
      Map.of(
          0,
          new BankingServiceinfo(
              paymentSystemDeterminer,
              new DialogValidatorHolder(
                  new CardValidatorDialog(commandLineUserInteraction), new CardValidator())),
          1,
          new BankingServiceinfo(
              mortgageCalculator,
              new DialogValidatorHolder(
                  new MortgageCalculatorDialog(commandLineUserInteraction),
                  new MortgageInputValidator())));

  public static void main(String[] args) {
    run();
  }

  private static void run() {
    handleSelectedBankingServiceInput(selectBankingService());
  }

  private static void handleSelectedBankingServiceInput(
      @NonNull BankingServiceinfo bankingServiceinfo) {
    var dialogValidatorHolder = bankingServiceinfo.dialogValidatorHolder();
    var inputList = dialogValidatorHolder.dialog().prompt();
    var validationInfo = dialogValidatorHolder.validator().validate(inputList);

    if (!validationInfo.isValid()) {
      commandLineUserInteraction.writeAll(validationInfo.errors());
    } else {
      var result = bankingServiceinfo.bankingService().run(inputList);
      commandLineUserInteraction.write(result.toString());
    }
  }

  private static BankingServiceinfo selectBankingService() {

    StringBuilder greetingMessage =
        new StringBuilder("Hello. Please type the index of the service you need:\n");

    bankingServiceMap.entrySet().stream()
        .forEachOrdered(
            e ->
                greetingMessage
                    .append("[")
                    .append(e.getKey())
                    .append("] - ")
                    .append(e.getValue())
                    .append("\n"));

    commandLineUserInteraction.write(greetingMessage.toString());

    var selectedService = -1;

    do {
      try {
        selectedService = Integer.parseInt(commandLineUserInteraction.read());
      } catch (NumberFormatException e) {
        displayOnlyNumberError();
      }
      if (!bankingServiceMap.containsKey(selectedService)) {
        displayOnlyNumberError();
      }
    } while (selectedService < 0);

    return bankingServiceMap.get(selectedService);
  }

  private static void displayOnlyNumberError() {
    commandLineUserInteraction.write(
        "Please write only a number representing an index of a service.");
  }

  //
  //  private static void promptForMortgageInput() {
  //  MortgageInputValidator mortgageInputValidator = new MortgageInputValidator();
  //    var isMortgageInputValid = false;
  //
  //    do {
  //
  //      //     prompt();
  //
  //      var borrowedAmount = commandLineUserInteraction.read();
  //      var annualInterestRate = commandLineUserInteraction.read();
  //      var numberOfYears = commandLineUserInteraction.read();
  //
  //      var mortgageInputValidationInfo =
  //              mortgageInputValidator.validate(
  //                      List.of(borrowedAmount, annualInterestRate, numberOfYears));
  //
  //      isMortgageInputValid = mortgageInputValidationInfo.isValid();
  //
  //      if (!isMortgageInputValid) {
  //        commandLineUserInteraction.write("Input data is incorrect. Errors: ");
  //        commandLineUserInteraction.writeAll(mortgageInputValidationInfo.errors());
  //        //      prompt();
  //      }
  //    } while (isMortgageInputValid);
  //  }
  //
  //  private static void promptForCardValidatorInput() {
  //
  //    // prompt()
  //  CardValidator cardValidator = new CardValidator();
  //    var userInput = commandLineUserInteraction.read();
  //    var cardValidationInfo = cardValidator.validate(List.of(userInput));
  //
  //    if (!cardValidationInfo.isValid()) {
  //      commandLineUserInteraction.write("Card is not valid. Errors:");
  //      commandLineUserInteraction.writeAll(cardValidationInfo.errors());
  //    } else {
  //      var paymentSystem = paymentSystemDeterminer.run(List.of(userInput));
  //      var message =
  //              new StringBuilder("Card is valid. Payment System is \"")
  //                      .append(paymentSystem)
  //                      .append("\"")
  //                      .toString();
  //
  //      commandLineUserInteraction.write(message);
  //    }
  //  }
}
