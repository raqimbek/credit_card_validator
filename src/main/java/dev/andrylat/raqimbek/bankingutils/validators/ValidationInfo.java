package dev.andrylat.raqimbek.bankingutils.validators;

import java.util.List;

public interface ValidationInfo {
  boolean isValid();

  List<String> errors();
}
