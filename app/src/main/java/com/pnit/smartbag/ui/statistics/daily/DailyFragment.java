package com.pnit.smartbag.ui.statistics.daily;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.pnit.smartbag.R;
import com.pnit.smartbag.utils.BarChartUtils;

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

        return root;
    }
}
