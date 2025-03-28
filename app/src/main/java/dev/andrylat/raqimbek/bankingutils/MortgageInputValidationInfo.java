package dev.andrylat.raqimbek.bankingutils;

import java.util.List;

public record MortgageInputValidationInfo(boolean isValid, List<String> errors) {}
