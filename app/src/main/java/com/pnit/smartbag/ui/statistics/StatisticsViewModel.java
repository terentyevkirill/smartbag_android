package com.pnit.smartbag.ui.statistics;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.pnit.smartbag.MainActivity;
import com.pnit.smartbag.R;
import com.pnit.smartbag.data.activity.ActivityRepository;
import com.pnit.smartbag.data.user.UserRepository;
import com.pnit.smartbag.data.user.model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StatisticsViewModel extends ViewModel {

    ActivityRepository activityRepository;
    UserRepository userRepository;
    User user;
    private Context context;

    public StatisticsViewModel(Context context) {
        activityRepository = new ActivityRepository(context);
        userRepository = new UserRepository(context);
        user = userRepository.findUserWithoutRegistration();
        this.context = context;
    }

    public String BMIcalc(Float hight, Float weight) {
        Float bmicalc = (weight / (hight * hight)) * 10000;
        return bmicalc.toString();
    }

    public String getBMIcalc(Float hight, Float weight) {
        return BMIcalc(hight, weight);
    }

    public String getUserName(MainActivity main) {
        if (main.getLoggedInUser() == null)
            return null;
        else
            return main.getLoggedInUser().getDisplayName();
    }

    public int getWeight() {
        return 0;
    }

    public int getHeight() {
        return 0;
    }

    //barChart
    //int [] steps = {5000,8000,5678,11000,3000,100,3099};

    public ArrayList<BarEntry> entities() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        Date today = new Date();
        calendar.set(1900 + today.getYear(), today.getMonth(), 1);
        int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 0; i < day; i++) {
            Date date = calendar.getTime();
            entries.add(new BarEntry(activityRepository.getStepsOfDay(date), i));
            calendar.add(calendar.DATE, 1);
        }
        return entries;
    }

    class MyBarDataSet extends BarDataSet {

        public MyBarDataSet(ArrayList entries, String label) {
            super(entries, label);
        }

        @Override
        public int getColor(int index) {
            if (getEntryForXIndex(index).getVal() > user.getDailyGoal())
                return mColors.get(0);
            else if(getEntryForXIndex(index).getVal() > (user.getDailyGoal()/2)) // less than 100 orange
                return mColors.get(1);
            else // greater or equal than 100 red
                return mColors.get(2);
        }
    }

    public BarDataSet barDataSet(ArrayList entries) {

        MyBarDataSet bardataset = new MyBarDataSet(entries, "Steps");
        bardataset.setColors(new int[]{ContextCompat.getColor(context, R.color.green),
                ContextCompat.getColor(context, R.color.orange),
                ContextCompat.getColor(context, R.color.red)});
        //bardataset.setColors(ColorTemplate.JOYFUL_COLORS);
        //bardataset.setDrawValues(false);
        return bardataset;
    }

    public ArrayList<String> labels() {
        ArrayList<String> labels = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        int month = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= month; i++) {
            labels.add(Integer.toString(i));
        }

        return labels;
    }

    public int getUserGoal(){
        return user.getDailyGoal();
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final Context ctxt;

        Factory(Context ctxt) {
            this.ctxt = ctxt.getApplicationContext();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return ((T) new StatisticsViewModel(ctxt));
        }
    }
}