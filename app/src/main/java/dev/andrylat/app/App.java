package dev.andrylat.app;

/*
import Dispatcher;
import CreditCardBrandDeterminer;
import CreditCardValidator;
*/
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
        dispatcher.print(() -> creditCardHandler.check(dispatcher.getInput()));
        
        scanner.close();
        
        return this;
    }
}
