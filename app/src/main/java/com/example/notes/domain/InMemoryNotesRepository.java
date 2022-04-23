package com.example.notes.domain;

import android.content.Context;

import com.example.notes.R;

import java.util.ArrayList;
import java.util.List;

public class InMemoryNotesRepository implements NotesRepository {

    private static NotesRepository INSTANCE;
    ArrayList<Note> result = new ArrayList<>();

    public static NotesRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new InMemoryNotesRepository(context);
        }
        return INSTANCE;
    }

    private final Context context;

    private InMemoryNotesRepository(Context context) {
        this.context = context;
    }

    @Override
    public List<Note> getAll() {

        result.add(new Note(context.getString(R.string.title1), context.getString(R.string.content1)));
        result.add(new Note(context.getString(R.string.title2), context.getString(R.string.content2)));
        result.add(new Note(context.getString(R.string.title3), context.getString(R.string.content3)));
        result.add(new Note(context.getString(R.string.title4), context.getString(R.string.content4)));
        result.add(new Note(context.getString(R.string.title5), context.getString(R.string.content5)));

        return result;
    }

    @Override
    public void add(Note note) {
        result.add(note);
    }
}
