package dev.andrylat.raqimbek.bankingutils;

import java.util.Scanner;
import java.util.List;
import java.io.PrintStream;

public class CardValidationUserInteraction {
    public void write(boolean isErrorMessage, List<String> messages, PrintStream out) {
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

    public String read(Scanner scanner) {
        return scanner.nextLine();
    }
}
