package dev.andrylat.raqimbek.bankingutils;

import java.util.Scanner;

public class BankingUtilsApp {
    private static CardValidationUserInteraction userInteraction = new CardValidationUserInteraction();
    private static CardValidator cardValidator = new CardValidator();
    private static PaymentSystemDeterminer paymentSystemDeterminer = new PaymentSystemDeterminer();

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        var scanner = new Scanner(System.in);

    	userInteraction.write("Hello. Enter card number for validation:", System.out);

    	var userInput = userInteraction.read(scanner);        
    	var cardValidationInfo = cardValidator.checkCardNumber(userInput);

    	if (!cardValidationInfo.getIsValid()) {
    	    var errors = cardValidationInfo.getErrors().toString();

    	    userInteraction.write(errors, System.out);
    	} else {
    	    var paymentSystem = paymentSystemDeterminer
    	            .determinePaymentSystemByCardNumber(userInteraction.getInput())
    	            .get();

    	    if (paymentSystem != null) {
    	        userInteraction.write(paymentSystem.toString(), System.out);
    	    }
    	}
    }
}
