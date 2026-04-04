package com.example.Notes_Management_Api.repository;


import com.example.Notes_Management_Api.entity.AppUser;
import com.example.Notes_Management_Api.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepo extends JpaRepository<Note, Long> {

    List<Note> findByAppUser(AppUser appUser);
}
