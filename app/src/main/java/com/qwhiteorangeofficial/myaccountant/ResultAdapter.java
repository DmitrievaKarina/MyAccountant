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


public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {

    private List<ResultDay> categList;

    ResultViewHolder mCategoryViewHolder;

    ResultAdapter(List<ResultDay> mList) {
        this.categList = mList;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.results, parent,false);
        mCategoryViewHolder = new ResultViewHolder(view);
        return mCategoryViewHolder;
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int position) {
        holder.bind(categList.get(position));
    }

    @Override
    public int getItemCount() {
        return categList.size();
    }

    class ResultViewHolder extends RecyclerView.ViewHolder
    {
        private TextView mDate;
        private TextView mIncome;
        private TextView mExpense;
//        private TextView mId;

        ResultViewHolder(final View view) {
            super(view);
            mDate = view.findViewById(R.id.day_results);
            mIncome = view.findViewById(R.id.income_results);
            mExpense = view.findViewById(R.id.expense_results);
//            mId = view.findViewById(R.id.id_of_category);


        }

        void bind(final ResultDay category){
            mDate.setText(String.valueOf(category.result_day_date_entity));
            mIncome.setText(String.valueOf(category.result_day_income_entity));
            mExpense.setText(String.valueOf(category.result_day_expense_entity));
//            mId.setText(String.valueOf(category.category_id_entity));
        }
    }
}
