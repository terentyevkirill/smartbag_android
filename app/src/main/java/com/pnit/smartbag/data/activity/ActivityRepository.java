package com.pnit.smartbag.data.activity;

import android.content.Context;
import android.os.AsyncTask;

import com.pnit.smartbag.Converters;
import com.pnit.smartbag.data.AppDatabase;
import com.pnit.smartbag.data.activity.model.Activity;
import com.pnit.smartbag.utils.DateUtil;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ActivityRepository {

    private MutableLiveData<List<Activity>> searchResults  = new MutableLiveData<List<Activity>>();
    private ActivityDAO activityDAO;
    private LiveData<List<Activity>> allActivities;
    private Executor executor;

    public ActivityRepository(Context context){
        AppDatabase db;
        db = AppDatabase.getInstance(context);
        activityDAO = db.activityDAO();
        allActivities = activityDAO.getAllActivities();
        executor = Executors.newSingleThreadExecutor();
    }

    public void insertActivity(Activity newActivity){
        executor.execute(() -> {
            activityDAO.insert(newActivity);
        });
    }

    public void deleteActivity(long id) {
        executor.execute(() -> {
            activityDAO.delete(id);
        });
    }

    public Activity getActivityById(long id) {
        return activityDAO.findById(id);
    }

    public LiveData<List<Activity>> getAllUsers() {
        return allActivities;
    }

    public MutableLiveData<List<Activity>> getSearchResults() {
        return searchResults;
    }

    public LiveData<Integer> getTodaySteps() {
        return activityDAO.getStepsForDay(Converters.dateToTimestamp(DateUtil.removeTime(new Date())));
    }

    public LiveData<Integer> getStepsOfSpecificDay(Date d){
        return activityDAO.getStepsForDay(Converters.dateToTimestamp(DateUtil.removeTime(d)));
    }

    public int getStepsOfDay(Date d){
        return activityDAO.getStepsForDayInt(Converters.dateToTimestamp(DateUtil.removeTime(d)));
    }
}
