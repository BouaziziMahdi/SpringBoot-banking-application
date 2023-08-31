package com.bank.banking.Services;

import com.bank.banking.dto.AccountDto;

import java.util.List;

public interface AccountService {
    Integer create(AccountDto accountDto);
    List<AccountDto> findAll();
    AccountDto findById(Integer id);
    void delete(Integer id);

}
