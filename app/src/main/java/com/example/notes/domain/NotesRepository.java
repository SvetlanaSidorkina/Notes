package com.example.notes.domain;

import androidx.annotation.Nullable;

import java.util.List;

public interface NotesRepository {

    List<Note> getAll();

    void add(Note note);
}
