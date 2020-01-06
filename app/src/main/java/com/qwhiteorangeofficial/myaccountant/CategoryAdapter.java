package com.qwhiteorangeofficial.myaccountant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        Category mCat = categList.get(position);
        holder.mName.setText(mCat.category_name);
    }

    @Override
    public int getItemCount() {
        return categList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder
    {
        private TextView mName;

        CategoryViewHolder(View view){
            super(view);
            mName = view.findViewById(R.id.name_of_category);
        }
    }
}
