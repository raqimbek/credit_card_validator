package dev.andrylat.raqimbek.bankingutils.validators;

import java.util.List;

public record CardValidationInfo(boolean isValid, List<String> errors) implements ValidationInfo {}
