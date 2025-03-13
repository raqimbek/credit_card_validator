package dev.andrylat.raqimbek.bankingutils;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class CardValidator {
    private static final int VALID_CREDIT_CARD_NUMBER_LENGTH = 16;
    private List<String> errors = new ArrayList<>();
    private StringBuilder message = new StringBuilder();
    private StringBuilder brand = new StringBuilder();
    private PaymentSystemDeterminer brandDeterminer = new PaymentSystemDeterminer();

    public CardValidationInfo checkCardNumber(String input) {
        validateCardNumber(input);

        var isValid = false;

        if (errors.size() == 0) {
            isValid = true;
            message.append("Card number is invalid.\n").append("Errors:\n").append(errors.toString());
        } else {
            message.append("Card is valid. Payment System is ").append(brand);
        }

        return new CardValidationInfo(isValid, errors);
    }

    private CardValidator conformsWithPaymentSystem(List<Integer> cardNumber) {
        var paymentSystemArray = PaymentSystem.values();
        var errors = new ArrayList<String>();

        for (PaymentSystem paymentSystem : paymentSystemArray) {
            var prefixMatchCounter = 0;
            var prefixes = paymentSystem.getPrefixesAsLists();
            var maxLength = paymentSystem.getCardMaxLength();
            var minLength = paymentSystem.getCardMinLength();

            if (cardNumber.size() < minLength) {
                errors.add(new StringBuilder("Length should be at least ")
                        .append(minLength)
                        .append(" symbols")
                        .toString());
            }
            
            if (cardNumber.size() > maxLength) {
                errors.add(new StringBuilder("Length should be at most ")
                        .append(maxLength)
                        .append(" symbols")
                        .toString());
            }

            for (var i = 0; i < prefixes.size(); i++) {
                var prefixPartMatchCounter = 0;

                for (var j = 0; j < prefixes.get(i).size(); j++) {
                    if (cardNumber.get(j) == prefixes.get(i).get(j)) {
                        prefixPartMatchCounter++;
                    }
                }

                if (prefixPartMatchCounter == prefixes.get(i).size()) {
                    prefixMatchCounter++;
                }
            }

            if (prefixMatchCounter != 1) {
                errors.add("Payment System prefix is not valid");
            }
        }

        if (errors.size() > 0) {
            this.errors.addAll(errors);
            return this;
        }
        
        return this;
        
    }

    private void validateCardNumber(String input) {
        if (input.length() < VALID_CREDIT_CARD_NUMBER_LENGTH) {
            errors.add(new StringBuilder("-> Length should be ").append(VALID_CREDIT_CARD_NUMBER_LENGTH)
                    .append(" symbols\n").toString());
        }

        var cardNumber = getCardNumber(input);
        brand.append(brandDeterminer.determineCreditCardBrandByNumber(cardNumber));

        if (cardNumber.size() < VALID_CREDIT_CARD_NUMBER_LENGTH || brand.length() == 0) {
            errors.add("-> Payment System can't be determined\n");
        } else {
            var everyOtherNumberList = new ArrayList<>(
                    IntStream.range(0, cardNumber.size()).filter(n -> n % 2 == 0).mapToObj(cardNumber::get).toList());

            var numbersWithTwoDigits = everyOtherNumberList.stream().map(n -> n * 2).filter(n -> n >= 10 && n < 100)
                    .map(n -> n / 2).toList();

            for (var i = 0; i < cardNumber.size(); i++) {
                if (everyOtherNumberList.contains(cardNumber.get(i))) {
                    cardNumber.remove(cardNumber.get(i));
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

            var cardNumberSum = cardNumber.stream().mapToInt(Integer::intValue).sum();

            var sum = cardNumberSum + sumOfNumbersWithTwoDigits + sumOfEveryOtherNumber;

            if (sum % 10 != 0) {
                errors.add("-> Payment System can't be determined\n");
            }
        }
    }

    private List<Integer> getCardNumber(String s) {
        return new ArrayList<>(Arrays.stream(s.split("")).filter(c -> !c.equals(" ")).filter(this::isDigit)
                .map(Integer::valueOf).toList());
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
