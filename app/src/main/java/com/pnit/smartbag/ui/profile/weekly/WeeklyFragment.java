package com.pnit.smartbag.ui.profile.weekly;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.pnit.smartbag.R;
import com.pnit.smartbag.utils.BarChartUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class WeeklyFragment extends Fragment {

    private WeeklyViewModel weeklyViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        weeklyViewModel = ViewModelProviders.of(this, new WeeklyViewModel.Factory(getContext())).get(WeeklyViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile_weekly, container, false);

        //setup BarChart weekly
        BarChart barChart = root.findViewById(R.id.barchart);
        barChart.setDescription(null);
        barChart.setData(weeklyViewModel.getBarData());

        //settings of BarChart like legend and axis settings
        barChart.getLegend().setEnabled(false);
        barChart.getAxisLeft().setAxisMinValue(0);
        barChart.getAxisRight().setAxisMinValue(0);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getAxisRight().addLimitLine(BarChartUtils.getLimitLine(weeklyViewModel.getUserGoal(), "Goal"));

        return root;
    }
}
