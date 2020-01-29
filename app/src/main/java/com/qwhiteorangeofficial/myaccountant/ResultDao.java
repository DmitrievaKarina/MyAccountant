package com.qwhiteorangeofficial.myaccountant;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ResultDao {
    @Query("SELECT * from results")
    List<ResultDay> getAllResults();

    @Query("SELECT * FROM results WHERE result_day_date_entity = :mDate LIMIT 1")
    ResultDay getObjectByDate(Long mDate);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ResultDay result);

    @Update
    void update(ResultDay result);

    @Delete
    void delete(ResultDay result);
}
