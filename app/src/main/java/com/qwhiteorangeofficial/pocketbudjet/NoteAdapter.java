package com.qwhiteorangeofficial.pocketbudjet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        private Long mId;

        NoteViewHolder(View view){
            super(view);
            mName = view.findViewById(R.id.name_of_note);
            mCategory = view.findViewById(R.id.category);
            mAmount = view.findViewById(R.id.amount);
//            mId = noteList.get(getAdapterPosition()).note_id;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, AddNoteActivity.class);
                    intent.putExtra("Name", noteList.get(getAdapterPosition()).name_of_note);
                    intent.putExtra("Category", noteList.get(getAdapterPosition()).category_id_of_note);
                    intent.putExtra("Time", noteList.get(getAdapterPosition()).note_date);
                    intent.putExtra("Id", noteList.get(getAdapterPosition()).note_id);
                    intent.putExtra("Sum", noteList.get(getAdapterPosition()).sum);
                    context.startActivity(intent);
                }
            });
            mNoteViewHolder = this;
            currentPosition = this.getAdapterPosition();
            currentId = noteList.get(currentPosition+1).note_id;

//            view.setOnLongClickListener(new View.OnLongClickListener(){
//                @Override
//                public boolean onLongClick(View v) {
//                    PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
//                    popupMenu.inflate(R.menu.menu_context_note_list);
//                    final Context mContext = v.getContext();
//                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                        @Override
//                        public boolean onMenuItemClick(MenuItem item) {
//                            Long date = noteList.get(getAdapterPosition()).note_date;
//                            AppDatabase.getInstance(mContext).noteDao().delete(noteList.get(getAdapterPosition()));
//                            recountResultsInDay(mContext, date);
//                            return true;
//                        }
//                    });
//                    popupMenu.show();
//                    return true;
//                }
//            });
        }

        public void bind(final Note note) {
            mName.setText(note.name_of_note);
            mAmount.setText(String.valueOf(note.sum));
            mCategory.setText(AppDatabase.getInstance(itemView.getContext()).catDao().mCategory(note.category_id_of_note).category_name_entity);
        }

    }
}