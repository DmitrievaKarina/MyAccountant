package com.qwhiteorangeofficial.myaccountant;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity
//        (tableName = "category", indices = {@Index(value = "category_name", unique = true)})
public class Category {
    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo (name = "category_id", index = true)
    public Long category_id;

    @NonNull
    String category_name;

    @NonNull
    String debit_credit;
}
