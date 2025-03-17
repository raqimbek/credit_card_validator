package dev.andrylat.raqimbek.bankingutils;

import java.util.Scanner;
import java.io.PrintStream;

public class CardValidationUserInteraction {
    private String input;

    public void write(String message, PrintStream out) {
        out.println(message);
    }

    public String read(Scanner scanner) {
        return scanner.nextLine();
    }

    public String getInput() {
        return input == null ? "" : input;
    }
}
