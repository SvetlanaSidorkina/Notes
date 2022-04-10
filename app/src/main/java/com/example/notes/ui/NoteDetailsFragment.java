package com.example.notes.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notes.R;
import com.example.notes.domain.Note;

public class NoteDetailsFragment extends Fragment {

    private static final String ARG_NOTE = "ARG_NOTE";
    private TextView title;
    private TextView detail;
    private TextView dateNote;
    private DatePicker datePicker;

    public static NoteDetailsFragment newInstance(Note note) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);

        NoteDetailsFragment fragment = new NoteDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public NoteDetailsFragment() {
        super(R.layout.fragment_note_details);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = view.findViewById(R.id.title);
        detail = view.findViewById(R.id.detail);
        datePicker = view.findViewById(R.id.date_picker);
        dateNote = view.findViewById(R.id.date_text);

        getParentFragmentManager()
                .setFragmentResultListener(NotesListFragment.NOTE_CLICKED_KEY, getViewLifecycleOwner(), (requestKey, result) -> {
                    Note note = result.getParcelable(NotesListFragment.SELECTED_NOTE);

                    showNote(note);
                });

        if (getArguments() != null && getArguments().containsKey(ARG_NOTE)) {
            Note note = getArguments().getParcelable(ARG_NOTE);
            showNote(note);
        }

    }

    private void showNote(Note note) {
        title.setText(note.getTitle());
        detail.setText(note.getDetail());
        datePicker.init(2022, 0, 1, (datePicker, dayOfMonth, monthOfYear, year) -> dateNote.setText("Дата: " + dayOfMonth + "." + (monthOfYear + 1) + "." + year));
    }

}
