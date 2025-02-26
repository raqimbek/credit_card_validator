package dev.andrylat.app;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class AppTest {

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
