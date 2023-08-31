package com.bank.banking.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class StatisticsResponse {

    private BigDecimal highestTransfer;
    private BigDecimal highestDeposit;
    private BigDecimal accountBalance;
}