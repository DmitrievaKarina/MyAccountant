package com.qwhiteorangeofficial.pocketbudjet.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
        (tableName = "note")
public class Note {
    @PrimaryKey(autoGenerate = true)
    public Long note_id;

    @NonNull
    public Long note_date;

    @NonNull
    public String name_of_note;

    @NonNull
    @ForeignKey(entity = CategoryEntity.class, parentColumns = "category_id_entity",
            childColumns = "category_id_of_note")
    public Long category_id_of_note;

    @NonNull
    public Float sum;
}
