package com.pnit.smartbag.database.activity;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ActivityViewModel extends AndroidViewModel {

    private ActivityRepository repository;
    private LiveData<List<Activity>> allActivities;
    private MutableLiveData<List<Activity>> searchResults;

    public ActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new ActivityRepository(application);
        allActivities = repository.getAllUsers();
        searchResults = repository.getSearchResults();
    }

    MutableLiveData<List<Activity>> getSearchResults() {return searchResults;}
    LiveData<List<Activity>> getAllActivities() {return allActivities;}
    public void insertActivity(Activity activity) {repository.insertActivity(activity);}
    public void findActivity(String id){repository.findActivity(id);}
    public void deleteActivity(String id){repository.deleteActivity(id);}
}
