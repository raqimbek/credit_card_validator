package dev.andrylat.app;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class CreditCardHandler {
	private static final int VALID_CREDIT_CARD_NUMBER_LENGTH = 16;
    private StringBuilder errors = new StringBuilder();
    private StringBuilder message = new StringBuilder();
    private StringBuilder brand = new StringBuilder();
    private CreditCardBrandDeterminer brandDeterminer = new CreditCardBrandDeterminer();
    
    public String checkCreditCardNumber(String input) {
    	validateCreditCardNumber(input);
    	
    	if (errors.length() > 0) {
    		message.append("Card number is invalid.\n")
                   .append("Errors:\n")
    		       .append(errors.toString());
    	} else {
    		message.append("Card is valid. Payment System is ")
    		       .append(brand);
    	}
    	
    	return message.toString();
    }

    private void validateCreditCardNumber(String input) {
        if (input.length() < VALID_CREDIT_CARD_NUMBER_LENGTH) {
            errors.append("-> Length should be ")
                  .append(VALID_CREDIT_CARD_NUMBER_LENGTH)
                  .append(" symbols\n");
        }
        
        var cardNumber = getCardNumber(input);
        brand.append(brandDeterminer.determineCreditCardBrandByNumber(cardNumber));

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

    private List<Integer> getCardNumber(String s) {
        return new ArrayList<>(Arrays.stream(s.split(""))
                         .filter(c -> !c.equals(" "))
                         .filter(this::isDigit)
                         .map(Integer::valueOf)
                         .toList());
    }
    
    private boolean isDigit(String s) {
        try {
            Integer.parseInt(s); 
        } catch (NumberFormatException e) {
            var msg = "-> Number should contain only digits\n";

            if (!errors.toString().contains(msg)) {
                errors.append(msg);
            }

            return false;
        }

        return true;
    }
}
