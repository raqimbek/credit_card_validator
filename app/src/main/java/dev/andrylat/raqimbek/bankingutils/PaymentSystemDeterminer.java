package dev.andrylat.raqimbek.bankingutils;

import java.util.Arrays;
import java.util.Optional;

public class PaymentSystemDeterminer {
    Optional<PaymentSystem> determinePaymentSystemByCardNumber(String cardNumber) {
        return Arrays.stream(PaymentSystem.values())
                .filter(p -> p.getPrefixes().stream().anyMatch(prefix -> cardNumber.startsWith(prefix.toString())))
                .findFirst();
    }
}
