package dev.andrylat.raqimbek.bankingutils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class MortgageCalculatorTest {
  private final MortgageCalculator mortgageCalculator = new MortgageCalculator();

  @Test
  public void shouldReturnProperPaymentForMortgageWithInterest() {
    var testDataMap = new HashMap<Double, Map<String, Double>>();
    initializeTestData(testDataMap);

    testDataMap.forEach(
        (expectedMonthlyPayment, value) -> {
          double borrowedAmount = 0.0;
          double annualInterestRate = 0.0;
          double numberOfYears = 0.0;

          for (Map.Entry<String, Double> currentEntry : value.entrySet()) {
            switch (currentEntry.getKey()) {
              case "annualInterestRate":
                annualInterestRate = currentEntry.getValue();
                break;
              case "numberOfYears":
                numberOfYears = currentEntry.getValue();
                break;
              case "borrowedAmount":
                borrowedAmount = currentEntry.getValue();
                break;
            }
          }

          if (annualInterestRate > 0 && borrowedAmount > 0 && numberOfYears > 0) {
            var actual =
                mortgageCalculator.calculateMonthlyMortgagePayment(
                    borrowedAmount, annualInterestRate, numberOfYears);
            assertEquals(expectedMonthlyPayment, actual);
          }
        });
  }

  private void initializeTestData(Map<Double, Map<String, Double>> testDataMap) {
    putOnTestDataMap(testDataMap, 360_000.0, 7.5, 30, 2_547.0);
    putOnTestDataMap(testDataMap, 360_000.0, 5, 30, 1_938.0);
    putOnTestDataMap(testDataMap, 176_000.0, 4, 30, 841.0);
  }

  private void putOnTestDataMap(
      Map<Double, Map<String, Double>> testDataMap,
      double borrowedAmount,
      double annualInterestRate,
      double numberOfYears,
      double expectedMonthlyPayment) {
    testDataMap.put(
        expectedMonthlyPayment,
        Map.of(
            "annualInterestRate",
            annualInterestRate,
            "numberOfYears",
            numberOfYears,
            "borrowedAmount",
            borrowedAmount));
  }
}
