package com.example.todoapp.backend;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AppDao {

    @Insert
    void insertToDo(ToDo item);

    @Delete
    void deleteToDo(ToDo item);

    @Update
    void updateToDo(ToDo item);

    @Query("SELECT * FROM ToDo")
    LiveData<List<ToDo>> getAllToDoItems();

}
