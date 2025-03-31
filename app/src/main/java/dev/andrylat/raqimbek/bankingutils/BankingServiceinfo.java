package dev.andrylat.raqimbek.bankingutils;

import lombok.NonNull;

import java.util.List;

public record BankingServiceinfo(
    @NonNull BankingService bankingService, @NonNull DialogValidatorHolder dialogValidatorHolder) {}
