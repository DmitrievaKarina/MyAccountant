package com.qwhiteorangeofficial.myaccountant;

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

    @NonNull
    Float result_day_debit_entity;

    @NonNull
    Float result_day_credit_entity;
}
