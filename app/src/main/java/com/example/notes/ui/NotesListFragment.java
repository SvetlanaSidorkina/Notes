package com.example.notes.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.di.Dependencies;
import com.example.notes.domain.Callback;
import com.example.notes.domain.Note;

import java.util.List;

public class NotesListFragment extends Fragment {

    static final String NOTE_CLICKED_KEY = "NOTE_CLICKED_KEY";
    static final String SELECTED_NOTE = "SELECTED_NOTE";

    private Note selectedNote;
    private int selectedPosition;
    private NotesAdapter adapter;
    private ProgressBar progressBar;

    public NotesListFragment() {
        super(R.layout.fragment_notes_list);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.toolbar);

        if (requireActivity() instanceof ToolbarHolder) {
            ((ToolbarHolder) requireActivity()).setToolbar(toolbar);
        }

        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_settings:
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new NoteSettingsFragment())
                            .commit();
                    return true;
                case R.id.action_search:
                    Toast.makeText(requireContext(), "поиск", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_info:
                    Toast.makeText(requireContext(), "о приложении", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_exit:
                    view.findViewById(R.id.action_exit).setOnClickListener(view13 -> new AlertDialog.Builder(NotesListFragment.this.requireContext())
                            .setMessage(R.string.exit_question)
                            .setCancelable(false)
                            .setPositiveButton(R.string.exit_pozitive_answer, (dialogInterface, i) -> Toast.makeText(NotesListFragment.this.requireContext(), "да", Toast.LENGTH_SHORT).show())
                            .setNegativeButton(R.string.exit_negative_answer, (dialogInterface, i) -> Toast.makeText(NotesListFragment.this.requireContext(), R.string.exit_negative_answer_toast, Toast.LENGTH_LONG).show())
                            .show());
                    return true;
            }
            return false;
        });

        RecyclerView notesList = view.findViewById(R.id.notes_list);

        notesList.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

        adapter = new NotesAdapter(this);

        adapter.setNoteClicked(new NotesAdapter.OnNoteClicked() {
            @Override
            public void onNoteClicked(Note note) {
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, NoteDetailsFragment.newInstance(note))
                        .addToBackStack("note")
                        .commit();
            }

            @Override
            public void onNoteLongClicked(Note note, int position) {
                selectedNote = note;
                selectedPosition = position;
            }
        });

        notesList.setAdapter(adapter);

        getParentFragmentManager()
                .setFragmentResultListener(AddNoteBottomSheetDialogFragment.ADD_KEY_RESULT, getViewLifecycleOwner(), (requestKey, result) -> {
                    Note note = result.getParcelable(AddNoteBottomSheetDialogFragment.ARG_NOTE);

                    int index = adapter.addNote(note);

                    adapter.notifyItemInserted(index);

                    notesList.smoothScrollToPosition(index);
                });

        getParentFragmentManager()
                .setFragmentResultListener(AddNoteBottomSheetDialogFragment.UPDATE_KEY_RESULT, getViewLifecycleOwner(), (requestKey, result) -> {
                    Note note = result.getParcelable(AddNoteBottomSheetDialogFragment.ARG_NOTE);

                    adapter.replaceNote(note, selectedPosition);
                    adapter.notifyItemChanged(selectedPosition);
                });

        view.findViewById(R.id.add).setOnClickListener(view12 -> AddNoteBottomSheetDialogFragment.addInstance()
                .show(NotesListFragment.this.getParentFragmentManager(), "AddNoteBottomSheetDialogFragment"));

        progressBar = view.findViewById(R.id.progress);

        Dependencies.getNotesRepository().getAll(new Callback<List<Note>>() {
            @Override
            public void onSuccess(List<Note> data) {
                adapter.setData(data);

                adapter.notifyDataSetChanged();

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable exception) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.menu_notes_context, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                AddNoteBottomSheetDialogFragment.editInstance(selectedNote)
                        .show(NotesListFragment.this.getParentFragmentManager(), "AddNoteBottomSheetDialogFragment");
                return true;
            case R.id.action_delete:
                progressBar.setVisibility(View.VISIBLE);

                Dependencies.getNotesRepository().removeNote(selectedNote, new Callback<Void>() {
                    @Override
                    public void onSuccess(Void data) {
                        progressBar.setVisibility(View.GONE);

                        adapter.removeNote(selectedNote);
                        adapter.notifyItemRemoved(selectedPosition);
                    }

                    @Override
                    public void onError(Throwable exception) {

                    }
                });
                return true;
        }
        return super.onContextItemSelected(item);
    }
}
