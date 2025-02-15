package dev.andrylat.app;

import java.util.Scanner;
import java.util.Arrays;

public class App {
    private static StringBuilder errors = new StringBuilder();

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        System.out.println("Hello. Enter card number for validation:");
        var input = Arrays.stream(scanner.nextLine().split(""))
                         .filter(App::isDigit)
                         .map(Integer::valueOf)
                         .toList();

        if (errors.length() > 0) {
            System.out.println("Card number is invalid.");
            System.out.println("Errors:");
            System.out.println(errors.toString());
        } else {
            System.out.println("Card is valid.");
        }
    }

    private static boolean isDigit(String s) {
        if (s.equals(" ")) return false;

        try {
            Integer.parseInt(s); 
        } catch (NumberFormatException e) {
            errors.append("-> Number should contain only digits\n");
            return false;
        }

        return true;
    }
}
