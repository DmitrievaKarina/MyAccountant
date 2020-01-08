package com.qwhiteorangeofficial.myaccountant;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity
        (tableName = "category", indices = {@Index(value = "category_name_entity", unique = true)})
public class Category {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo (name = "category_id_entity", index = true)
    Long category_id_entity;

    @NonNull
    public String category_name_entity;

    @NonNull
    String category_debit_credit_entity;
}
