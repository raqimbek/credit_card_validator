package dev.andrylat.raqimbek.bankingutils;

import lombok.NonNull;

import java.util.List;

public interface ValidationInfo {
  boolean isValid();

  @NonNull
  List<String> errors();
}
