package com.example.todoapp.backend;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AppRepository {
    private AppDao mDao;
    private LiveData<List<ToDo>> allToDoItems;

    public AppRepository(Application application) {
        AppDataBase db = AppDataBase.getInstance(application);
        mDao = db.mDao();
        allToDoItems = mDao.getAllToDoItems();
    }

    /**
     * Return the LiveData list of all the items in the database.
     * @return LiveData<List<ToDo>
     */
    public LiveData<List<ToDo>> getAllToDoItems() {
        return allToDoItems;
    }

    /**
     * Insert a new ToDo object.
     * @param item Takes a ToDo object
     * @return returns void
     */
    public void insertToDoItem(ToDo item){
        new InsertToDoItemAsyncTask(mDao).execute(item);
    }

    /**
     * Delete a ToDo object.
     * @param item Takes a ToDo object
     * @return returns void
     */
    public void deleteToDoItem(ToDo item){
        new DeleteToDoItemAsyncTask(mDao).execute(item);
    }

    /**
     * Update a ToDo object with another instance of a ToDo object.
     * @param item Takes a ToDo object.
     * @return returns void
     */
    public void updateToDoItem(ToDo item){
        new UpdateToDoItemAsyncTask(mDao).execute(item);
    }

    private static class InsertToDoItemAsyncTask extends AsyncTask<ToDo,Void,Void>{
        private AppDao mDao;
        private InsertToDoItemAsyncTask(AppDao dao){
            mDao = dao;
        }
        @Override
        protected Void doInBackground(ToDo... toDos) {
            mDao.insertToDo(toDos[0]);
            return null;
        }
    }
    private static class DeleteToDoItemAsyncTask extends AsyncTask<ToDo,Void,Void>{
        private AppDao mDao;
        private DeleteToDoItemAsyncTask(AppDao dao){
            mDao = dao;
        }
        @Override
        protected Void doInBackground(ToDo... toDos) {
            mDao.deleteToDo(toDos[0]);
            return null;
        }
    }
    private static class UpdateToDoItemAsyncTask extends AsyncTask<ToDo,Void,Void>{
        private AppDao mDao;
        private UpdateToDoItemAsyncTask(AppDao dao){
            mDao = dao;
        }
        @Override
        protected Void doInBackground(ToDo... toDos) {
            mDao.updateToDo(toDos[0]);
            return null;
        }
    }
}
