package com.pnit.smartbag.database.user;

import android.content.Context;
import android.os.AsyncTask;

import com.pnit.smartbag.database.AppDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UserRepository {
    private MutableLiveData<List<User>> searchResults = new MutableLiveData<>();
    private UserDAO userDAO;
    private LiveData<List<User>> allUsers;

    public UserRepository(Context context) {
        AppDatabase db;
        db = AppDatabase.getInstance(context);
        userDAO = db.userDao();
        allUsers = userDAO.getAllUsers();
    }

    public void insertUser(User newUser) {
        InsertAsyncTask task = new InsertAsyncTask(userDAO);
        task.execute(newUser);
    }

    public void deleteUser(String name) {
        DeleteAsyncTask task = new DeleteAsyncTask(userDAO);
        task.execute(name);
    }

    public void findUser(String name) {
        QueryAsyncTask task = new QueryAsyncTask(userDAO);
        task.delegate = this;
        task.execute(name);
    }

    private void asyncFinished(List<User> results){
        searchResults.setValue(results);
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public MutableLiveData<List<User>> getSearchResults() {
        return searchResults;
    }

    private static class QueryAsyncTask extends AsyncTask<String, Void, List<User>>{
        private UserDAO asyncTaskDao;
        private UserRepository delegate = null;
        QueryAsyncTask(UserDAO dao){
            asyncTaskDao = dao;
        }

        @Override
        protected List<User> doInBackground(final String... params){
            return asyncTaskDao.findByName(params[0]);
        }

        @Override
        protected void onPostExecute(List<User> result){
            delegate.asyncFinished(result);
        }
    }

    private static class InsertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDAO asyncTaskDao;

        InsertAsyncTask(UserDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            asyncTaskDao.insertAll(params[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<String, Void, Void> {

        private UserDAO asyncTaskDao;

        DeleteAsyncTask(UserDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            asyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
