package dev.andrylat.raqimbek.bankingutils;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class CardValidationUserInteraction {
    private String input;

    public void write(String message, Consumer<String> messageConsumer) {
        messageConsumer.accept(message);
    }

    public void read(Supplier<String> inputSupplier) {
        input = inputSupplier.get();
    }

    public String getInput() {
        return input == null ? "" : input;
    }
}
