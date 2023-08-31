package com.bank.banking.controlleurs;

import com.bank.banking.Services.impl.StatisticsServiceImpl;
import com.bank.banking.dto.StatisticsResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
@Tag(name = "statistics")
public class StatisticsController {
   @NonNull
    private final StatisticsServiceImpl service;

    @GetMapping("/{user-id}")
    public ResponseEntity<StatisticsResponse> getUserStats(
            @PathVariable("user-id") Integer userId
    ) {
        return ResponseEntity.ok(service.getStats(userId));
    }
}
