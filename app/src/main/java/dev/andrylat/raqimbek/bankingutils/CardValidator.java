package dev.andrylat.raqimbek.bankingutils;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class CardValidator {
    public CardValidationInfo checkCardNumber(String input) {
        var errors = new ArrayList<String>();
        var isValid = false;
        
        if (!containsOnlyDigits(input)) {
            errors.add("Card Number must contain only digits");
        }
        
        for (PaymentSystem paymentSystem : PaymentSystem.values()) {
            if (!hasValidPrefix(input, paymentSystem)) {
                errors.add("Payment System can't be determined");
            }

            if (!hasValidLength(input, paymentSystem)) {
                if (paymentSystem.getCardMinLength() > 0) {
                    errors.add(new StringBuilder("Length should be between ")
                                .append(paymentSystem.getCardMinLength())
                                .append(" and ")
                                .append(paymentSystem.getCardMaxLength())
                                .append(" symbols")
                                .toString());
                } else {
                    errors.add(new StringBuilder("Length should be ")
                                .append(paymentSystem.getCardMaxLength())
                                .append(" symbols")
                                .toString());
                }
            }
        }
        
        if (!passesLuhnTest(parseCardNumber(input))) {
            errors.add("Card Number does not pass the Luhn Test");
        }

        if (errors.size() == 0) {
            isValid = true;
        }

        return new CardValidationInfo(isValid, errors);
    }
    
    private boolean hasValidPrefix(String cardNumber, PaymentSystem paymentSystem) {
        var prefixes = paymentSystem.getPrefixes();

            return prefixes.stream()
                .anyMatch(p -> cardNumber.startsWith(p.toString()));
    }
    
    private boolean hasValidLength(String cardNumber, PaymentSystem paymentSystem) {
        var minLength = paymentSystem.getCardMinLength();
        var maxLength = paymentSystem.getCardMaxLength();

        if (cardNumber.length() < minLength
                && cardNumber.length() > maxLength) {
            return false;
        }

        return true;
    }

    private boolean passesLuhnTest(List<Integer> cardNumberAsList) {
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
           return false;
        }

        return true;
    }

    private List<Integer> parseCardNumber(String str) {
        return Arrays.stream(str.split(""))
                .map(Integer::valueOf)
                .toList();
    }
    
    private boolean containsOnlyDigits(String str) {
        return Arrays.stream(str.split(""))
                .map(s -> s.charAt(0))
                .filter(c -> !c.equals(' '))
                .allMatch(Character::isDigit);
    }
}
