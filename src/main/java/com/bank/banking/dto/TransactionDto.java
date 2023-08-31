package com.bank.banking.dto;

import com.bank.banking.models.Transaction;
import com.bank.banking.models.TransactionType;
import com.bank.banking.models.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto {
    @NotNull(message = "Amount is mandatory")
    @Positive(message = "Amount should be positive")
    @Min(1)
    private BigDecimal amount;
    @NotNull(message = "IBAN is mandatory")
    private String destinationIban;
    @NotNull(message = "TYpe is mandatory")
    private TransactionType type;
    @NotNull(message = "User is mandatory")
    private Integer userId;
    private LocalDate transactionDate;

    public static Transaction toEntity(TransactionDto dto) {
        return Transaction.builder()
                .amount(dto.getAmount())
                .destinationIban(dto.getDestinationIban())
                .type(dto.getType())
                .transactionDate(LocalDate.now())
                .user(
                        User.builder()
                                .id(dto.getUserId())
                                .build()
                )
                .build();
    }

    public static TransactionDto fromEntity(Transaction transaction) {
        return TransactionDto.builder()
                .amount(transaction.getAmount())
                .destinationIban(transaction.getDestinationIban())
                .type(transaction.getType())
                .transactionDate(transaction.getTransactionDate())
                .build();
    }
}
