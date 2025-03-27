package dev.andrylat.raqimbek.bankingutils;

import java.util.List;

public record CardValidationInfo(boolean isValid, List<String> errors) {}
