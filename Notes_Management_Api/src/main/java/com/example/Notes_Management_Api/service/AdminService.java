package com.example.Notes_Management_Api.service;

import com.example.Notes_Management_Api.dto.CommonResponse;
import com.example.Notes_Management_Api.entity.Note;
import com.example.Notes_Management_Api.repository.NoteRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final NoteRepo noteRepo;

    public ResponseEntity<List<Note>> getAllNotes() {
        List<Note> notes = noteRepo.findAll();
        return ResponseEntity.ok(notes);
    }


    public ResponseEntity<CommonResponse> deleteNote(long id) {
        Note note = noteRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Note not found"));

        noteRepo.delete(note);

        return ResponseEntity.ok(new CommonResponse(Instant.now().toString(), null, "Note deleted successfully"));
    }
}