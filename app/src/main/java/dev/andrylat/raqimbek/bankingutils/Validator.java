package dev.andrylat.raqimbek.bankingutils;

import lombok.NonNull;

import java.util.List;

public interface Validator {
  @NonNull
  ValidationInfo validate(@NonNull List<String> input);
}
