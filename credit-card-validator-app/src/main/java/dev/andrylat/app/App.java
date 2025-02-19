package dev.andrylat.app;

import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class App {
    private StringBuilder errors;
    private Scanner scanner;

    public App() {
        System.out.println("Hello. Enter card number for validation:");
        errors = new StringBuilder();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        new App().process().print();
    }

    private App process() {
        var input = scanner.nextLine();
        validate(input);

        var cardNum = getCardNum(input);
        validate(cardNum);

        return this;
    }

    private ArrayList<Integer> getCardNum(String s) {
        return new ArrayList<>(Arrays.stream(s.split(""))
                         .filter(this::isDigit)
                         .map(Integer::valueOf)
                         .toList());
    }

    private void validate(String input) {
        if (input.length() < 16) {
            errors.append("-> Length should be 16 symbols\n");
        }
    }

    private void validate(ArrayList<Integer> cardNum) {
        if (cardNum.size() < 16) {
            errors.append("-> Payment System can't be determined\n");
        } else {
            var everyOtherNumList = new ArrayList<>(IntStream.range(0, cardNum.size())
                    .filter(n -> n % 2 == 0)
                    .mapToObj(cardNum::get)
                    .toList());

            var numsWithTwoDigits = everyOtherNumList.stream()
                    .map(n -> n*2)
                    .filter(n -> n >= 10 && n < 100)
                    .map(n -> n/2)
                    .toList();

            for (var i = 0; i < cardNum.size(); i++) {
                if (everyOtherNumList.contains(cardNum.get(i))) {
                    cardNum.remove(cardNum.get(i));
                }
            }

            for (var i = 0; i < everyOtherNumList.size(); i++) {
                if (numsWithTwoDigits.contains(everyOtherNumList.get(i))) {
                    everyOtherNumList.remove(everyOtherNumList.get(i));
                    i--;
                }
            }

            var sumOfNumsWithTwoDigits = numsWithTwoDigits.stream()
                    .map(n -> n*2)
                    .map(String::valueOf)
                    .map(s -> Arrays.stream(s.split(""))
                        .map(Integer::valueOf)
                        .mapToInt(Integer::intValue)
                        .sum()
                    )
                    .mapToInt(Integer::intValue)
                    .sum();

            var sumOfEveryOtherNum = everyOtherNumList.stream()
                .map(n->n*2)
                .mapToInt(Integer::intValue)
                .sum();

            var cardNumSum = cardNum.stream().mapToInt(Integer::intValue).sum();

            var sum = cardNumSum + sumOfNumsWithTwoDigits + sumOfEveryOtherNum;

            if (sum % 10 != 0) {
                errors.append("-> Payment System can't be determined\n");
            }
        }
    }

    private void print() {
        if (errors.length() > 0) {
            System.out.println("Card number is invalid.");
            System.out.println("Errors:");
            System.out.println(errors.toString());
        } else {
            System.out.println("Card is valid.");
        }
    }

    private boolean isDigit(String s) {
        if (s.equals(" ")) return false;

        try {
            Integer.parseInt(s); 
        } catch (NumberFormatException e) {
            var msg = "-> Number should contain only digits\n";

            if (!errors.toString().contains(msg)) {
                errors.append(msg);
            }

            return false;
        }

        return true;
    }
}
