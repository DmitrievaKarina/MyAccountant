package com.qwhiteorangeofficial.pocketbudjet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categList;

    CategoryViewHolder mCategoryViewHolder;
    private int currentPosition;
    private long currentId;

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

        CategoryViewHolder(final View view) {
            super(view);
            mName = view.findViewById(R.id.name_of_category);
            mCategory = view.findViewById(R.id.debit_credit_of_category);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, AddCategoryActivity.class);
                    intent.putExtra("Name", categList.get(getAdapterPosition()).category_name_entity);
                    intent.putExtra("Id", categList.get(getAdapterPosition()).category_id_entity);
                    intent.putExtra("Type", categList.get(getAdapterPosition()).category_debit_credit_entity);
                    context.startActivity(intent);
                }
            });
            mCategoryViewHolder = this;
            currentPosition = this.getAdapterPosition();
            currentId = categList.get(currentPosition+1).category_id_entity;

        }

        void bind(final Category category){
            mName.setText(category.category_name_entity);
            mCategory.setText(category.category_debit_credit_entity);
        }
    }
}
