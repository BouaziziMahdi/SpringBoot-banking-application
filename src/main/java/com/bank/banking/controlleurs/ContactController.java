package com.bank.banking.controlleurs;

import com.bank.banking.Services.ContactService;
import com.bank.banking.dto.ContactDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contacts")
@RequiredArgsConstructor
@Tag(name = "Contacts")
public class ContactController {

    private final ContactService service;

    @PostMapping
    public Integer save(
            @RequestBody ContactDto contactDto
    ) {
        return service.create(contactDto);
    }

    @GetMapping("/user/{user-id}")
    public ResponseEntity<List<ContactDto>> findAll(
            @PathVariable("user-id") Integer id
    ) {
        return ResponseEntity.ok(service.findAllByUser(id));
    }

    @GetMapping("/{contact-id}")
    public ResponseEntity<ContactDto> findById(
            @PathVariable("contact-id") Integer id
    ) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{contact-id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(
            @PathVariable("contact-id") Integer id
    ) {
        service.delete(id);
    }
}
