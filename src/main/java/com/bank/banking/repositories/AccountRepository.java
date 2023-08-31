package com.bank.banking.repositories;

import com.bank.banking.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Integer> {
    boolean existsByUserId(Integer userId);

    boolean existsByIban(String iban);
}
