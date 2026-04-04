package com.example.Notes_Management_Api.service;

import com.example.Notes_Management_Api.dto.CommonResponse;
import com.example.Notes_Management_Api.dto.NoteRequest;
import com.example.Notes_Management_Api.dto.NoteResponse;
import com.example.Notes_Management_Api.entity.AppUser;
import com.example.Notes_Management_Api.entity.Note;
import com.example.Notes_Management_Api.repository.AppUserRepo;
import com.example.Notes_Management_Api.repository.NoteRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final NoteRepo noteRepo;
    private final AppUserRepo appUserRepo;

    public ResponseEntity<List<NoteResponse>> getAllNotes(String username) {
        AppUser user = appUserRepo.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        List<NoteResponse> noteResponses = noteRepo.findByAppUser(user)
                .stream()
                .map(note -> new NoteResponse(note.getId(), note.getTitle(), note.getContent()))
                .toList();

        return ResponseEntity.ok(noteResponses);
    }


    public NoteResponse getNote(Long id, String username) {
        AppUser user = appUserRepo.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        Note note = noteRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Note not found"));

        if (!note.getAppUser().getUsername().equals(user.getUsername())) {
            return null; // Controller handles 404
        }

        return new NoteResponse(note.getId(), note.getTitle(), note.getContent());
    }


    public ResponseEntity<CommonResponse> createNote(NoteRequest noteRequest, String username) {
        AppUser user = appUserRepo.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        noteRepo.save(Note.builder()
                .title(noteRequest.getTitle())
                .content(noteRequest.getContent())
                .appUser(user)
                .build());

        CommonResponse commonResponse = CommonResponse.builder()
                .timestamp(Instant.now().toString())
                .error(null)
                .message("Note created successfully")
                .build();

        return new ResponseEntity<>(commonResponse, HttpStatus.CREATED);
    }


    public ResponseEntity<CommonResponse> updateNote(NoteRequest noteRequest, Long id, String username) {
        AppUser user = appUserRepo.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        Note note = noteRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Note not found"));

        if (!note.getAppUser().getUsername().equals(user.getUsername())) {
            return ResponseEntity.status(403)
                    .body(new CommonResponse(Instant.now().toString(), "Forbidden", "You cannot update this note"));
        }

        note.setTitle(noteRequest.getTitle());
        note.setContent(noteRequest.getContent());
        noteRepo.save(note);

        return ResponseEntity.ok(new CommonResponse(Instant.now().toString(), null, "Note updated successfully"));
    }


    public ResponseEntity<CommonResponse> deleteNote(Long id, String username) {
        AppUser user = appUserRepo.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        Note note = noteRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Note not found"));

        if (!note.getAppUser().getUsername().equals(user.getUsername())) {
            return ResponseEntity.status(403)
                    .body(new CommonResponse(Instant.now().toString(), "Forbidden", "You cannot delete this note"));
        }

        noteRepo.delete(note);
        return ResponseEntity.ok(new CommonResponse(Instant.now().toString(), null, "Note deleted successfully"));
    }
}