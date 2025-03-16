package dev.andrylat.raqimbek.bankingutils;

import java.util.Scanner;
import java.io.PrintStream;

public class CardValidationUserInteraction {
    private String input;
    private Scanner scanner = new Scanner(System.in);

    public void write(String message, PrintStream out) {
        out.println(message);
    }

    public void read() {
        input = scanner.nextLine();
    }

    public String getInput() {
        return input == null ? "" : input;
    }
}
