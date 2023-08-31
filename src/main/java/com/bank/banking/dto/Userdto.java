package com.bank.banking.dto;

import com.bank.banking.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class Userdto {

    private Integer id;
    @NotNull(message = "First name is mandatory")
    private String firstname;
    @NotNull(message = "Last name is mandatory")
    private String lastname;
    @NotNull(message = "Email name is mandatory")
    @Email(message = "Email is not valid")
    private String email;
    @NotNull(message = "Email name is mandatory")
    @Size(min = 4, max = 16, message = "Password should be between 4 and 16 chars")
    private String password;
    private String iban;
    private boolean active;



    public static User toEntity(Userdto userDto) {
        return User.builder()
                .id( userDto.getId())
                .firstName(userDto.getFirstname())
                .lastName(userDto.getLastname())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .active(userDto.isActive())
                .build();
    }

    public static Userdto FromEntity(User user) {
        return Userdto.builder()
                .id(user.getId())
                .firstname(user.getFirstName())
                .lastname(user.getLastName())
                .email(user.getEmail())
                .active(user.isActive())
                .iban(user.getAccount() == null ? null : user.getAccount().getIban())
                .build();
    }

}
