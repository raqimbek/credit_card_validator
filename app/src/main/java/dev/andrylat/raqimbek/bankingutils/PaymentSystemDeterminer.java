package dev.andrylat.raqimbek.bankingutils;

import lombok.NonNull;

import java.util.Arrays;
import java.util.List;

public class PaymentSystemDeterminer implements BankingService<PaymentSystem, String> {
  @NonNull
  public PaymentSystem run(@NonNull List<String> inputList) {
    var cardNumber = inputList.getFirst();

    return Arrays.stream(PaymentSystem.values())
        .filter(
            p ->
                p.getPrefixes().stream()
                    .anyMatch(prefix -> cardNumber.startsWith(prefix.toString())))
        .findFirst()
        .get();
  }
}
