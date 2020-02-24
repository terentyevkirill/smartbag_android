package com.pnit.smartbag.ui.profile;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.pnit.smartbag.MainActivity;
import com.pnit.smartbag.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ProfileViewModel extends ViewModel {

    public ProfileViewModel() {
    }

    public String BMIcalc (Float hight, Float weight){
        Float bmicalc = (weight/(hight*hight))*10000;
        return bmicalc.toString();
    }

    public String getBMIcalc(Float hight, Float weight){return BMIcalc(hight, weight); }

    public String getUserName(MainActivity main){
        if (main.getLoggedInUser() == null)
            return null;
        else
            return main.getLoggedInUser().getDisplayName();
    }
    public int getWeight(){
        return 0;
    }
    public int getHeight(){
        return 0;
    }

    //barChart
    int [] steps = {5000,8000,5678,11000,3000,100,3099};

    public ArrayList <BarEntry> entities (){
        ArrayList<BarEntry> entries = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int stepsPerDay=0;
        for (int i = 0; i<day; i++){
            if (stepsPerDay >= steps.length){
                entries.add(new BarEntry(0, i));
            }
            else
                entries.add(new BarEntry(steps[stepsPerDay], i));
            stepsPerDay++;

        }
        return entries;
    }

    class MyBarDataSet extends BarDataSet {

        public MyBarDataSet(ArrayList entries, String label) {
            super(entries, label);
        }

        @Override
        public int getColor(int index) {
            if(getEntryForXIndex(index).getVal() > 10000)
                return mColors.get(0);
            /*else if(getEntryForXIndex(index).getVal() < 5000) // less than 100 orange
                return mColors.get(1);*/
            else // greater or equal than 100 red
                return mColors.get(2);
        }
    }
    public BarDataSet barDataSet(ArrayList entries){

        MyBarDataSet bardataset = new MyBarDataSet(entries,"Steps");
        bardataset.setColors(ColorTemplate.JOYFUL_COLORS);
        return bardataset;
    }
    public ArrayList<String> labels (){
        ArrayList<String> labels = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        int month = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i=1; i<=month; i++){
            labels.add(Integer.toString(i));
        }

        return labels;
    }
}