package com.bank.banking.controlleurs;


import com.bank.banking.Services.impl.AuthenticationServiceImpl;
import com.bank.banking.dto.AuthenticationRequest;
import com.bank.banking.dto.AuthenticationResponse;
import com.bank.banking.dto.Userdto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag( name = "authentication")
public class AuthenticationController {
    private final AuthenticationServiceImpl service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody Userdto userDto
    ) {
        return ResponseEntity.ok(service.register(userDto));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
