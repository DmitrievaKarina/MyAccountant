package com.qwhiteorangeofficial.pocketbudjet.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.qwhiteorangeofficial.pocketbudjet.Dao.CategoryDao;
import com.qwhiteorangeofficial.pocketbudjet.Dao.NoteDao;
import com.qwhiteorangeofficial.pocketbudjet.Dao.ResultDao;
import com.qwhiteorangeofficial.pocketbudjet.Entity.CategoryEntity;
import com.qwhiteorangeofficial.pocketbudjet.Entity.Note;
import com.qwhiteorangeofficial.pocketbudjet.Entity.ResultDay;


@Database(entities = {Note.class, CategoryEntity.class, ResultDay.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();
    public abstract CategoryDao catDao();
    public abstract ResultDao resDao();

    private static AppDatabase database;
    final static String DB_NAME = "app_database";

    public static AppDatabase getInstance(Context context) {

        if (database == null) {

            database = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }

        return database;

    }
}
