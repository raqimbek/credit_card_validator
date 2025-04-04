package dev.andrylat.raqimbek.bankingutils.services.paymentsystemdeterminer;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PaymentSystemDeterminer {
  public Optional<PaymentSystem> determinePaymentSystem(List<String> inputList) {
    var cardNumber = inputList.getFirst();

    return Arrays.stream(PaymentSystem.values())
        .filter(
            p ->
                p.getPrefixes().stream()
                    .anyMatch(prefix -> cardNumber.startsWith(prefix.toString())))
        .findFirst();
  }
}
