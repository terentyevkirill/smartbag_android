package com.pnit.smartbag.ui.profile.monthly;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.pnit.smartbag.R;
import com.pnit.smartbag.utils.BarChartUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class MonthlyFragment extends Fragment {

    private MonthlyViewModel monthlyViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        monthlyViewModel = ViewModelProviders.of(this, new MonthlyViewModel.Factory(getContext())).get(MonthlyViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile_monthly, container, false);

        //setup BarChart weekly
        BarChart barChart = root.findViewById(R.id.barchart);
        barChart.setDescription(null);
        barChart.setData(monthlyViewModel.getBarData());

        //settings of BarChart like legend and axis settings
        barChart.getLegend().setEnabled(false);
        barChart.getAxisLeft().setAxisMinValue(0);
        barChart.getAxisRight().setAxisMinValue(0);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getAxisRight().addLimitLine(BarChartUtils.getLimitLine(monthlyViewModel.getUserGoal(), "Goal"));

        return root;
    }
}
