package com.example.notes.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.domain.InMemoryNotesRepository;
import com.example.notes.domain.Note;

import java.util.List;

public class NotesListFragment extends Fragment {

    static final String NOTE_CLICKED_KEY = "NOTE_CLICKED_KEY";
    static final String SELECTED_NOTE = "SELECTED_NOTE";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);

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

        NotesAdapter adapter = new NotesAdapter();

        adapter.setNoteClicked(new NotesAdapter.OnNoteClicked() {
            @Override
            public void onNoteClicked(Note note) {
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, NoteDetailsFragment.newInstance(note))
                        .addToBackStack("note")
                        .commit();
            }
        });

        notesList.setAdapter(adapter);

        List<Note> notes = InMemoryNotesRepository.getInstance(requireContext()).getAll();

        adapter.setData(notes);

        adapter.notifyDataSetChanged();

        Button addButton = view.findViewById(R.id.add);
        addButton.setOnClickListener(view12 -> new AlertDialog.Builder(requireContext())
                .setTitle(R.string.action_add)
                .setView(R.layout.fragment_dialog)
                .setPositiveButton(R.string.action_ok, (dialogInterface, i) -> getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new NoteDetailsFragment())
                        .commit())
                .show());
    }

}
