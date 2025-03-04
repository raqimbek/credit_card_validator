package dev.andrylat.app;

import java.util.function.Supplier;

public class Dispatcher {
    private String input;

    public void prompt(Supplier<String> messageSupplier) {
        System.out.println(messageSupplier.get());
    }

    public void read(Supplier<String> inputSupplier) {
        input = inputSupplier.get();
    }

    public String getInput() {
        return input == null ? "" : input;
    }

    public void print(Supplier<String> messageSupplier) {
    	System.out.println(messageSupplier.get());

        if (errors.length() > 0) {
            System.out.println("Card number is invalid.");
            System.out.println("Errors:");
            System.out.println(errors.toString());
        } else {
            System.out.println(
                new StringBuilder("Card is valid. Payment System is ")
                    .append(brand)
                    .toString()
            );
        }
    }
}
