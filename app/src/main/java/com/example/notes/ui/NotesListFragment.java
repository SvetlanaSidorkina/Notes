package com.example.notes.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

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
                    view.findViewById(R.id.action_exit).setOnClickListener(view13 -> new AlertDialog.Builder(requireContext())
                            .setMessage("Действительно хотите выйти из приложения?")
                            .setCancelable(false)
                            .setPositiveButton("Да", (dialogInterface, i) -> Toast.makeText(requireContext(), "да", Toast.LENGTH_SHORT).show())
                            .setNegativeButton("Нет", (dialogInterface, i) -> Toast.makeText(requireContext(), "Отлично, продолжим!", Toast.LENGTH_LONG).show())
                            .show());
                    return true;
            }
            return false;
        });

        List<Note> notes = InMemoryNotesRepository.getInstance(requireContext()).getAll();

        LinearLayout container = view.findViewById(R.id.container);

        for (Note note : notes) {
            View itemView = getLayoutInflater().inflate(R.layout.item_note, container, false);

            itemView.findViewById(R.id.root).setOnClickListener(view1 -> getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, NoteDetailsFragment.newInstance(note))
                    .addToBackStack("note")
                    .commit());

            TextView titleNote = itemView.findViewById(R.id.title);

            titleNote.setText(note.getTitle());

            container.addView(itemView);

        }

        Button addButton = view.findViewById(R.id.add);
        addButton.setOnClickListener(view12 -> Toast.makeText(requireContext(), "добавить", Toast.LENGTH_SHORT).show());

        ImageButton delete = view.findViewById(R.id.delete_button);
        delete.setOnClickListener(view14 -> Toast.makeText(requireContext(), "Удалить", Toast.LENGTH_SHORT).show());
    }

}
