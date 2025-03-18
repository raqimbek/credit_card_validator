package dev.andrylat.raqimbek.bankingutils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CardValidatorTest {
  CardValidator cardValidator = new CardValidator();

  @Test
  public void shouldReturnInvalidLengthErrorMessage() {
    var expected = "Length should be 16 symbols";
    var actual = "";
    var errors =  cardValidator.checkCardNumber("1234").getErrors();

    if (!errors.isEmpty()) {
      actual = errors.getFirst();
    }

    assertEquals(expected, actual);
  }

  @Test
  public void shouldReturnOnlyDigitsErrorMessage() {
    var expected = "Card Number must contain only digits";
    var actual = "";
    var errors = cardValidator.checkCardNumber("123ab").getErrors();

    if (!errors.isEmpty()) {
      actual = errors.getFirst();
    }

    assertEquals(expected, actual);
  }

  @Test
  public void shouldReturnNoPaymentSystemErrorMessage() {
    var expected =
        List.of(
            "Length should be 16 symbols",
            "Payment System can't be determined",
            "Card Number does not pass the Luhn Test");
    var actual =  cardValidator.checkCardNumber("5625 2334 3010 9903").getErrors();


    assertEquals(expected, actual);
  }

  @Test
  public void shouldReturnLuhnTestErrorMessage() {
    var expected = "Card Number does not pass the Luhn Test";
    var actual = "";
    var errors = cardValidator.checkCardNumber("5425 2334 3010 9923").getErrors();
    if (!errors.isEmpty()) {
      actual = errors.getFirst();
    }

    assertEquals(expected, actual);
  }

  @Test
  public void shouldReturnNoErrorMessage() {
    var expected = 0;
    var errors = cardValidator.checkCardNumber("5425 2334 3010 9903").getErrors();
    var actual = errors.size();

    assertEquals(expected, actual);
  }
}
