package dev.andrylat.app;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class AppTest {

    @Test
    public void shouldReturnStringContainingTwoErrors() {
        var expected = """
-> Length should be 16 symbols
-> Payment System can't be determined
""";

        var app = new App().process();
        assertEquals(app.getErrors().trim(), expected.trim());
    }

    @Test
    public void shouldReturnStringContainingThreeErrors() {
        var expected = """
-> Length should be 16 symbols
-> Number should contain only digits
-> Payment System can't be determined
""";

        var app = new App().process();
        assertEquals(app.getErrors().trim(), expected.trim());
    }
}
