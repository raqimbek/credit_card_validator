package dev.andrylat.raqimbek.bankingutils;

import java.util.Scanner;

public class App {
    private Dispatcher dispatcher = new Dispatcher();
    private CreditCardHandler creditCardHandler = new CreditCardHandler();

    public static void main(String[] args) {
        new App().run();
    }

    public App run() {
    	var scanner = new Scanner(System.in);
    	
        dispatcher.prompt(() -> "Hello. Enter card number for validation:");
        dispatcher.read(() -> scanner.nextLine());        
        dispatcher.print(() -> creditCardHandler.checkCardNumber(dispatcher.getInput()));
        
        scanner.close();
        
        return this;
    }
}
