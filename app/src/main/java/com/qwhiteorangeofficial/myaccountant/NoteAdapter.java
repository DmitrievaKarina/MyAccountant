package com.qwhiteorangeofficial.myaccountant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

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

            view.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                    popupMenu.inflate(R.menu.menu_context_note_list);
                    final Context mContext = v.getContext();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            AppDatabase.getInstance(mContext).noteDao().delete(noteList.get(getAdapterPosition()));
                            return true;
                        }
                    });
                    popupMenu.show();
                    return true;
                }
            });
        }

        public void bind(final Note note) {
            mName.setText(note.name_of_note);
            mAmount.setText(String.valueOf(note.sum));
            mCategory.setText(AppDatabase.getInstance(itemView.getContext()).catDao().mCategory(note.category_id_of_note).category_name_entity);
        }
    }
}
