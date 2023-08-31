package com.bank.banking.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Account {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String iban;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
