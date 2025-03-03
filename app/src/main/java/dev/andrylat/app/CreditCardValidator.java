package dev.andrylat.app;

import java.util.List;

public class CreditCardValidator {
    private StringBuilder errors;
    private static final int VALID_CREDIT_CARD_NUMBER_LENGTH = 16;

    public CreditCardValidator() {
        errors = new StringBuilder();
    }

    public void validate(String input) {
        if (input.length() < VALID_CREDIT_CARD_NUMBER_LENGTH) {
            errors.append("-> Length should be ")
                  .append(VALID_CREDIT_CARD_NUMBER_LENGTH)
                  .append(" symbols\n");
        }
    }

    private void validate(List<Integer> cardNumber) {
        if (cardNumber.size() < VALID_CREDIT_CARD_NUMBER_LENGTH || brand.length() == 0) {
            errors.append("-> Payment System can't be determined\n");
        } else {
            var everyOtherNumberList = new ArrayList<>(IntStream.range(0, cardNumber.size())
                    .filter(n -> n % 2 == 0)
                    .mapToObj(cardNumber::get)
                    .toList());

            var numbersWithTwoDigits = everyOtherNumberList.stream()
                    .map(n -> n*2)
                    .filter(n -> n >= 10 && n < 100)
                    .map(n -> n/2)
                    .toList();

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

            var sumOfNumbersWithTwoDigits = numbersWithTwoDigits.stream()
                    .map(n -> n*2)
                    .map(String::valueOf)
                    .map(s -> Arrays.stream(s.split(""))
                        .map(Integer::valueOf)
                        .mapToInt(Integer::intValue)
                        .sum()
                    )
                    .mapToInt(Integer::intValue)
                    .sum();

            var sumOfEveryOtherNumber = everyOtherNumberList.stream()
                .map(n->n*2)
                .mapToInt(Integer::intValue)
                .sum();

            var cardNumberSum = cardNumber.stream().mapToInt(Integer::intValue).sum();

            var sum = cardNumberSum + sumOfNumbersWithTwoDigits + sumOfEveryOtherNumber;

            if (sum % 10 != 0) {
                errors.append("-> Payment System can't be determined\n");
            }
        }
    }

    public String getErrors() {
        return errors.toString();
    }
}
