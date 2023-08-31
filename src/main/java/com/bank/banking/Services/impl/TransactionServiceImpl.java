package com.bank.banking.Services.impl;

import com.bank.banking.Services.TransactionService;
import com.bank.banking.dto.TransactionDto;
import com.bank.banking.models.TransactionType;
import com.bank.banking.repositories.TransactionRepository;
import com.bank.banking.validator.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final ObjectsValidator<TransactionDto> validator ;
    @Override
    public Integer create(TransactionDto transactionDto) {
        validator.validate( transactionDto );
        var transaction=TransactionDto.toEntity( transactionDto );
        var multiplier = BigDecimal.valueOf(getTransactionMultiplier(transactionDto.getType()));
        var amountToSave=transactionDto.getAmount().multiply( multiplier );
         transaction.setAmount( amountToSave );
        return transactionRepository.save( transaction ).getId();
    }

    @Override
    public List<TransactionDto> findAllByUser(Integer userId) {
        return transactionRepository.findAllByUserId( userId )
                .stream().map( TransactionDto::fromEntity )
                .collect( Collectors.toList());
    }

    @Override
    public int getTransactionMultiplier(TransactionType type) {
            return  TransactionType.TRANSFERT == type ? -1 : 1;
    }
}
