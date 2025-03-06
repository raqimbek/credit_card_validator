package dev.andrylat.raqimbek.bankingutils;

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
    }
}
