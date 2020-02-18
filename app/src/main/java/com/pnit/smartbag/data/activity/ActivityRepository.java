package com.pnit.smartbag.data.activity;

import android.content.Context;
import android.os.AsyncTask;

import com.pnit.smartbag.data.AppDatabase;
import com.pnit.smartbag.data.activity.model.Activity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ActivityRepository {

    private MutableLiveData<List<Activity>> searchResults  = new MutableLiveData<List<Activity>>();
    private ActivityDAO activityDAO;
    private LiveData<List<Activity>> allActivities;

    public ActivityRepository(Context context){
        AppDatabase db;
        db = AppDatabase.getInstance(context);
        activityDAO = db.activityDAO();
        allActivities = activityDAO.getAllActivities();
    }

    public void insertActivity(Activity newActivity){
        InsertAsyncTask task = new InsertAsyncTask(activityDAO);
        task.execute(newActivity);
    }

    public void deleteActivity(String name) {
        DeleteAsyncTask task = new DeleteAsyncTask(activityDAO);
        task.execute(name);
    }

    public void findActivity(String name) {
        QueryAsyncTask task = new QueryAsyncTask(activityDAO);
        task.delegate = this;
        task.execute(name);
    }

    private void asyncFinished(List<Activity> results){
        searchResults.setValue(results);
    }

    public LiveData<List<Activity>> getAllUsers() {
        return allActivities;
    }

    public MutableLiveData<List<Activity>> getSearchResults() {
        return searchResults;
    }


    private static class QueryAsyncTask extends AsyncTask<String, Void, List<Activity>> {
        private ActivityDAO asyncTaskDao;
        private ActivityRepository delegate = null;
        QueryAsyncTask(ActivityDAO dao){
            asyncTaskDao = dao;
        }

        @Override
        protected List<Activity> doInBackground(final String... params){
            return asyncTaskDao.findById(params[0]);
        }

        @Override
        protected void onPostExecute(List<Activity> result){
            delegate.asyncFinished(result);
        }
    }

    private static class InsertAsyncTask extends AsyncTask<Activity, Void, Void> {

        private ActivityDAO asyncTaskDao;

        InsertAsyncTask(ActivityDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Activity... params) {
            asyncTaskDao.insertAll(params[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<String, Void, Void> {

        private ActivityDAO asyncTaskDao;

        DeleteAsyncTask(ActivityDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            asyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
