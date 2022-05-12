package com.example.notes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.domain.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private Fragment fragment;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    private final List<Note> data = new ArrayList<>();

    public void setData(Collection<Note> notes) {
        data.addAll(notes);
    }

    public NotesAdapter(Fragment fragment) {
        this.fragment = fragment;
    }



    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);

        NoteViewHolder holder = new NoteViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = data.get(position);

        holder.titleNote.setText(note.getTitle());

        holder.date.setText(simpleDateFormat.format(note.getCreatedAt()));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public int addNote(Note note) {
        data.add(note);
        return data.size() - 1;
    }

    public void removeNote(Note selectedNote) {
        data.remove(selectedNote);
    }

    public void replaceNote(Note note, int selectedPosition) {
        data.set(selectedPosition, note);
    }

    interface OnNoteClicked {
        void onNoteClicked(Note note);

        void onNoteLongClicked(Note note, int position);
    }

    private OnNoteClicked noteClicked;

    public OnNoteClicked getNoteClicked() {
        return noteClicked;
    }

    public void setNoteClicked(OnNoteClicked noteClicked) {
        this.noteClicked = noteClicked;
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView titleNote;
        TextView date;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            titleNote = itemView.findViewById(R.id.title_note);
            date = itemView.findViewById(R.id.date);

            CardView cardView = itemView.findViewById(R.id.root);

            fragment.registerForContextMenu(cardView);

            cardView.setOnClickListener(view -> {
                if (noteClicked != null) {
                    int clickedPosition = getAdapterPosition();
                    noteClicked.onNoteClicked(data.get(clickedPosition));
                }
            });

            cardView.setOnLongClickListener(view -> {
                cardView.showContextMenu();

                if (noteClicked != null) {
                    int clickedPosition = getAdapterPosition();
                    noteClicked.onNoteLongClicked(data.get(clickedPosition), clickedPosition);
                }
                return true;
            });
        }
    }
}


