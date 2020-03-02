package com.pnit.smartbag.ui.statistics.daily;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.pnit.smartbag.R;
import com.pnit.smartbag.data.activity.model.Activity;
import com.pnit.smartbag.utils.BarChartUtils;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class DailyFragment extends Fragment {

    private DailyViewModel dailyViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dailyViewModel = ViewModelProviders.of(this, new DailyViewModel.Factory(getContext())).get(DailyViewModel.class);
        View root = inflater.inflate(R.layout.fragment_statistics_daily, container, false);

        //setup BarChart weekly
        BarChart barChart = root.findViewById(R.id.barchart);
        barChart.setDescription(null);
        barChart.setData(dailyViewModel.getBarData());

        //settings of BarChart like legend and axis settings
        barChart.getLegend().setEnabled(false);
        barChart.getAxisLeft().setAxisMinValue(0);
        barChart.getAxisRight().setAxisMinValue(0);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getAxisRight().addLimitLine(BarChartUtils.getLimitLine(dailyViewModel.getUserGoal() / 8, "Goal"));

        TextView caloriesTextView = root.findViewById(R.id.calories_text);
        TextView caloriesDescTextView = root.findViewById(R.id.calories_des_text);
        TextView caloriesKcalTextView = root.findViewById(R.id.calories_kcal_text);

        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                caloriesTextView.setVisibility(View.VISIBLE);
                caloriesDescTextView.setVisibility(View.VISIBLE);
                caloriesKcalTextView.setVisibility(View.VISIBLE);

                Date d = new Date();
                d.setHours(e.getXIndex());
                caloriesTextView.setText(String.valueOf(dailyViewModel.getCalories(dailyViewModel.getSteps(d))));

                caloriesTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        caloriesTextView.setVisibility(View.INVISIBLE);
                        caloriesDescTextView.setVisibility(View.INVISIBLE);
                        caloriesKcalTextView.setVisibility(View.INVISIBLE);
                    }
                }, 3000);
            }

            @Override
            public void onNothingSelected() {

            }
        });

        return root;
    }
}
