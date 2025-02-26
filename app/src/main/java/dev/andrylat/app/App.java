package dev.andrylat.app;

import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class App {
    private StringBuilder errors;
    private Scanner scanner;
    private String brand = "";
    private static final int validCreditCardNumberLength = 16;

    public App() {
        System.out.println("Hello. Enter card number for validation:");
        errors = new StringBuilder();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        new App().process().print();
    }

    public App process() {
        var input = scanner.nextLine();
        
        validate(input);

        var cardNum = getCardNum(input);
        determineBrandByNumber(cardNum);
        validate(cardNum);

        return this;
    }

    private void determineBrandByNumber(ArrayList<Integer> cardNum) {
        /*
           for later improvements:

           I could create an enum with all card brands,
           and each card brand would have a method to validate
           if the given number equals to the brand's possible number
           the return type of the method would be boolean

        */

        switch (cardNum.get(0)) {
            4: brand = "VISA";
            break;
            5: brand = IntStream.range(0,6).anyMatch(n -> n == cardNum.get(1)) ? "MASTERCARD" : "";
            break;
        }
    }

    private List<Integer> getCardNum(String s) {
        return new ArrayList<>(Arrays.stream(s.split(""))
                         .filter(this::isDigit)
                         .map(Integer::valueOf)
                         .toList());
    }

    private void validate(String input) {
        if (input.length() < validCreditCardNumberLength) {
            errors.append("-> Length should be ")
                  .append(validCreditCardNumberLength)
                  .append(" symbols\n");
        }
    }

    private void validate(List<Integer> cardNum) {
        if (cardNum.size() < validCreditCardNumberLength || brand.length() == 0) {
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

    public void print() {
        if (errors.length() > 0) {
            System.out.println("Card number is invalid.");
            System.out.println("Errors:");
            System.out.println(errors.toString());
        } else {
            System.out.println(
                new StringBuilder("Card is valid. Payment System is ")
                    .append(brand)
                    .toString()
            );
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
