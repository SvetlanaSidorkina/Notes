package com.example.notes.domain;

import java.util.List;

public interface NotesRepository {

    void getAll(Callback<List<Note>> callback);

    void addNote(String title, Callback<Note> callback);

    void removeNote(Note note, Callback<Void> callback);

    void updateNote(Note note, String title, Callback<Note> callback);
}
