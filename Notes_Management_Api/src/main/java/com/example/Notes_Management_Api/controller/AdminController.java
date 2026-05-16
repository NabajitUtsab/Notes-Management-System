package com.example.Notes_Management_Api.controller;

import com.example.Notes_Management_Api.dto.CommonResponse;
import com.example.Notes_Management_Api.entity.Note;
import com.example.Notes_Management_Api.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@CrossOrigin
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/notes")
    public ResponseEntity<List<Note>> getAllNotes() {
        return adminService.getAllNotes();
    }

    @DeleteMapping("/notes/{id}")
    public ResponseEntity<CommonResponse> deleteNote(@PathVariable long id) {
        try {
            return adminService.deleteNote(id); // returns ResponseEntity<CommonResponse> directly
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404)
                    .body(new CommonResponse(null, "Not Found", "Note not found"));
        }
    }
}