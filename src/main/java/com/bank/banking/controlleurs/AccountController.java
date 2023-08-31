package com.bank.banking.controlleurs;

import com.bank.banking.Services.AccountService;
import com.bank.banking.dto.AccountDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Tag(name = "Accounts")
public class AccountController {

    private final AccountService service;

    @PostMapping
    public Integer save(
            @RequestBody AccountDto accountDto
    ) {
        return service.create(accountDto);
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{account-id}")
    public ResponseEntity<AccountDto> findById(
            @PathVariable("account-id") Integer id
    ) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{account-id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(
            @PathVariable("account-id") Integer id
    ) {
        service.delete( id );
    }
}