package com.qwhiteorangeofficial.myaccountant;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class, Category.class, ResultDay.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();
    public abstract CategoryDao catDao();
    public abstract ResultDao resDao();

    public static AppDatabase database;
    final static String DB_NAME = "my_accountant_database";

    public static AppDatabase getInstance(Context context){

        if(database==null){

            database = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }

        return database;

    }
}
