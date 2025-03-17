package dev.andrylat.raqimbek.bankingutils;

import java.util.Scanner;
import java.util.List;
import java.io.PrintStream;

public class UserInteraction {
    private PrintStream out;
    private Scanner scanner;

    public UserInteraction(Scanner scanner, PrintStream out) {
        this.out = out;
        this.scanner = scanner;
    }

    public void write(boolean isErrorMessage, List<String> messages) {
        if (isErrorMessage) {
            out.println("Errors:");
            messages.stream()
                .forEachOrdered(m ->
                    out.println(new StringBuilder("->")
                            .append(m)
                            .toString()
                            ));
        } else {
            messages.stream()
                .forEach(out::println);
        }
    }

    public String read() {
        return scanner.nextLine();
    }
}
