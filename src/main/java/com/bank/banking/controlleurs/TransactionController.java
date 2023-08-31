package com.bank.banking.controlleurs;

import com.bank.banking.Services.TransactionService;
import com.bank.banking.dto.TransactionDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@Tag(name = "Transactions")
public class TransactionController {
    private final TransactionService service;

    @PostMapping
    public Integer save(
            @RequestBody TransactionDto transaction
    ) {
        return service.create(transaction);
    }

    @GetMapping("/user/{user-id}")
    public ResponseEntity<List<TransactionDto>> findAll(
            @PathVariable("user-id") Integer id
    ) {
        return ResponseEntity.ok(service.findAllByUser(id));
    }
}
