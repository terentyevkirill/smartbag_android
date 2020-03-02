package com.pnit.smartbag.ui.statistics.daily;

import android.content.Context;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.pnit.smartbag.data.activity.ActivityRepository;
import com.pnit.smartbag.data.activity.model.Activity;
import com.pnit.smartbag.data.calories.CalorieCalculator;
import com.pnit.smartbag.data.user.UserRepository;
import com.pnit.smartbag.data.user.model.User;
import com.pnit.smartbag.utils.BarChartUtils;
import com.pnit.smartbag.utils.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class DailyViewModel extends ViewModel {

    private ActivityRepository activityRepository;
    private User user;

    //Access to Calorie Calculator once initialized with height and weight of user
    private CalorieCalculator calorieCalculator;

    private DailyViewModel(Context context) {
        activityRepository = new ActivityRepository(context);
        UserRepository userRepository = new UserRepository(context);
        user = userRepository.findUserWithoutRegistration();

        calorieCalculator = new CalorieCalculator((int)user.getHeight(), (int)user.getWeight());
    }

    BarData getBarData() {
        ArrayList<BarEntry> entries = getBarEntries();
        ArrayList<String> labels = getBarLabels();
        return new BarData(labels, barDataSet(entries));
    }

    private ArrayList<BarEntry> getBarEntries() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        Date date = new Date();
        Date x = DateUtil.removeTime(date);

        List<Activity> activityList = activityRepository.findByDate(date);
        int[] steps = new int[24];
        for (int i = 0; i < 24; i++) {
            for (Activity a : activityList) {
                if (a.getEndTime().getHours() == i) {
                    steps[i] += a.getSteps();
                }
            }
        }

        for (int i = 0; i < 24; i++) {
            entries.add(new BarEntry(steps[i], i));
        }
        return entries;
    }

    private ArrayList<String> getBarLabels() {
        ArrayList<String> labels = new ArrayList<>();
        for (int i = 0; i <= 23; i++)
            labels.add(String.valueOf(i));
        return labels;
    }

    private BarDataSet barDataSet(ArrayList<BarEntry> entries) {
        BarDataSet barDataSet = new DailyViewModel.MyBarDataSet(entries, "Steps");
        barDataSet.setColors(BarChartUtils.getColorsBarChart());
        barDataSet.setDrawValues(false);
        return barDataSet;
    }

    class MyBarDataSet extends BarDataSet {
        private MyBarDataSet(ArrayList<BarEntry> entries, String label) {
            super(entries, label);
        }

        @Override
        public int getColor(int index) {
            if (getEntryForXIndex(index).getVal() > user.getDailyGoal() / 24)
                return mColors.get(0);
            else if (getEntryForXIndex(index).getVal() > ((user.getDailyGoal() / 24) / 2))
                return mColors.get(1);
            else
                return mColors.get(2);
        }
    }

    int getUserGoal() {
        return user.getDailyGoal();
    }

    public int getCalories(int currentSteps){
        return calorieCalculator.calculateCalories(currentSteps);
    }

    public Integer getSteps(Date d) {
        return activityRepository.getStepsOfDay(d);
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final Context ctxt;

        Factory(Context ctxt) {
            this.ctxt = ctxt.getApplicationContext();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return ((T) new DailyViewModel(ctxt));
        }
    }
}
