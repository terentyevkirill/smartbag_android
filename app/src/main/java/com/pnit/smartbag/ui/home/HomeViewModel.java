package com.pnit.smartbag.ui.home;

import android.content.Context;

import com.pnit.smartbag.data.activity.ActivityRepository;
import com.pnit.smartbag.data.user.UserRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeViewModel extends ViewModel {

    //Access to local database via repositories
    private ActivityRepository activityRepo;
    private UserRepository userRepo;

    private MutableLiveData<String> liveDataSteps; //Live Data for number of steps
    private int currentSteps = 0; //Variable for representing steps as int transferred via bluetooth

    private final int GOAL_DAILY_STEPS = 10000; //when data from user is not correct, use this constant


    public HomeViewModel(Context context) {
        activityRepo = new ActivityRepository(context);
        userRepo = new UserRepository(context);

        liveDataSteps = new MutableLiveData<>();
        liveDataSteps.setValue(String.valueOf(currentSteps));
    }

    public LiveData<String> getSteps() {
        return liveDataSteps;
    }

    public int getGoal(){
        //if information about user possible, then users preferred goal
        return GOAL_DAILY_STEPS;
    }

    //data from demo Button
    public void newStep(){
        currentSteps++;
        liveDataSteps.setValue(String.valueOf(currentSteps));
    }

    //using bluetooth and amount of steps
    public void setSteps(int steps){
        currentSteps += steps;
        liveDataSteps.setValue(String.valueOf(currentSteps));
    }

    public String getFormattedData(String pattern) {
        return new SimpleDateFormat(pattern, Locale.US).format(new Date());
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final Context ctxt;

        Factory(Context ctxt) {
            this.ctxt=ctxt.getApplicationContext();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return((T)new HomeViewModel(ctxt));
        }
    }
}