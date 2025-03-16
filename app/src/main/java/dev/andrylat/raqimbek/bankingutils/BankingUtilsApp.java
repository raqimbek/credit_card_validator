package dev.andrylat.raqimbek.bankingutils;

public class BankingUtilsApp {
    private static CardValidationUserInteraction userInteraction = new CardValidationUserInteraction();
    private static CardValidator cardValidator = new CardValidator();

    public static void main(String[] args) {
        run();
    }

    public static void run() {
    	userInteraction.write("Hello. Enter card number for validation:", System.out);
    	userInteraction.read();        
    	userInteraction.write(cardValidator
    	        .checkCardNumber(userInteraction.getInput())
    	        .errors.toString(), System.out);
    }
}
