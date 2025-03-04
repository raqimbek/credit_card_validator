package dev.andrylat.app;

/*
import Dispatcher;
import CreditCardBrandDeterminer;
import CreditCardValidator;
*/
import java.util.Scanner;

public class App {
    private Dispatcher dispatcher;
    private CreditCardBrandDeterminer determiner;

    public App() {
        dispatcher = new Dispatcher();
        determiner = new CreditCardBrandDeterminer();
    }

    public static void main(String[] args) {
        new App().run();
    }

    public App run() {
        dispatcher.prompt(() -> "Hello. Enter card number for validation:");
        dispatcher.read(() -> new Scanner(System.in).nextLine());
        determiner.determineCreditCardBrandByNumber(dispatcher.getInput());

        return this;
    }
}
