package dev.andrylat.raqimbek.bankingutils.core.validators;

import java.util.List;

public interface ValidationInfo {
  boolean isValid();

  List<String> errors();
}
