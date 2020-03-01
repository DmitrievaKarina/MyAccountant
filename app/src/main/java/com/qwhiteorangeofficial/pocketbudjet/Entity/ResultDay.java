package com.qwhiteorangeofficial.pocketbudjet.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity
        (tableName = "results", indices = {@Index(value = "result_day_date_entity", unique = true)})
public class ResultDay {

    @PrimaryKey
    @NonNull
    public Long result_day_date_entity;

    public Float result_day_income_entity;

    public Float result_day_expense_entity;

}
