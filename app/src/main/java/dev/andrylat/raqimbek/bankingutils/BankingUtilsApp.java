package dev.andrylat.raqimbek.bankingutils;

import lombok.NonNull;

import java.util.*;

public class BankingUtilsApp {
  private static final CommandLineUserInteraction commandLineUserInteraction =
      new CommandLineUserInteraction(new Scanner(System.in), System.out);

  public static void main(String[] args) {
    run();
  }

  private static void run() {
    var dialogMapOptional = getDialogMap();

    if (dialogMapOptional.isPresent()) {
      var dialogMap = dialogMapOptional.get();

      handleSelectedBankingServiceInput(selectBankingService(dialogMap));
    }
  }

  private static void handleSelectedBankingServiceInput(@NonNull Dialog dialog) {
    dialog.run();
  }

  @NonNull
  private static String getBankingServiceSelectionPromptMessage(
      @NonNull Map<Integer, Dialog> dialogMap) {
    StringBuilder greetingMessage =
        new StringBuilder("Hello. Please type the index of the service you need:\n");

    dialogMap.entrySet().stream()
        .sorted(Map.Entry.comparingByKey())
        .forEach(
            e ->
                greetingMessage
                    .append("[")
                    .append(e.getKey())
                    .append("] - ")
                    .append(e.getValue())
                    .append("\n"));

    return greetingMessage.toString();
  }

  private static Dialog selectBankingService(@NonNull Map<Integer, Dialog> dialogMap) {

    var promptMessage = getBankingServiceSelectionPromptMessage(dialogMap);

    commandLineUserInteraction.write(promptMessage);

    var selectedBankingService = -1;

    do {
      var input = commandLineUserInteraction.read();

      if (!isValidBankingServiceIndex(input, dialogMap)) {
        commandLineUserInteraction.write(
            "Please write only a number representing an index of a service.");
      }

      selectedBankingService = Integer.parseInt(input);

    } while (selectedBankingService < 0);

    return dialogMap.get(selectedBankingService);
  }

  private static boolean isValidBankingServiceIndex(
      @NonNull String input, @NonNull Map<Integer, Dialog> dialogMap) {
    // Regex pattern to match a non-negative integer
    var regex = "^(0|[1-9]\\d*)$";
    return input.matches(regex) && dialogMap.containsKey(Integer.parseInt(input));
  }

  @NonNull
  private static Optional<Map<Integer, Dialog>> getDialogMap() {
    var dialogMap = new HashMap<Integer, Dialog>();
    var dialogList = getDialogList();

    if (populateDialogMap(dialogMap, dialogList)) {
      return Optional.of(dialogMap);
    } else {
      return Optional.empty();
    }
  }

  @NonNull
  private static List<Dialog> getDialogList() {
    return List.of(
        new MortgageCalculatorDialog(commandLineUserInteraction),
        new CardValidatorDialog(commandLineUserInteraction));
  }

  private static boolean populateDialogMap(
      @NonNull Map<Integer, @NonNull Dialog> dialogMap, @NonNull List<Dialog> dialogList) {
    for (var i = 0; i < dialogList.size(); i++) {
      if (!putDataInDialogMap(dialogMap, i, dialogList.get(i))) return false;
    }
    return true;
  }

  private static boolean putDataInDialogMap(
      @NonNull Map<Integer, Dialog> dialogMapMap, int bankingServiceIndex, @NonNull Dialog dialog) {
    try {
      dialogMapMap.put(bankingServiceIndex, dialog);
      return true;
    } catch (IllegalArgumentException | NullPointerException | ClassCastException e) {
      commandLineUserInteraction.write(e.getMessage());
      return false;
    }
  }
}
