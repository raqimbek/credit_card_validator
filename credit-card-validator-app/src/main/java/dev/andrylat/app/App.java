package dev.andrylat.app;

import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class App {
    private static StringBuilder errors = new StringBuilder();

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        System.out.println("Hello. Enter card number for validation:");
        var cardNum = new ArrayList<>(Arrays.stream(scanner.nextLine().split(""))
                         .filter(App::isDigit)
                         .map(Integer::valueOf)
                         .toList());

        if (cardNum.size() < 16) {
          errors.append("-> Length should be 16 symbols\n");
        }

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

        if (sum % 2 != 0) {
            errors.append("-> Payment System can't be determined\n");
        }

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
