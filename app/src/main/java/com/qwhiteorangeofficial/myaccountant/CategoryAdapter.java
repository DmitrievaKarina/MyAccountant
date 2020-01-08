package com.qwhiteorangeofficial.myaccountant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categList;

    CategoryViewHolder mCategoryViewHolder;

    CategoryAdapter(List<Category> mList) {
        this.categList = mList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category, parent,false);
        mCategoryViewHolder = new CategoryViewHolder(view);
        return mCategoryViewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        holder.bind(categList.get(position));
    }

    @Override
    public int getItemCount() {
        return categList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder
    {
        private TextView mName;
        private TextView mCategory;
        private TextView mId;

        CategoryViewHolder(final View view) {
            super(view);
            mName = view.findViewById(R.id.name_of_category);
            mCategory = view.findViewById(R.id.debit_credit_of_category);
            mId = view.findViewById(R.id.id_of_category);


            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    PopupMenu popup = new PopupMenu(v.getContext(), v);
                    popup.inflate(R.menu.menu_context_category_list);
                    final Context mContext = view.getContext();
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            AppDatabase.getInstance(mContext).catDao().delete(categList.get(getAdapterPosition()));
                            return true;
                        }
                    });
                    popup.show();
                    return true;
                }
            });

        }

        void bind(final Category category){
            mName.setText(category.category_name_entity);
            mCategory.setText(category.category_debit_credit_entity);
            mId.setText(category.category_id_entity.toString());
        }
    }
}
