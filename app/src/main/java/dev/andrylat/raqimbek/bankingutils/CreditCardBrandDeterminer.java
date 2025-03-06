package dev.andrylat.raqimbek.bankingutils;

import java.util.List;
import java.util.stream.IntStream;

public class CreditCardBrandDeterminer {
    String determineCreditCardBrandByNumber(List<Integer> cardNumber) {
        /*
           for later improvements:

           I could create an enum with all card brands,
           and each card brand would have a method to validate
           if the given number equals to the brand's possible number
           the return type of the method would be boolean

        */
    	
    	var brand = "";

        switch (cardNumber.get(0)) {
            case 4:
                brand = "VISA";
                break;
            case 5:
                brand = IntStream.range(0,6)
                            .anyMatch(n -> n == cardNumber.get(1))
                            ? "MASTERCARD" : "";
                break;
        }
        
        return brand;
    }
}
