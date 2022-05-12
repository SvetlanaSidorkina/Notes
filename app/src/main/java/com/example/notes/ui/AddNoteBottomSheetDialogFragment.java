package com.example.notes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.notes.R;
import com.example.notes.di.Dependencies;
import com.example.notes.domain.Callback;
import com.example.notes.domain.Note;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNoteBottomSheetDialogFragment extends BottomSheetDialogFragment {

    public static final String ADD_KEY_RESULT = "AddNoteBottomSheetDialogFragment_ADD_KEY_RESULT";
    public static final String UPDATE_KEY_RESULT = "AddNoteBottomSheetDialogFragment_UPDATE_KEY_RESULT";
    public static final String ARG_NOTE = "ARG_NOTE";

    public static AddNoteBottomSheetDialogFragment addInstance() {
        return new AddNoteBottomSheetDialogFragment();
    }


    public static AddNoteBottomSheetDialogFragment editInstance(Note note) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);

        AddNoteBottomSheetDialogFragment fragment = new AddNoteBottomSheetDialogFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Note noteToEdit = null;

        if (getArguments() != null && getArguments().containsKey(ARG_NOTE)) {
            noteToEdit = getArguments().getParcelable(ARG_NOTE);
        }

        EditText title = view.findViewById(R.id.title_edit_text);

        if (noteToEdit != null) {
            title.setText(noteToEdit.getTitle());
        }

        Note finalNoteToEdit = noteToEdit;

        Button saveButton = view.findViewById(R.id.save_button);

        saveButton.setOnClickListener(view1 -> {
            saveButton.setEnabled(false);
            if (finalNoteToEdit != null) {
                Dependencies.getNotesRepository().updateNote(finalNoteToEdit, title.getText().toString(), new Callback<Note>() {
                    @Override
                    public void onSuccess(Note data) {

                        Bundle bundle = new Bundle();
                        bundle.putParcelable(ARG_NOTE, data);

                        getParentFragmentManager().setFragmentResult(UPDATE_KEY_RESULT, bundle);
                        saveButton.setEnabled(true);
                        dismiss();
                    }

                    @Override
                    public void onError(Throwable exception) {
                        saveButton.setEnabled(true);
                    }
                });
            } else {
                Dependencies.getNotesRepository().addNote(title.getText().toString(), new Callback<Note>() {
                    @Override
                    public void onSuccess(Note data) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(ARG_NOTE, data);

                        getParentFragmentManager().setFragmentResult(ADD_KEY_RESULT, bundle);
                        saveButton.setEnabled(true);
                        dismiss();
                    }

                    @Override
                    public void onError(Throwable exception) {
                        saveButton.setEnabled(true);
                    }
                });
            }
        });
    }
}
