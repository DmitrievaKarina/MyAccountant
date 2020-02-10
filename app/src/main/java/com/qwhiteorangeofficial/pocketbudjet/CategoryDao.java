package com.qwhiteorangeofficial.pocketbudjet;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * from category")
    List<Category> getAllCategories();

    @Query("SELECT category_name_entity FROM category")
    List<String> getAllCategoriesInText();

    @Query("SELECT * FROM category WHERE category_id_entity = :mId")
    Category mCategory(Long mId);

    @Query("SELECT category_id_entity FROM category WHERE category_name_entity = :mName")
    Long getIdByName(String mName);

    @Query("SELECT category_debit_credit_entity FROM category WHERE category_id_entity = :mId")
    String getTypeById(Long mId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Category category);

    @Update
    void update(Category category);

    @Delete
    void delete(Category category);
}
