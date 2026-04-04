package com.example.Notes_Management_Api.service;

import com.example.Notes_Management_Api.configuration.JwtUtil;
import com.example.Notes_Management_Api.dto.RegisterRequest;
import com.example.Notes_Management_Api.dto.RegistrationResponse;
import com.example.Notes_Management_Api.entity.AppUser;
import com.example.Notes_Management_Api.repository.AppUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AppUserRepo appUserRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public ResponseEntity<RegistrationResponse> login(RegisterRequest registerRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(registerRequest.getName(), registerRequest.getPassword())
        );

        AppUser user = appUserRepo.findByUsername(registerRequest.getName()).orElseThrow();

        String token = jwtUtil.generateToken(user.getUsername(), user.getRoles());

        return ResponseEntity.ok(
                new RegistrationResponse(user.getUsername(), "Login successful. Token: " + token)
        );
    }
}
