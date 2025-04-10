package dev.andrylat.raqimbek.bankingutils.core.validators;

import java.util.List;

public interface Validator {
  ValidationInfo validate(List<String> input);
}
