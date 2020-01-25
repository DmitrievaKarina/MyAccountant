package com.qwhiteorangeofficial.myaccountant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity
        (tableName = "results", indices = {@Index(value = "result_day_date_entity", unique = true)})
public class ResultDay {

    @PrimaryKey
    @NonNull
    public Long result_day_date_entity;

    Float result_day_income_entity;

    Float result_day_expense_entity;

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }
}
