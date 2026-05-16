package com.example.Notes_Management_Api.controller;

import com.example.Notes_Management_Api.dto.CommonResponse;
import com.example.Notes_Management_Api.dto.RegisterRequest;
import com.example.Notes_Management_Api.service.LoginService;
import com.example.Notes_Management_Api.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class RegistrationController {

    private final RegistrationService registrationService;
    private final LoginService loginService;

    @PostMapping("/register/admin")
    public ResponseEntity<?> createAdmin(@Valid @RequestBody RegisterRequest registerRequest) {
        return registrationService.adminRegistration(registerRequest);
    }

    @PostMapping("/register/user")
    public ResponseEntity<?> createUser(@Valid @RequestBody RegisterRequest registerRequest) {
        return registrationService.userRegistration(registerRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody RegisterRequest registerRequest) {
        return loginService.login(registerRequest);
    }
}