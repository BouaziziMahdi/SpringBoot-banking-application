package com.bank.banking.Services;


import com.bank.banking.dto.ContactDto;

import java.util.List;

public interface ContactService {
    Integer create(ContactDto contactDto);
    List<ContactDto> findAllByUser(Integer userId);
    ContactDto findById(Integer contactId);
    void delete(Integer contactId);
}
