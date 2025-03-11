package dev.andrylat.raqimbek.bankingutils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class BankingUtilsAppTest {
	
	CardValidator creditCardHandler = new CardValidator();

    @Test
    public void shouldReturnStringContainingTwoErrors() {
        var expected = """
Card number is invalid.
Errors:
-> Length should be 16 symbols
-> Payment System can't be determined
""";  

        assertEquals(creditCardHandler.checkCardNumber("1234").getErrors().toString().trim(), expected.trim());
    }

    @Test
    public void shouldReturnStringContainingThreeErrors() {
        var expected = """
Card number is invalid.
Errors:
-> Length should be 16 symbols
-> Number should contain only digits
-> Payment System can't be determined
""";

        assertEquals(creditCardHandler.checkCardNumber("12o1").getErrors().toString().trim(), expected.trim());
    }
    
    @Test
    public void shouldReturnStringContainingOneError() {
        var expected = """
Card number is invalid.
Errors:
-> Payment System can't be determined
""";

        assertEquals(creditCardHandler.checkCardNumber("5425 2334 3010 9923").getErrors().toString().trim(), expected.trim());
    }
    
    @Test
    public void shouldReturnStringContainingCreditCardBrandName() {
        var expected = "Card is valid. Payment System is MASTERCARD";

        assertEquals(creditCardHandler.checkCardNumber("5425 2334 3010 9903").getErrors().toString().trim(), expected.trim());
    }
}
