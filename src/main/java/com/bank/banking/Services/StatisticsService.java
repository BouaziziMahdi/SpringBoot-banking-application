package com.bank.banking.Services;

import com.bank.banking.dto.StatisticsResponse;

public interface StatisticsService {
    StatisticsResponse getStats(Integer userId);
}
