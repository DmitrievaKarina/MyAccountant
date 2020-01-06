package com.qwhiteorangeofficial.myaccountant;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> noteList;

    private int currentPosition;
    private long currentId;
    private NoteViewHolder mNoteViewHolder;

    NoteAdapter(List<Note> mList) {
        this.noteList = mList;
    }

    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note, parent,false);
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

    class NoteViewHolder extends RecyclerView.ViewHolder
    {
        private TextView mName;
        private TextView mCategory;
        private TextView mAmount;

        NoteViewHolder(View view){
            super(view);
            mName = view.findViewById(R.id.name_of_note);
            mCategory = view.findViewById(R.id.category);
            mAmount = view.findViewById(R.id.amount);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Context context = v.getContext();
//                    Intent intent = new Intent(context, AddNoteActivity.class);
//                    intent.putExtra("Name", noteList.get(getAdapterPosition()).name_of_note);
//                    intent.putExtra("Date", noteList.get(getAdapterPosition()).note_date);
//                    intent.putExtra("Id", noteList.get(getAdapterPosition()).note_id);
//                    context.startActivity(intent);
//                }
//            });
//            mNoteViewHolder = this;
//            currentPosition = this.getAdapterPosition();
//            currentId = noteList.get(currentPosition+1).note_id;
        }

        public void bind(final Note note) {
            mName.setText(note.name_of_note);
            mAmount.setText(String.valueOf(note.sum));
//        holder.mCategory.setText(String.valueOf(mNote.category_id_of_note));
        }
    }
}
