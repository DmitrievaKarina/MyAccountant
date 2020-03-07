package com.qwhiteorangeofficial.pocketbudjet.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity
        (tableName = "category", indices = {@Index(value = "category_name_entity", unique = true)})
public class CategoryEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id_entity", index = true)
    public Long category_id_entity;

    @NonNull
    public String category_name_entity;

    @NonNull
    public String category_debit_credit_entity;
}
