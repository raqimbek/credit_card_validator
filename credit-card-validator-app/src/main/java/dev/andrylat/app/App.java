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

        System.out.print("card number: ");
        System.out.println(cardNum);

        var everyOtherNumList = new ArrayList<>(IntStream.range(0, cardNum.size())
                .filter(n -> n % 2 == 0)
                .mapToObj(cardNum::get)
                .toList());

        System.out.print("every other number of the card number: ");
        System.out.println(everyOtherNumList);

        var numsWithTwoDigits = everyOtherNumList.stream()
                .map(n -> n*2)
                .filter(n -> n >= 10 && n < 100)
                .map(n -> n/2)
                .toList();


        System.out.print("every other number of the card number with two digits: ");
        System.out.println(numsWithTwoDigits);

        for (var i = 0; i < cardNum.size(); i++) {

            if (everyOtherNumList.contains(cardNum.get(i))) {
                System.out.print("current item: ");
                System.out.println(cardNum.get(i));

                System.out.print("cardNum before: ");
                System.out.println(cardNum);

                cardNum.remove(cardNum.get(i));

                // we don't need to decrement i, because cardNum's original size was greater than everyOtherNumList's size

                System.out.print("cardNum after: ");
                System.out.println(cardNum);
            }
        }

        System.out.println("removed every other number from the card number");
        System.out.print("modified card number: ");
        System.out.println(cardNum);

        System.out.print("everyOtherNumList before loop: ");
        System.out.println(everyOtherNumList);


        for (var i = 0; i < everyOtherNumList.size(); i++) {
            System.out.println("i = " + i);
            System.out.print("everyOtherNumList.get(" + i + "): ");
            System.out.println(everyOtherNumList.get(i));

            if (numsWithTwoDigits.contains(everyOtherNumList.get(i))) {
                System.out.print("current item of everyOtherNumList: ");
                System.out.println(everyOtherNumList.get(i));

                System.out.print("everyOtherNumList before: ");
                System.out.println(everyOtherNumList);

                everyOtherNumList.remove(everyOtherNumList.get(i));

                System.out.print("everyOtherNumList after: ");
                System.out.println(everyOtherNumList);

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

        System.out.print("sum of selected numbers with two digits: ");
        System.out.println(sumOfNumsWithTwoDigits);

        var sumOfEveryOtherNum = everyOtherNumList.stream()
            .map(n->n*2)
            .mapToInt(Integer::intValue)
            .sum();

        System.out.println("sum of every other number in card number: " + sumOfEveryOtherNum);

        var cardNumSum = cardNum.stream().mapToInt(Integer::intValue).sum();

        System.out.print("sum of numbers in card number: ");
        System.out.println(cardNumSum);

        var sum = cardNumSum + sumOfNumsWithTwoDigits + sumOfEveryOtherNum;

        System.out.println("final sum: " + sum);

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
