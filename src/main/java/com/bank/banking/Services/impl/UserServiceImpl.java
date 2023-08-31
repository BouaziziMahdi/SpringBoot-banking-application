package com.bank.banking.Services.impl;

import com.bank.banking.dto.AccountDto;
import com.bank.banking.dto.LightUserDto;
import com.bank.banking.dto.Userdto;
import com.bank.banking.models.Account;
import com.bank.banking.models.TransactionType;
import com.bank.banking.models.User;
import com.bank.banking.repositories.TransactionRepository;
import com.bank.banking.repositories.UserRepository;
import com.bank.banking.validator.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
@RequiredArgsConstructor
public class UserServiceImpl {
    private final UserRepository userRepository ;
    private final ObjectsValidator<Userdto>validator ;
    private final Userdto userDto ;
    private final PasswordEncoder passwordEncoder ;
    private final TransactionRepository transactionRepository ;
    private final AccountServiceImpl accountService  ;



    public Integer create(Userdto userdto) {
        validator.validate(userdto);
        var user = Userdto.toEntity(userdto);
        user.setPassword(passwordEncoder.encode(userdto.getPassword()));

        return userRepository.save(user).getId();
    }

    public Integer update(LightUserDto userDto) {
        User user = LightUserDto.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user).getId();
    }


    public List<Userdto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(Userdto::FromEntity)
                .collect(Collectors.toList());
    }

    public Userdto findById(Integer id) {
        return userRepository.findById(id)
                .map(Userdto::FromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No user found with the ID:: " + id));
    }



    @Transactional(rollbackOn = Exception.class)
    public Integer validateAccount(Integer userId) {
        var user = userRepository.findById( userId )
                .orElseThrow(() -> new EntityNotFoundException("No user found with the ID for account validation:: " + userId));
        if (user.getAccount() == null) {
            // create a bank account
            var account = AccountDto.builder()
                    .userId(userId)
                    .build();
            var savedAccountId = accountService.create(account);
            user.setAccount(
                    Account.builder()
                            .id(savedAccountId)
                            .build()
            );
        }
        user.setActive(true);
        userRepository.save(user);
        return user.getId();
    }

    public Integer invalidateAccount(Integer userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("No user found with the ID for account invalidation:: " + userId));
        user.setActive(false);
        userRepository.save(user);
        return user.getId();
    }

    public BigDecimal getAccountBalance(Integer userId) {
        return transactionRepository.findAccountBalance(userId);
    }

    public BigDecimal highestTransfer(Integer userId) {
        return transactionRepository.findHighestAmountByTransactionType(userId, TransactionType.TRANSFERT);
    }
    public BigDecimal highestDeposit(Integer userId) {
        return transactionRepository.findHighestAmountByTransactionType(userId, TransactionType.DEPOSIT);
    }

    public List<Userdto> findAllUsersByState(boolean active) {
        return userRepository.findAllByActive( active )
                .stream()
                .map(Userdto::FromEntity)
                .collect(Collectors.toList());
    }
}