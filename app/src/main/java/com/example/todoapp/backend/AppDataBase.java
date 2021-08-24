package com.example.todoapp.backend;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = ToDo.class, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase INSTANCE;

    public abstract AppDao mDao();

    public static synchronized AppDataBase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDataBase.class, "app_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
