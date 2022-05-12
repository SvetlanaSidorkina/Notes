package com.example.notes.di;

import com.example.notes.domain.Callback;
import com.example.notes.domain.Note;
import com.example.notes.domain.NotesRepository;

import java.util.List;

public class Dependencies {

    public static NotesRepository getNotesRepository() {
        return new NotesRepository() {
            @Override
            public void getAll(Callback<List<Note>> callback) {

            }

            @Override
            public void addNote(String title, Callback<Note> callback) {

            }

            @Override
            public void removeNote(Note note, Callback<Void> callback) {

            }

            @Override
            public void updateNote(Note note, String title, Callback<Note> callback) {

            }
        };
    }
}
