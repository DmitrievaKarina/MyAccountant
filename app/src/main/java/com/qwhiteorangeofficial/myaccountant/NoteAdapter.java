package com.qwhiteorangeofficial.myaccountant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> noteList;

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
        Note mNote = noteList.get(position);
        holder.mName.setText(mNote.name_of_note);
        holder.mAmount.setText(String.valueOf(mNote.sum));
//        holder.mCategory.setText(String.valueOf(mNote.category_id_of_note));
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
        }
    }
}
