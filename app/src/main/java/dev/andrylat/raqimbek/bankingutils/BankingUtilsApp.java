package dev.andrylat.raqimbek.bankingutils;

import java.util.Scanner;
import java.util.List;

public class BankingUtilsApp {
    private static CardValidationUserInteraction userInteraction = new CardValidationUserInteraction();
    private static CardValidator cardValidator = new CardValidator();
    private static PaymentSystemDeterminer paymentSystemDeterminer = new PaymentSystemDeterminer();

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        var scanner = new Scanner(System.in);

    	userInteraction.write(false, List.of("Hello. Enter card number for validation:"), System.out);

    	var userInput = userInteraction.read(scanner);        
    	var cardValidationInfo = cardValidator.checkCardNumber(userInput);

    	if (!cardValidationInfo.getIsValid()) {
    	    var errors = cardValidationInfo.getErrors();

    	    userInteraction.write(true, errors, System.out);
    	} else {
    	    var paymentSystem = paymentSystemDeterminer
    	            .determinePaymentSystemByCardNumber(userInteraction.getInput())
    	            .get();

    	    if (paymentSystem != null) {
    	        userInteraction.write(false, List.of(
    	                new StringBuilder("Card is valid. Payment System is \"")
    	                    .append(paymentSystem.toString())
    	                    .append("\"")
    	                    .toString()), System.out);
    	    }
    	}
    }
}
