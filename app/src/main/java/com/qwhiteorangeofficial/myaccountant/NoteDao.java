package com.qwhiteorangeofficial.myaccountant;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note")
    List<Note> getAllNotes();

    @Query("SELECT * FROM note WHERE  note_date = :select_date")
    List<Note> getItemsByDate(Long select_date);

    @Query("SELECT category_debit_credit_entity FROM category WHERE category_id_entity = :mId")
    String getTypeByName(Long mId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note mNote);

    @Update
    void update(Note mNote);

    @Delete
    void delete(Note mNote);
}
