package com.bank.banking.controlleurs;


import com.bank.banking.Services.impl.UserServiceImpl;
import com.bank.banking.dto.Userdto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users")
public class UserController {

    private final UserServiceImpl service;

    @Operation(
            description = "Saves a user to the database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User successfully created"),
                    @ApiResponse(responseCode = "403", description = "Missing or invalid JWT token")
            }
    )
   /* @PostMapping("")
    public ResponseEntity<Integer> save(
            @RequestBody LightUserDto userDto
    ) {
        return ResponseEntity.ok(service.update(userDto));
    }*/
   @PostMapping
    public Integer save(
            @RequestBody Userdto userdto
    ) {
        return service.create(userdto);
    }

    @GetMapping
    public ResponseEntity<List<Userdto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(
            description = "Finds user by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User successfully created"),
                    @ApiResponse(responseCode = "403", description = "Missing or invalid JWT token")
            }
    )
    @GetMapping("/{user-id}")
    public ResponseEntity<Userdto> findById(
            @PathVariable("user-id") Integer id
    ) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PatchMapping("/validate/{user-id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Integer validate(
            @PathVariable("user-id") Integer id
    ) {
        return service.validateAccount(id);
    }

    @PatchMapping("/invalidate/{user-id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Integer invalidate(
            @PathVariable("user-id") Integer id
    ) {
        return service.invalidateAccount(id);
    }

    @GetMapping("/transactions/highest/{user-id}")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal getHighestTransfert(
            @PathVariable("user-id") Integer userId
    ) {
        return service.highestTransfer(userId);
    }
    @GetMapping("/transactions/lowest/{user-id}")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal getHighestDeposit(
            @PathVariable("user-id") Integer userId
    ) {
        return service.highestDeposit(userId);
    }

    @GetMapping("/account/balance/{user-id}")
    public BigDecimal getAccountBalance(
            @PathVariable("user-id") Integer userId
    ) {
        return service.getAccountBalance(userId);
    }

}