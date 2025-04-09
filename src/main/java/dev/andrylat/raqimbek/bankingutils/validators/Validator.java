package dev.andrylat.raqimbek.bankingutils.validators;

import java.util.List;

public interface Validator {
  ValidationInfo validate(List<String> input);
}
