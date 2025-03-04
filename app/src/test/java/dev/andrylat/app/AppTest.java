package dev.andrylat.app;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class AppTest {
	
	CreditCardHandler creditCardHandler = new CreditCardHandler();

    @Test
    public void shouldReturnStringContainingTwoErrors() {
        var expected = """
Card number is invalid.
Errors:
-> Length should be 16 symbols
-> Payment System can't be determined
""";  

        assertEquals(creditCardHandler.check("1234").trim(), expected.trim());
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

        assertEquals(creditCardHandler.check("12o1").trim(), expected.trim());
    }
    
    @Test
    public void shouldReturnStringContainingOneError() {
        var expected = """
Card number is invalid.
Errors:
-> Payment System can't be determined
""";

        assertEquals(creditCardHandler.check("5425 2334 3010 9923").trim(), expected.trim());
    }
    
    @Test
    public void shouldReturnStringContainingCreditCardBrandName() {
        var expected = "Card is valid. Payment System is MASTERCARD";

        assertEquals(creditCardHandler.check("5425 2334 3010 9903").trim(), expected.trim());
    }
}
