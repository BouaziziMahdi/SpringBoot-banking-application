package com.bank.banking.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OperationNonPermittedException extends RuntimeException {
    public OperationNonPermittedException(String message) {
        super( message );
    }
}
