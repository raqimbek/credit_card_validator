package dev.andrylat.raqimbek.bankingutils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MortgageCalculator {
  public double calculate(BigDecimal p, double r, int n, int t) {
    // formula: (p * (r / n) * (1 + ((r / n) ^ n) * t)) / ((1 + ((r / n) ^ n) * t) - 1)

    var annualInterestRate = new BigDecimal(r / n);
    var bg1 = annualInterestRate.pow(n).multiply(new BigDecimal(t)).add(BigDecimal.ONE);

    return p.multiply(annualInterestRate)
        .multiply(bg1)
        .divide(bg1.subtract(BigDecimal.ONE), 10, RoundingMode.HALF_EVEN)
        .doubleValue();
  }
}
