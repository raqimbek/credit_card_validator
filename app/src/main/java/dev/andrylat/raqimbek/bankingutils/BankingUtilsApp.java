package dev.andrylat.raqimbek.bankingutils;

public class BankingUtilsApp {
    private static CardValidationUserInteraction userInteraction = new CardValidationUserInteraction();
    private static CardValidator cardValidator = new CardValidator();
    private static PaymentSystemDeterminer paymentSystemDeterminer = new PaymentSystemDeterminer();

    public static void main(String[] args) {
        run();
    }

    private static void run() {
    	userInteraction.write("Hello. Enter card number for validation:", System.out);
    	userInteraction.read();        

    	var cardValidationInfo = cardValidator.checkCardNumber(userInteraction.getInput());

    	if (cardValidationInfo.getErrors().size() > 0) {
    	    userInteraction.write(cardValidationInfo.getErrors().toString(), System.out);
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
