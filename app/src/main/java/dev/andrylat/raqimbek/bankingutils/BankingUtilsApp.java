package dev.andrylat.raqimbek.bankingutils;

import java.util.Scanner;

public class BankingUtilsApp {
    private CardValidationUserInteraction userInteraction = new CardValidationUserInteraction();
    private CardValidator cardValidator = new CardValidator();

    public static void main(String[] args) {
        new BankingUtilsApp().run();
    }

    public BankingUtilsApp run() {
    	var scanner = new Scanner(System.in);
    	
    	userInteraction.write("Hello. Enter card number for validation:", System.out::println);
    	userInteraction.read(() -> scanner.nextLine());        
    	userInteraction.write(cardValidator.checkCardNumber(userInteraction.getInput()), System.out::println);
        
        scanner.close();
        
        return this;
    }
}
