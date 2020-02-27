package com.pnit.smartbag.ui.settings;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.pnit.smartbag.MainActivity;
import com.pnit.smartbag.data.activity.ActivityRepository;
import com.pnit.smartbag.data.calories.CalorieCalculator;
import com.pnit.smartbag.data.user.UserRepository;
import com.pnit.smartbag.data.user.model.User;
import com.pnit.smartbag.ui.home.HomeViewModel;

import java.util.Calendar;
import java.util.Date;

public class SettingsViewModel extends ViewModel {

    User user;
    private UserRepository userRepo;
    private ActivityRepository activityRepo;

    public SettingsViewModel() {
    }

    private String BMIcalc(Float hight, Float weight) {
        Float bmicalc = (weight / (hight * hight)) * 10000;
        return bmicalc.toString();
    }

    String getBMIcalc(Float hight, Float weight) {
        return BMIcalc(hight, weight);
    }

    String getUserName(MainActivity main) {
        if (main.getLoggedInUser() == null)
            return null;
        else
            return main.getLoggedInUser().getDisplayName();
    }

    public int getWeight() {
        return (int)user.getWeight();
    }
    //TODO height/weight/Goal in Datenbank speichern

    public int getHeight() {
        return (int)user.getHeight();
    }

    public int getGoal(){
        try{
            return user.getDailyGoal();
        } catch (Exception e){
            e.printStackTrace();
        }
        //if information about user possible, then users preferred goal
        return 10000;
    }
    public void setGoal(int goal){
        user.setDailyGoal(goal);
        //TODO UserRepository.update()
    }

    Calendar calendar = Calendar.getInstance();
    Date date = calendar.getTime();
    int addBonus = 0;

    public int getBonus(){
        int x = 1;
        while(x<activityRepo.getStepsOfDay(date)){
            x+=3000;
            addBonus++;
        }
        return addBonus - 1;
    }



    public SettingsViewModel(Context context) {
        userRepo = new UserRepository(context);
        activityRepo = new ActivityRepository(context);

        if (MainActivity.getLoggedInUser() == null){
            user = userRepo.findUserWithoutRegistration();
        } else {
        }
    }
    public static class Factory implements ViewModelProvider.Factory {
        private final Context ctxt;


        Factory(Context ctxt) {
            this.ctxt=ctxt.getApplicationContext();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return((T)new SettingsViewModel(ctxt));
        }
    }


}