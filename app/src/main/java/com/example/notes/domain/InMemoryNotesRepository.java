package com.example.notes.domain;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class InMemoryNotesRepository implements NotesRepository {

    private final ArrayList<Note> result = new ArrayList<>();

    private Executor executor = Executors.newSingleThreadExecutor();

    private Handler handler = new Handler(Looper.getMainLooper());

    private InMemoryNotesRepository() {
        result.add(new Note("Заметка 1", "Сождержание заметки 1", new Date()));
        result.add(new Note("Заметка 2", "Сождержание заметки 2", new Date()));
        result.add(new Note("Заметка 3", "Сождержание заметки 3", new Date()));
    }

    @Override
    public void getAll(Callback<List<Note>> callback) {

        executor.execute(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.post(() -> callback.onSuccess(result));

        });
    }

    @Override
    public void addNote(String title, Callback<Note> callback) {
        executor.execute(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Note note = new Note(title, new Date());
            result.add(note);

            handler.post(() -> callback.onSuccess(note));
        });
    }

    @Override
    public void removeNote(Note note, Callback<Void> callback) {
        executor.execute(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            result.remove(note);

            handler.post(() -> callback.onSuccess(null));
        });
    }

    @Override
    public void updateNote(Note note, String title, Callback<Note> callback) {
        executor.execute(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Note newNote = new Note(title, note.getCreatedAt());

            int index = result.indexOf(note);

            result.set(index, newNote);

            handler.post(() -> callback.onSuccess(newNote));
        });
    }

}