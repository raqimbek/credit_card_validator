package dev.andrylat.raqimbek.bankingutils;

import lombok.NonNull;

public record DialogValidatorHolder(@NonNull Dialog dialog, @NonNull Validator validator) {}
