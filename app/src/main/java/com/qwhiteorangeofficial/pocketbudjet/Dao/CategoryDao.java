package com.qwhiteorangeofficial.pocketbudjet.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.qwhiteorangeofficial.pocketbudjet.Entity.CategoryEntity;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * from category ORDER BY category_name_entity")
    List<CategoryEntity> getAllCategories();

    @Query("SELECT category_name_entity FROM category")
    List<String> getAllCategoriesInText();

    @Query("SELECT category_name_entity FROM category")
    List<String> getAllCategoriesAsMassiv();

    @Query("SELECT * FROM category WHERE category_id_entity = :mId")
    CategoryEntity getCategoryById(Long mId);

    @Query("SELECT category_id_entity FROM category WHERE category_name_entity = :mName")
    Long getIdByName(String mName);

    @Query("SELECT category_debit_credit_entity FROM category WHERE category_id_entity = :mId")
    String getTypeById(Long mId);

    @Query("SELECT category_name_entity FROM category WHERE category_id_entity = :mId")
    String getNameById(Long mId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CategoryEntity category);

    @Update
    void update(CategoryEntity category);

    @Delete
    void delete(CategoryEntity category);
}
