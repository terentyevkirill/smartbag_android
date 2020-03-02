package com.pnit.smartbag.ui.home;

import android.content.Context;

import com.pnit.smartbag.MainActivity;
import com.pnit.smartbag.data.activity.ActivityRepository;
import com.pnit.smartbag.data.activity.model.Activity;
import com.pnit.smartbag.data.calories.CalorieCalculator;
import com.pnit.smartbag.data.user.UserRepository;
import com.pnit.smartbag.data.user.model.User;
import com.pnit.smartbag.utils.DateUtil;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeViewModel extends ViewModel {

    //Access to local database via repositories
    private ActivityRepository activityRepo;
    private UserRepository userRepo;

    //Access to Calorie Calculator once initialized with height and weight of user
    private CalorieCalculator calorieCalculator;

    //private MutableLiveData<String> liveDataSteps; //Live Data for number of steps
    //private int currentSteps = 0; //Variable for representing steps as int transferred via bluetooth
    private MutableLiveData<Date> liveDataDate;
    private Date currentDate = new Date();

    private final int GOAL_DAILY_STEPS = 10000; //when data from user is not correct, use this constant

    User user;

    public HomeViewModel(Context context) {
        activityRepo = new ActivityRepository(context);
        userRepo = new UserRepository(context);

        //liveDataSteps = new MutableLiveData<>();
        //liveDataSteps.setValue(String.valueOf(getCurrentSteps()));

        liveDataDate = new MutableLiveData<>();
        liveDataDate.setValue(new Date());

        if (MainActivity.getLoggedInUser() == null){
            user = userRepo.findUserWithoutRegistration();
        } else {
            //ToDO: Get User from Remote Database
        }

        calorieCalculator = new CalorieCalculator((int)user.getHeight(), (int)user.getWeight());
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

    public LiveData<Integer> getSteps() {
        return activityRepo.getStepsOfSpecificDay(currentDate);
    }

    public int getGoal(){
        try{
            return user.getDailyGoal();
        } catch (Exception e){
            e.printStackTrace();
        }
        //if information about user possible, then users preferred goal
        return GOAL_DAILY_STEPS;
    }

    //using bluetooth and amount of steps
    public void setSteps(int steps){
        /*currentSteps += steps;
        liveDataSteps.setValue(String.valueOf(currentSteps));
        User user = new User(User.DEFAULT_USER_ID, "", "", 12500, 60, 170);
        userRepo.insertUser(user);*/
        Activity activity = new Activity(Activity.DEFAULT_ACTIVITY_ID, DateUtil.removeTime(currentDate), new Date(), 100, User.DEFAULT_USER_ID);
        activityRepo.insertActivity(activity);
    }

    public String getFormattedData(String pattern) {
        return new SimpleDateFormat(pattern, Locale.US).format(currentDate);
    }


    public int getCalories(int currentSteps){
       return calorieCalculator.calculateCalories(currentSteps);
    }

    public void resetToCurrentDate() {
        currentDate = new Date();
        liveDataDate.setValue(currentDate);
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