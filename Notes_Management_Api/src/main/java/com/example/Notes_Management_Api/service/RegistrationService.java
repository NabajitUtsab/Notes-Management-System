package com.example.Notes_Management_Api.service;

import com.example.Notes_Management_Api.dto.RegisterRequest;
import com.example.Notes_Management_Api.dto.RegistrationResponse;
import com.example.Notes_Management_Api.entity.AppUser;
import com.example.Notes_Management_Api.repository.AppUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final AppUserRepo appUserRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public ResponseEntity<RegistrationResponse> userRegistration(RegisterRequest registerRequest){


        appUserRepo.save(AppUser.builder().
                username(registerRequest.getName()).
                password(bCryptPasswordEncoder.encode(registerRequest.getPassword())).
                roles(Set.of("ROLE_USER")).
                build());

        return ResponseEntity.ok(RegistrationResponse.builder().
                username("name : "+registerRequest.getName()).
                message("message : Registration successful!").
                build());
    }

    public ResponseEntity<RegistrationResponse> adminRegistration(RegisterRequest registerRequest){


        appUserRepo.save(AppUser.builder().
                username(registerRequest.getName()).
                password(bCryptPasswordEncoder.encode(registerRequest.getPassword())).
                roles(Set.of("ROLE_ADMIN")).
                build());

        return ResponseEntity.ok(RegistrationResponse.builder().
                username(registerRequest.getName()).
                message("Admin Registration successful!").
                build());
    }

}
