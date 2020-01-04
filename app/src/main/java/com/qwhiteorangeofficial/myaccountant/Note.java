package com.qwhiteorangeofficial.myaccountant;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity
//        (foreignKeys = @ForeignKey(entity = Category.class, parentColumns = "category_id", childColumns = "category_id_of_note", onDelete = CASCADE, onUpdate = CASCADE))
public class Note {
    @PrimaryKey(autoGenerate = true)
    Long note_id;

    @NonNull
    Long note_date;

    @NonNull
    String name_of_note;

//    @NonNull
//    @ColumnInfo (name = "category_id_of_note", index = true)
//    Long category_id_of_note;

    @NonNull
    Float sum;
}
