package com.example.todoapp.backend;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ToDo {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String todo;
    public ToDo(String todo){
        this.todo = todo;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }
}
