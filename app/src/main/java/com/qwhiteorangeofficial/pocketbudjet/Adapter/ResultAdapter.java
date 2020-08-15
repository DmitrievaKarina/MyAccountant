package com.qwhiteorangeofficial.pocketbudjet.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qwhiteorangeofficial.pocketbudjet.R;
import com.qwhiteorangeofficial.pocketbudjet.Entity.ResultDay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {

    private List<ResultDay> list;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

    public ResultAdapter(List<ResultDay> mList) {
        this.list = mList;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.results, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ResultViewHolder extends RecyclerView.ViewHolder {
        private TextView mDate;
        private TextView mIncome;
        private TextView mExpense;

        ResultViewHolder(final View view) {
            super(view);
            mDate = view.findViewById(R.id.day_results);
            mIncome = view.findViewById(R.id.income_results);
            mExpense = view.findViewById(R.id.expense_results);
        }

        void bind(final ResultDay category) {
            Date date = new Date(category.result_day_date_entity);
            mDate.setText(dateFormat.format(date));
            mIncome.setText(String.valueOf(category.result_day_income_entity));
            mExpense.setText(String.valueOf(category.result_day_expense_entity));
        }
    }
}
