package com.bank.banking.exceptions;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Set;
@Data
@RequiredArgsConstructor
public class ObjectValidationException extends RuntimeException {
    @NonNull
    private final Set<String> violations;
    @NonNull
    private final String violationSource;
}