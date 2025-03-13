package dev.andrylat.raqimbek.bankingutils;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class CardValidator {
    private List<String> errors = new ArrayList<>();
    private List<Integer> cardNumberAsList = new ArrayList<>();

    public CardValidationInfo checkCardNumber(String input) {
        
        var isValid = false;

        determineCardNumberAsList(input)
        .conformsWithPaymentSystem()
        .checkControlSum();

        if (errors.size() == 0) {
            isValid = true;
        }

        return new CardValidationInfo(isValid, errors);
    }

    private CardValidator conformsWithPaymentSystem() {
        var paymentSystemArray = PaymentSystem.values();
        PaymentSystem matchedPaymentSystem;

        for (PaymentSystem paymentSystem : paymentSystemArray) {
            var prefixMatchCounter = 0;
            var prefixes = paymentSystem.getPrefixesAsLists();

            for (var i = 0; i < prefixes.size(); i++) {
                var prefixPartMatchCounter = 0;

                for (var j = 0; j < prefixes.get(i).size(); j++) {
                    if (cardNumberAsList.get(j) == prefixes.get(i).get(j)) {
                        prefixPartMatchCounter++;
                    }
                }

                if (prefixPartMatchCounter == prefixes.get(i).size()) {
                    prefixMatchCounter++;
                }
            }

            if (prefixMatchCounter == 1) {
                matchedPaymentSystem = paymentSystem;
                var minLength = matchedPaymentSystem.getCardMinLength();
                var maxLength = matchedPaymentSystem.getCardMaxLength();

                if (cardNumberAsList.size() < minLength) {
                    this.errors.add(
                            new StringBuilder("Length should be at least ")
                                .append(minLength)
                                .append(" symbols")
                                .toString());
                }

                if (cardNumberAsList.size() > maxLength) {
                    this.errors.add(
                            new StringBuilder("Length should be at most ")
                                .append(maxLength)
                                .append(" symbols")
                                .toString());
                }
            } else {
                this.errors.add("Payment System can't be determined");
            }
        }
        
        return this;
    }

    private CardValidator checkControlSum() {
        var everyOtherNumberList = new ArrayList<>(
                IntStream.range(0, cardNumberAsList.size())
                    .filter(n -> n % 2 == 0)
                    .mapToObj(cardNumberAsList::get)
                    .toList());

        var numbersWithTwoDigits = everyOtherNumberList.stream()
                .map(n -> n * 2)
                .filter(n -> n >= 10 && n < 100)
                .map(n -> n / 2)
                .toList();

        for (var i = 0; i < cardNumberAsList.size(); i++) {
            if (everyOtherNumberList.contains(cardNumberAsList.get(i))) {
                cardNumberAsList.remove(cardNumberAsList.get(i));
            }
        }

        for (var i = 0; i < everyOtherNumberList.size(); i++) {
            if (numbersWithTwoDigits.contains(everyOtherNumberList.get(i))) {
                everyOtherNumberList.remove(everyOtherNumberList.get(i));
                i--;
            }
        }

        var sumOfNumbersWithTwoDigits = numbersWithTwoDigits.stream().map(n -> n * 2).map(String::valueOf)
                .map(s -> Arrays.stream(s.split("")).map(Integer::valueOf).mapToInt(Integer::intValue).sum())
                .mapToInt(Integer::intValue).sum();

        var sumOfEveryOtherNumber = everyOtherNumberList.stream().map(n -> n * 2).mapToInt(Integer::intValue).sum();

        var cardNumberSum = cardNumberAsList.stream().mapToInt(Integer::intValue).sum();

        var sum = cardNumberSum + sumOfNumbersWithTwoDigits + sumOfEveryOtherNumber;

        if (sum % 10 != 0) {
            errors.add("Card Number is not valid");
        }

        return this;
    }

    private CardValidator determineCardNumberAsList(String s) {
        cardNumberAsList.addAll(Arrays.stream(s.split("")).filter(c -> !c.equals(" ")).filter(this::isDigit)
                .map(Integer::valueOf).toList());
        return this;
    }

    private boolean isDigit(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            var msg = "-> Number should contain only digits\n";

            if (!errors.toString().contains(msg)) {
                errors.add(msg);
            }

            return false;
        }

        return true;
    }
}
