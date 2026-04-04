package com.example.Notes_Management_Api.controller;

import com.example.Notes_Management_Api.dto.CommonResponse;
import com.example.Notes_Management_Api.dto.NoteRequest;
import com.example.Notes_Management_Api.dto.NoteResponse;
import com.example.Notes_Management_Api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_USER')")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<NoteResponse>> getAllNotes(Authentication authentication) {
        return userService.getAllNotes(authentication.getName());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNoteById(@PathVariable long id, Authentication authentication) {
        NoteResponse note = userService.getNote(id, authentication.getName());
        if (note != null) {
            return ResponseEntity.ok(note);
        }
        return ResponseEntity.status(404)
                .body(new CommonResponse(null, "Not Found", "Note not found or access denied"));
    }

    @PostMapping
    public ResponseEntity<?> createNote(@Valid @RequestBody NoteRequest noteRequest, Authentication authentication) {
        CommonResponse response =
                userService.createNote(noteRequest, authentication.getName()).getBody();

        return ResponseEntity.status(201).body(response);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNote(@PathVariable long id, @Valid @RequestBody NoteRequest noteRequest,
                                        Authentication authentication) {
        return userService.updateNote(noteRequest, id, authentication.getName());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable long id, Authentication authentication) {
        return userService.deleteNote(id, authentication.getName());
    }
}