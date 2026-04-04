package com.example.Notes_Management_Api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @NotBlank(message = "Username must not be empty")
    private String name;

    @NotBlank(message = "Password must not be empty")
    private String password;

    private Set<String> roles;
}
