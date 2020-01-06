package com.qwhiteorangeofficial.myaccountant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categList;

    CategoryAdapter(List<Category> mList) {
        this.categList = mList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category, parent,false);
        return new CategoryViewHolder(view);
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

        CategoryViewHolder(View view){
            super(view);
            mName = view.findViewById(R.id.name_of_category);
            mCategory = view.findViewById(R.id.debit_credit_of_category);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(v.getContext(), v);
                    popup.inflate(R.menu.menu_main);
//                    popup.setOnMenuItemClickListener(CategoryViewHolder.this);
                    popup.show();
                }
            });
        }

        void bind(final Category category){
            mName.setText(category.category_name_entity);
            mCategory.setText(category.category_debit_credit_entity);
        }
    }
}
