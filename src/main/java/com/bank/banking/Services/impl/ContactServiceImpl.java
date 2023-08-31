package com.bank.banking.Services.impl;

import com.bank.banking.Services.ContactService;
import com.bank.banking.dto.ContactDto;
import com.bank.banking.repositories.ContactRepository;
import com.bank.banking.validator.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository ;
    private final ObjectsValidator<ContactDto>validator ;
    @Override
    public Integer create(ContactDto contactDto) {
        validator.validate( contactDto );
        var contact=ContactDto.toEntity( contactDto );
        return contactRepository.save( contact ).getId();
    }

    @Override
    public List<ContactDto> findAllByUser(Integer userId) {
        return contactRepository.findAllByUserId( userId )
                .stream().map( ContactDto::fromEntity )
                .collect( Collectors.toList());
    }

    @Override
    public ContactDto findById(Integer contactId) {
        return contactRepository.findById( contactId )
                .map( ContactDto::fromEntity )
                .orElseThrow(()->new EntityNotFoundException("Aucun contact trouvé avec l'ID"+contactId) );
    }

    @Override
    public void delete(Integer contactId) {
     contactRepository.deleteById( contactId );
    }
}
