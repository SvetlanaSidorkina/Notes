package com.example.notes.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.notes.R;
import com.example.notes.domain.Note;

public class NoteDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_NOTE = "EXTRA_NOTE";

    static void show(Context context, Note note) {
        Intent intent = new Intent(context, NoteDetailsActivity.class);
        intent.putExtra(EXTRA_NOTE, note);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_delails);

        if(savedInstanceState == null) {
            Note note = getIntent().getParcelableExtra(EXTRA_NOTE);

            NoteDetailsFragment noteDetailsFragment = NoteDetailsFragment.newInstance(note);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, noteDetailsFragment)
                    .commit();
        }
    }
}