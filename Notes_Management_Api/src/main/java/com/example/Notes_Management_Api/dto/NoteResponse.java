package com.example.Notes_Management_Api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NoteResponse {
    private Long id;
    private String title;
    private String content;
}
