package com.example.notes.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.notes.R;
import com.example.notes.domain.Note;

public class NoteDetailsFragment extends Fragment {

    private static final String ARG_NOTE = "ARG_NOTE";
    private TextView titleToolbar;
    private TextView detail;

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

        titleToolbar = view.findViewById(R.id.title_toolbar);
        detail = view.findViewById(R.id.detail);

        Toolbar toolbar = view.findViewById(R.id.toolbar);

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_search) {
                Toast.makeText(requireContext(), "поиск", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });

        toolbar.setNavigationOnClickListener(view1 -> getParentFragmentManager()
                .popBackStack());

        getParentFragmentManager()
                .setFragmentResultListener(NotesListFragment.NOTE_CLICKED_KEY, getViewLifecycleOwner(), (requestKey, result) -> {
                    Note note = result.getParcelable(NotesListFragment.SELECTED_NOTE);

                    showNote(note);
                });

        if (getArguments() != null && getArguments().containsKey(ARG_NOTE)) {
            Note note = getArguments().getParcelable(ARG_NOTE);
            showNote(note);
        }

        Button saveButton = view.findViewById(R.id.save_btn);
        saveButton.setOnClickListener(view12 -> Toast.makeText(requireContext(), "сохранение", Toast.LENGTH_SHORT).show());

    }

    private void showNote(Note note) {
        titleToolbar.setText(note.getTitle());
        detail.setText(note.getDetail());
    }

}
