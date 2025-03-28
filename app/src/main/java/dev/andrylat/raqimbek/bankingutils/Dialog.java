package dev.andrylat.raqimbek.bankingutils;

import lombok.NonNull;

public interface Dialog {

  @NonNull
  String prompt();

  void display(@NonNull String message);

  void displayAll(@NonNull String... messages);
}
