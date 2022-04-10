package com.example.notes.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notes.R;
import com.example.notes.domain.InMemoryNotesRepository;
import com.example.notes.domain.Note;

import java.util.List;

public class NotesListFragment extends Fragment {

    static final String NOTE_CLICKED_KEY = "NOTE_CLICKED_KEY";
    static final String SELECTED_NOTE = "SELECTED_NOTE";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Note> notes = InMemoryNotesRepository.getInstance(requireContext()).getAll();

        LinearLayout container = view.findViewById(R.id.container);

        for (Note note : notes) {
            View itemView = getLayoutInflater().inflate(R.layout.item_note, container, false);

            itemView.findViewById(R.id.root).setOnClickListener(view1 -> {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(SELECTED_NOTE, note);
                    getParentFragmentManager()
                            .setFragmentResult(NOTE_CLICKED_KEY, bundle);
                } else NoteDetailsActivity.show(requireContext(), note);
            });

            TextView titleNote = itemView.findViewById(R.id.title);
            TextView detailNote = itemView.findViewById(R.id.detail);

            titleNote.setText(note.getTitle());
            detailNote.setText(note.getDetail());

            container.addView(itemView);
        }

    }
}
