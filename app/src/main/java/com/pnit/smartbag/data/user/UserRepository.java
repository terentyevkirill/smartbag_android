package com.pnit.smartbag.data.user;

import android.content.Context;
import android.os.AsyncTask;

import com.pnit.smartbag.data.AppDatabase;
import com.pnit.smartbag.data.user.model.User;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UserRepository {
    private MutableLiveData<List<User>> searchResults = new MutableLiveData<>();
    private UserDAO userDAO;
    private LiveData<List<User>> allUsers;
    private Executor executor;

    public UserRepository(Context context) {
        AppDatabase db;
        db = AppDatabase.getInstance(context);
        userDAO = db.userDao();
        allUsers = userDAO.getAllUsers();
        executor = Executors.newSingleThreadExecutor();
    }

    public void insertUser(User newUser) {
        executor.execute(() -> {
            userDAO.insert(newUser);
        });
    }

    public void deleteUser(String name) {
        executor.execute(() -> {
            userDAO.delete(name);
        });
    }

    public User findUser(String name) {
        return userDAO.findByName(name);
    }

    public User findUserWithoutRegistration() {
        return userDAO.loadUserWithoutRegistration();
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public MutableLiveData<List<User>> getSearchResults() {
        return searchResults;
    }

}
