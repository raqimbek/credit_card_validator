package dev.andrylat.raqimbek.bankingutils;

import java.util.Scanner;
import java.util.List;
import java.io.PrintStream;

public class CardValidationUserInteraction {
    private String input;

    public void write(List<String> messages, PrintStream out) {
        messages.stream()
            .forEachOrdered(out::println);
    }

    public String read(Scanner scanner) {
        return scanner.nextLine();
    }

    public String getInput() {
        return input == null ? "" : input;
    }
}
