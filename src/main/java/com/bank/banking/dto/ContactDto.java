package com.bank.banking.dto;

import com.bank.banking.models.Contact;
import com.bank.banking.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ContactDto {

    private Integer id;
    @NotNull(message = "First name is mandatory")
    @NotBlank(message = "First name is mandatory")
    private String firstname;
    @NotNull(message = "Last name is mandatory")
    @NotBlank(message = "Last name is mandatory")
    private String lastname;
    @NotNull(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be a valid address")
    private String email;
    @NotNull(message = "IBAN is mandatory")
    @NotBlank(message = "IBAN is mandatory")
    private String iban;
    @NotNull(message = "User is mandatory")
    @Positive(message = "User is mandatory")
    private Integer userId;
    public static Contact toEntity(ContactDto dto) {
        return Contact.builder()
                .id(dto.getId())
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .email(dto.getEmail())
                .iban(dto.getIban())
                .user(
                        User.builder()
                                .id(dto.getUserId())
                                .build()
                )
                .build();
    }

    public  static ContactDto fromEntity(Contact contact) {
        return ContactDto.builder()
                .id(contact.getId())
                .firstname(contact.getFirstname())
                .lastname(contact.getLastname())
                .email(contact.getEmail())
                .iban(contact.getIban())
                .build();
    }
}
