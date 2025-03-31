package dev.andrylat.raqimbek.bankingutils;

import lombok.NonNull;

import java.util.List;

public interface BankingService<T, U> {
  @NonNull
  T run(@NonNull List<U> input);
}
