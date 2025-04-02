package dev.andrylat.raqimbek.bankingutils;

import lombok.NonNull;

import java.util.*;
import java.util.stream.Collectors;

public class BankingUtilsApp {
  private static final CommandLineUserInteraction commandLineUserInteraction =
      new CommandLineUserInteraction(new Scanner(System.in), System.out);

  public static void main(String[] args) {
    run();
  }

  private static void run() {
    var bankingServiceMapOptional = getBankingServiceMap();

    if (bankingServiceMapOptional.isPresent()) {
      var bankingServiceMap = bankingServiceMapOptional.get();

      handleSelectedBankingServiceInput(selectBankingService(bankingServiceMap));
    }
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

  private static BankingServiceinfo selectBankingService(
      @NonNull Map<Integer, BankingServiceinfo> bankingServiceMap) {

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
    var onlyNumberErrorMessage = "Please write only a number representing an index of a service.";

    do {

      try {
        selectedService = Integer.parseInt(commandLineUserInteraction.read());
      } catch (NumberFormatException e) {
        commandLineUserInteraction.write(onlyNumberErrorMessage);
      }
      if (!bankingServiceMap.containsKey(selectedService)) {
        commandLineUserInteraction.write(onlyNumberErrorMessage);
      }
    } while (selectedService < 0);

    return bankingServiceMap.get(selectedService);
  }

  public static boolean isValidBankingServiceIndex(
      @NonNull String input, @NonNull Map<Integer, BankingServiceinfo> bankingServiceMap) {
    // Regex pattern to match a non-negative integer
    var regex = "^(0|[1-9]\\d*)$";
    return input.matches(regex) && bankingServiceMap.containsKey(Integer.parseInt(input));
  }

  @NonNull
  public static Optional<Map<Integer, BankingServiceinfo>> getBankingServiceMap() {
    var bankingServiceMap = new HashMap<Integer, BankingServiceinfo>();
    var bankingServiceInfoList = getBankingServiceInfoList();

    if (populateBankingServiceMap(bankingServiceMap, bankingServiceInfoList)) {
      return Optional.of(bankingServiceMap);
    } else {
      return Optional.empty();
    }
  }

  @NonNull
  public static List<BankingServiceinfo> getBankingServiceInfoList() {
    return List.of(
        new BankingServiceinfo(
            new MortgageCalculator(),
            new DialogValidatorHolder(
                new MortgageCalculatorDialog(commandLineUserInteraction),
                new MortgageInputValidator())),
        new BankingServiceinfo(
            new PaymentSystemDeterminer(),
            new DialogValidatorHolder(
                new CardValidatorDialog(commandLineUserInteraction), new CardValidator())));
  }

  public static boolean populateBankingServiceMap(
      @NonNull Map<Integer, @NonNull BankingServiceinfo> bankingServiceMap,
      @NonNull List<BankingServiceinfo> bankingServiceList) {
    for (var i = 0; i < bankingServiceList.size(); i++) {
      if (!putDataInBankingServiceMap(bankingServiceMap, i, bankingServiceList.get(i)))
        return false;
    }
    return true;
  }

  public static boolean putDataInBankingServiceMap(
      @NonNull Map<Integer, BankingServiceinfo> bankingServiceMap,
      int bankingServiceIndex,
      @NonNull BankingServiceinfo bankingServiceInfo) {
    try {
      bankingServiceMap.put(bankingServiceIndex, bankingServiceInfo);
      return true;
    } catch (IllegalArgumentException | NullPointerException | ClassCastException e) {
      commandLineUserInteraction.write(e.getMessage());
      return false;
    }
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
