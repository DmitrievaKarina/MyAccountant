package com.qwhiteorangeofficial.pocketbudjet.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qwhiteorangeofficial.pocketbudjet.Database.AppDatabase;
import com.qwhiteorangeofficial.pocketbudjet.Entity.Note;
import com.qwhiteorangeofficial.pocketbudjet.R;
import com.qwhiteorangeofficial.pocketbudjet.Activity.AddNoteActivity;

import java.util.List;
import java.util.Objects;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> noteList;

    public NoteAdapter(List<Note> mList) {
        this.noteList = mList;
    }

    @NonNull
    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note, parent, false);
        return new NoteAdapter.NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteAdapter.NoteViewHolder holder, int position) {
        holder.bind(noteList.get(position));
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView mCategory;
        private TextView mAmount;

        NoteViewHolder(View view) {
            super(view);
            mName = view.findViewById(R.id.name_of_note);
            mCategory = view.findViewById(R.id.category);
            mAmount = view.findViewById(R.id.amount);

            itemView.setOnClickListener(v -> {
                Context context = v.getContext();
                Intent intent = new Intent(context, AddNoteActivity.class);
                intent.putExtra("Name", noteList.get(getAdapterPosition()).name_of_note);
                intent.putExtra("Category", noteList.get(getAdapterPosition()).category_id_of_note);
                intent.putExtra("Time", noteList.get(getAdapterPosition()).note_date);
                intent.putExtra("Id", noteList.get(getAdapterPosition()).note_id);
                intent.putExtra("Sum", noteList.get(getAdapterPosition()).sum);
                context.startActivity(intent);
            });

        }

        void bind(final Note note) {
            try {
                mName.setText(note.name_of_note);
                mAmount.setText(String.valueOf(note.sum));
                mCategory.setText(AppDatabase.getInstance(itemView.getContext()).catDao().getCategoryById(note.category_id_of_note).category_name_entity);
            } catch (Exception e) {
                Log.e("NoteAdapter exception", Objects.requireNonNull(e.getMessage()));
            }
        }

    }
}
