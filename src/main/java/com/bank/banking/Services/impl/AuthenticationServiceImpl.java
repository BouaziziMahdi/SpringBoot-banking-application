package com.bank.banking.Services.impl;

import com.bank.banking.config.JwtService;
import com.bank.banking.dto.AuthenticationRequest;
import com.bank.banking.dto.AuthenticationResponse;
import com.bank.banking.dto.Userdto;
import com.bank.banking.models.Role;
import com.bank.banking.models.RoleType;
import com.bank.banking.models.User;
import com.bank.banking.repositories.RoleRepository;
import com.bank.banking.repositories.UserRepository;
import com.bank.banking.validator.ObjectsValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl {

    private final UserRepository useruserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final ObjectsValidator<Userdto> validator;

    @Transactional
    public AuthenticationResponse register(Userdto userdto) {
        validator.validate( userdto );
        var user = User.builder()
                .firstName( userdto.getFirstname() )
                .lastName( userdto.getLastname() )
                .email( userdto.getEmail() )
                .password(
                        passwordEncoder.encode( userdto.getPassword() )
                )
                .build();
        // set roles
        var userRole = roleRepository.findByName( RoleType.ROLE_USER.name() )
                .orElse(
                        Role.builder()
                                .name( RoleType.ROLE_USER.name() )
                                .build()
                );
        if (userRole.getId() == null) {
            userRole = roleRepository.save( userRole );
        }
        var defaultUserRole = List.of( userRole );
        user.setRoles( defaultUserRole );
        var savedUser = useruserRepository.save( user );

        userRole.setUsers( new ArrayList<>( List.of( savedUser ) ) );
        roleRepository.save( userRole );
        var claims = new HashMap<String, Object>();
        claims.put( "role", user.getRoles() );
        claims.put( "active", user.isActive() );
        var jwtToken = jwtService.generateToken( savedUser, claims );

        return AuthenticationResponse.builder()
                .token( jwtToken )
                .username(user.getFirstName() + " " + user.getLastName())

                .userId( savedUser.getId() )
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = useruserRepository.findByEmail( request.getEmail() )
                .orElseThrow();
        var claims = new HashMap<String, Object>();
        claims.put( "role", user.getRoles() );
        claims.put( "active", user.isActive() );
        var jwtToken = jwtService.generateToken( user, claims );

        return AuthenticationResponse.builder()
                .token( jwtToken )
                .username(user.getFirstName() + " " + user.getLastName())
                .userId( user.getId() )
                .build();
    }
}