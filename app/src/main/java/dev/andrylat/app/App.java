package dev.andrylat.app;

/*
import Dispatcher;
import CreditCardBrandDeterminer;
import CreditCardValidator;
*/
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class App {
    private Dispatcher dispatcher;
    private CreditCardBrandDeterminer determiner;
    private CreditCardValidator validator;

    public App() {
        dispatcher = new Dispatcher();
        determiner = new CreditCardBrandDeterminer();
        validator = new CreditCardValidator();
    }

    public static void main(String[] args) {
        new App().run();
    }

    public App run() {
        dispatcher.prompt(() -> "Hello. Enter card number for validation:");
        dispatcher.read(() -> new Scanner(System.in).nextLine());
        determiner.determineCreditCardBrandByNumber(dispatcher.getInput());

        
        validate(cardNumber);

        return this;
    }
}
