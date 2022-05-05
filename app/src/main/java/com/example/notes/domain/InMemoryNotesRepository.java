package com.example.notes.domain;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InMemoryNotesRepository implements NotesRepository {

    private final Context context;
    private static NotesRepository INSTANCE;
    private final ArrayList<Note> result = new ArrayList<>();

    public static NotesRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new InMemoryNotesRepository(context);
        }
        return INSTANCE;
    }

    private InMemoryNotesRepository(Context context) {
        this.context = context;
    }

    @Override
    public List<Note> getAll() {
        result.add(new Note("Заметка 1", "Сождержание заметки 1", new Date()));
        result.add(new Note("Заметка 2", "Сождержание заметки 2", new Date()));
        result.add(new Note("Заметка 3", "Сождержание заметки 3", new Date()));
        return result;
    }

    @Override
    public void add(Note note) {

    }

}
