package com.pnit.smartbag.database.user;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UserViewModel extends AndroidViewModel {

    private UserRepository repository;
    private LiveData<List<User>> allUsers;
    private MutableLiveData<List<User>> searchResults;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        allUsers = repository.getAllUsers();
        searchResults = repository.getSearchResults();
    }

    MutableLiveData<List<User>> getSearchResults() {
        return searchResults;
    }

    LiveData<List<User>> getAllProducts() {
        return allUsers;
    }

    public void insertUser(User user) {
        repository.insertUser(user);
    }

    public User findUser(String name) {
        return repository.findUser(name);
    }

    public void deleteUser(String name) {
        repository.deleteUser(name);
    }
}
