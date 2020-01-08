package com.qwhiteorangeofficial.myaccountant;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity
        (tableName = "note")
public class Note {
    @PrimaryKey(autoGenerate = true)
    Long note_id;

    @NonNull
    public Long note_date;

    @NonNull
    String name_of_note;

    @NonNull
    @ForeignKey(entity = Category.class, parentColumns = "category_id_entity",
            childColumns = "category_id_of_note")
    Long category_id_of_note;

    @NonNull
    Float sum;
}
