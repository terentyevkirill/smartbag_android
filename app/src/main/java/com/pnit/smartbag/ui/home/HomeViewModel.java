package com.pnit.smartbag.ui.home;

import android.content.Context;

import com.pnit.smartbag.data.activity.ActivityRepository;
import com.pnit.smartbag.data.calories.CalorieCalculator;
import com.pnit.smartbag.data.user.UserRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeViewModel extends ViewModel {

    //Access to local database via repositories
    private ActivityRepository activityRepo;
    private UserRepository userRepo;

    //Access to Calorie Calculator once initialized with height and weight of user
    private CalorieCalculator calorieCalculator;

    private MutableLiveData<String> liveDataSteps; //Live Data for number of steps
    private int currentSteps = 0; //Variable for representing steps as int transferred via bluetooth
    private MutableLiveData<Date> liveDataDate;
    private Date currentDate = new Date();

    private final int GOAL_DAILY_STEPS = 10000; //when data from user is not correct, use this constant

    public HomeViewModel(Context context) {
        activityRepo = new ActivityRepository(context);
        userRepo = new UserRepository(context);
        calorieCalculator = new CalorieCalculator(150, 80);

        liveDataSteps = new MutableLiveData<>();
        liveDataSteps.setValue(String.valueOf(currentSteps));

        liveDataDate = new MutableLiveData<>();
        liveDataDate.setValue(new Date());
    }

    public void setDateMinus1(){
        currentDate = changeDate(-1, currentDate);
        liveDataDate.setValue(currentDate);
    }

    public void setDatePlus1(){
        currentDate = changeDate(1, currentDate);
        liveDataDate.setValue(currentDate);
    }

    private Date changeDate(int amount, Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, amount);
        return cal.getTime();
    }

    public LiveData<Date> getCurrentDate(){
        return liveDataDate;
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
        return new SimpleDateFormat(pattern, Locale.US).format(currentDate);
    }

    public int getCurrentSteps(){
        return currentSteps;
    }

    public int getCalories(){
       return calorieCalculator.calculateCalories(currentSteps);
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