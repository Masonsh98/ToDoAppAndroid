package com.example.todoapp.backend;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AppViewModel extends AndroidViewModel {
    private AppRepository mRepository;
    private LiveData<List<ToDo>> allTodoItems;
    public AppViewModel(@NonNull Application application) {
        super(application);
        mRepository = new AppRepository(application);
        allTodoItems = mRepository.getAllToDoItems();
    }

    /**
     * Returns LiveData list of all the ToDo objects.
     * @return LiveData<List<ToDo>>
     */
    public LiveData<List<ToDo>> getAllTodoItems() {
        return allTodoItems;
    }

    /**
     * Takes a new ToDo object and inserts into the Database.
     * @param item
     */
    public void insertToDoItem(ToDo item){
        mRepository.insertToDoItem(item);
    }

    /**
     * Deletes the ToDo object in the Database.
     * @param item
     */
    public void deleteToDoItem(ToDo item){
        mRepository.deleteToDoItem(item);
    }

    /**
     * Updates a ToDo object in the Database.
     * @param item
     */
    public void updateToDoItem(ToDo item){
        mRepository.updateToDoItem(item);
    }

}
