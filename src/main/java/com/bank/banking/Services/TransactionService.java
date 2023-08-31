package com.bank.banking.Services;

import com.bank.banking.dto.TransactionDto;
import com.bank.banking.models.TransactionType;

import java.util.List;

public interface TransactionService {
    Integer create(TransactionDto transactionDto);
    List<TransactionDto> findAllByUser(Integer userId);
    int getTransactionMultiplier(TransactionType type);
}
