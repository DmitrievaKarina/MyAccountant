package com.qwhiteorangeofficial.pocketbudjet.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.qwhiteorangeofficial.pocketbudjet.Entity.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note")
    List<Note> getAllNotes();

    @Query("SELECT * FROM note WHERE  note_date = :select_date")
    List<Note> getItemsByDate(Long select_date);

    @Query("SELECT * FROM note WHERE  category_id_of_note = :id")
    List<Note> getItemsByIdOfCat(Long id);

    @Query("SELECT * FROM note WHERE  note_id = :mId")
    Note getItemById(Long mId);

    @Query("SELECT category_debit_credit_entity FROM category WHERE category_id_entity = :mId")
    String getTypeByName(Long mId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note mNote);

    @Update
    void update(Note mNote);

    @Delete
    void delete(Note mNote);
}
