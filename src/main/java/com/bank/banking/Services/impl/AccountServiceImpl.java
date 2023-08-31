package com.bank.banking.Services.impl;

import com.bank.banking.Services.AccountService;
import com.bank.banking.dto.AccountDto;
import com.bank.banking.exceptions.OperationNonPermittedException;
import com.bank.banking.repositories.AccountRepository;
import com.bank.banking.validator.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Getter
@Setter
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    @NonNull
    private final AccountDto accountDto;
    private final AccountRepository repository;
    private final ObjectsValidator<AccountDto> validator;

    @Override
    public Integer create(AccountDto accountDto) {
        validator.validate( accountDto );
        var account=AccountDto.ToEntity( accountDto );
        var userHasAlreadyAnAccount=repository.existsByUserId( accountDto.getUserId() );
        if(userHasAlreadyAnAccount){
           new OperationNonPermittedException("L’utilisateur sélectionné a déjà un account");
        }

        account.setIban(generateRandomIban());
        return repository.save(account).getId();
    }

    @Override
    public List<AccountDto> findAll() {
        return  repository.findAll().stream()
                .map(AccountDto::FromEntity )
                .collect( Collectors.toList());
    }

    @Override
    public AccountDto findById(Integer id) {
        return repository.findById( id )
                .map( AccountDto::FromEntity )
                .orElseThrow( ()->new EntityNotFoundException("Aucun compte trouvé avec l’ID : "+id) );
    }

    @Override
    public void delete(Integer id) {
   repository.deleteById( id );
    }
    private String generateRandomIban() {
        // generate a random IBAN
        var iban = Iban.random( CountryCode.TN).toFormattedString();
        // check if the iban exists
        if (repository.existsByIban(iban)) {
            // if true --> generate new IBAN
            generateRandomIban();
        }
        // if false (not exists) --> return IBAN
        return iban;
    }


}
