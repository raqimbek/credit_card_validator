package dev.andrylat.app;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class AppTest {

    @Test
    public void shouldReturnFalse() {
        var app = new App();
        
        assertEquals(app.isDigit(" "), false);
        assertEquals(app.isDigit("a"), false);
    }

    @Test
    public void shouldReturnTrue() {
        assertEquals(new App().isDigit("1"), true);
    }

    @Test
    public void shouldReturnEmptyList() {
        assertEquals(new App().getCardNum("abcde"), new ArrayList<Integer>());
    }

    @Test
    public void shouldReturnListThatContains3Ints() {
        assertEquals(new App().getCardNum("abc123"), new ArrayList<Integer>(List.of(1,2,3)));
    }

    @Test
    public void shouldReturnListThatContains16Ints() {
        assertEquals(new App().getCardNum("4417 1234 5678 9113"), new ArrayList<>(List.of(4,4,1,7,1,2,3,4,5,6,7,8,9,1,1,3)));
    }

    @Test
    public void shouldReturnEmptyStringBuilder() {
        var app = new App();
        app.validate("4417 1234 5678 9113");
        assertEquals(app.getErrors().length(), 0);
    }

    @Test
    public void shouldReturnStringBuilderThatContainsOneErrorDescribingLength() {
        var expected = "-> Length should be 16 symbols";

        var app = new App();
        app.validate("123 oo 44 oooo 1"); // this string's length is 16
        assertEquals(app.getErrors().toString().trim(), expected);
    }

    @Test
    public void shouldReturnStringBuilderThatContainsOneErrorDescribingPaymentSystem() {
        var expected = "-> Payment System can't be determined";

        var app = new App();
        app.validate("4417 1234 5678 9114");
        app.validate(app.getCardNum("4417 1234 5678 9114"));
        assertEquals(app.getErrors().toString().trim(), expected);
    }

    @Test
    public void shouldReturnStringBuilderThatContainsTwoErrors() {
        var expected = """
-> Length should be 16 symbols
-> Payment System can't be determined
""";

        var app = new App();
        app.validate("123");
        app.validate(app.getCardNum("123"));
        assertEquals(app.getErrors().toString().trim(), expected.trim());
    }

    @Test
    public void shouldReturnStringBuilderThatContainsThreeErrors() {
        var expected = """
-> Length should be 16 symbols
-> Number should contain only digits
-> Payment System can't be determined
""";

        var app = new App();
        app.validate("123oo44");
        app.validate(app.getCardNum("123oo44"));
        assertEquals(app.getErrors().toString().trim(), expected.trim());
    }
}
