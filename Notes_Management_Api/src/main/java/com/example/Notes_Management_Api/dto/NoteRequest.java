package com.example.Notes_Management_Api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NoteRequest {

    @NotBlank(message = "Title must not be empty")
    private String title;

    @NotBlank(message = "Content must not be empty")
    private String content;
}
