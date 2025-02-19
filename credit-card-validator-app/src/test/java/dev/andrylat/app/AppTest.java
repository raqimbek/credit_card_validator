package dev.andrylat.app;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AppTest {
    App app;

    public AppTest {
        app = new App();
    }

    @Test
    public void shouldReturnFalse() {
        
        Assert.assertEquals(app.isDigit(" "), false);
        Assert.assertEquals(app.isDigit("a"), false);
    }

    @Test
    public void shouldReturnTrue() {
        Assert.assertEquals(app.isDigit("1"), true);
    }
}
