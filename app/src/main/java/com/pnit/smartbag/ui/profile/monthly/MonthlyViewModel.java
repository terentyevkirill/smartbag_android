package com.pnit.smartbag.ui.profile.monthly;

import android.content.Context;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.pnit.smartbag.R;
import com.pnit.smartbag.data.activity.ActivityRepository;
import com.pnit.smartbag.data.user.UserRepository;
import com.pnit.smartbag.data.user.model.User;
import com.pnit.smartbag.ui.profile.weekly.WeeklyViewModel;
import com.pnit.smartbag.utils.BarChartUtils;
import com.pnit.smartbag.utils.DateUtil;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MonthlyViewModel extends ViewModel {

    private ActivityRepository activityRepository;
    private User user;

    private MonthlyViewModel(Context context){
        activityRepository = new ActivityRepository(context);
        UserRepository userRepository = new UserRepository(context);
        user = userRepository.findUserWithoutRegistration();
    }

    BarData getBarData(){
        ArrayList<BarEntry> entries = getBarEntries();
        ArrayList<String> labels = getBarLabels();
        return new BarData(labels, barDataSet(entries));
    }

    private ArrayList<BarEntry> getBarEntries(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1900 + new Date().getYear(), new Date().getMonth(), 1);
        int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for(int i = 0; i < day; i++){
            entries.add(new BarEntry(activityRepository.getStepsOfDay(calendar.getTime()), i));
            calendar.add(Calendar.DATE, 1);
        }
        return entries;
    }

    private ArrayList<String> getBarLabels() {
        ArrayList<String> labels = new ArrayList<String>();
        for (int i = 1; i <= Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH); i++)
            labels.add(Integer.toString(i));
        return labels;
    }

    private BarDataSet barDataSet(ArrayList<BarEntry> entries) {
        BarDataSet barDataSet = new MonthlyViewModel.MyBarDataSet(entries, "Steps");
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
            if (getEntryForXIndex(index).getVal() > user.getDailyGoal())
                return mColors.get(0);
            else if(getEntryForXIndex(index).getVal() > (user.getDailyGoal()/2))
                return mColors.get(1);
            else
                return mColors.get(2);
        }
    }

    int getUserGoal(){
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
            return ((T) new MonthlyViewModel(ctxt));
        }
    }




}
