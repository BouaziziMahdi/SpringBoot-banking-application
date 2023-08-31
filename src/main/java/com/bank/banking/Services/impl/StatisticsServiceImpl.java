package com.bank.banking.Services.impl;

import com.bank.banking.dto.StatisticsResponse;
import com.bank.banking.models.TransactionType;
import com.bank.banking.repositories.TransactionRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl {
    @NonNull
    private final TransactionRepository transactionRepository;

    public StatisticsResponse getStats(Integer userId) {
        var accountBalance = transactionRepository.findAccountBalance(userId);
        var highestTransfer = transactionRepository.findHighestAmountByTransactionType(userId, TransactionType.TRANSFERT);
        var highestDeposit = transactionRepository.findHighestAmountByTransactionType(userId, TransactionType.DEPOSIT);
        return StatisticsResponse.builder()
                .accountBalance(accountBalance)
                .highestDeposit(highestDeposit)
                .highestTransfer(highestTransfer)
                .build();
    }
}
