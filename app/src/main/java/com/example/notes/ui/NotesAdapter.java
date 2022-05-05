package com.example.notes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.domain.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    private final List<Note> data = new ArrayList<>();

    public void setData(List<Note> notes) {
        data.addAll(notes);
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

    interface OnNoteClicked {
        void onNoteClicked(Note note);
    }

    public OnNoteClicked getNoteClicked() {
        return noteClicked;
    }

    public void setNoteClicked(OnNoteClicked noteClicked) {
        this.noteClicked = noteClicked;
    }

    private OnNoteClicked noteClicked;

    class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView titleNote;
        TextView date;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            titleNote = itemView.findViewById(R.id.title_note);
            date = itemView.findViewById(R.id.date);

            itemView.findViewById(R.id.root).setOnClickListener(view -> {
                if (noteClicked != null) {
                    int clickedPosition = getAdapterPosition();
                    noteClicked.onNoteClicked(data.get(clickedPosition));
                }
            });
        }
    }
}


