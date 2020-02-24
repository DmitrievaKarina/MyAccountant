package com.qwhiteorangeofficial.pocketbudjet;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.sql.Array;
import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * from category ORDER BY category_name_entity")
    List<Category> getAllCategories();

    @Query("SELECT category_name_entity FROM category")
    List<String> getAllCategoriesInText();

    @Query("SELECT category_name_entity FROM category")
    List<String> getAllCategoriesAsMassiv();

    @Query("SELECT * FROM category WHERE category_id_entity = :mId")
    Category getCategoryById(Long mId);

    @Query("SELECT category_id_entity FROM category WHERE category_name_entity = :mName")
    Long getIdByName(String mName);

    @Query("SELECT category_debit_credit_entity FROM category WHERE category_id_entity = :mId")
    String getTypeById(Long mId);

    @Query("SELECT category_name_entity FROM category WHERE category_id_entity = :mId")
    String getNameById(Long mId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Category category);

    @Update
    void update(Category category);

    @Delete
    void delete(Category category);
}
