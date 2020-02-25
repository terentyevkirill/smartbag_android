package com.pnit.smartbag.ui.statistics;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.pnit.smartbag.MainActivity;
import com.pnit.smartbag.R;

public class StatisticsFragment extends Fragment {

    private StatisticsViewModel statisticsViewModel;

    Button calcButton;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        statisticsViewModel = ViewModelProviders.of(this, new StatisticsViewModel.Factory(getContext())).get(StatisticsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_statistics, container, false);
        /*EditText bmi = root.findViewById(R.id.bmi);
        EditText height = root.findViewById(R.id.number_hight);
        EditText weight = root.findViewById(R.id.number_weight);
        EditText userName = root.findViewById(R.id.display_name);
        TextView userNameText = root.findViewById(R.id.text_userName);*/

        String loggedInUserName = statisticsViewModel.getUserName((MainActivity)getActivity());
        /*if (loggedInUserName== null) {
            userName.setVisibility(View.INVISIBLE);
            userNameText.setVisibility(View.INVISIBLE);
        } else
            userName.setText(loggedInUserName);

        weight.setText(String.valueOf(statisticsViewModel.getWeight()));
        height.setText(String.valueOf(statisticsViewModel.getHeight()));

        calcButton = root.findViewById(R.id.calc);
        calcButton.setOnClickListener(v -> bmi.setText(statisticsViewModel.getBMIcalc(Float.valueOf(height.getText().toString()), Float.valueOf(weight.getText().toString()))));
*/
        BarChart barChart = root.findViewById(R.id.barchart);
        barChart.setDescription(null);

        BarData data = new BarData(statisticsViewModel.labels(), statisticsViewModel.barDataSet(statisticsViewModel.entities()));
        barChart.setData(data);

        Legend legend = barChart.getLegend();
//        legend.setEnabled(false);

        barChart.getAxisLeft().setAxisMinValue(0);
        barChart.getAxisRight().setAxisMinValue(0);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        LimitLine limitLine = new LimitLine(statisticsViewModel.getUserGoal());
        limitLine.setLineColor(Color.GREEN);
        limitLine.setTextSize(14);
        limitLine.setLabel("Goal");
        limitLine.setTextColor(Color.GREEN);

        YAxis yAxis = barChart.getAxisRight();
        yAxis.addLimitLine(limitLine);

        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                // TODO: show calories for chosen column
            }

            @Override
            public void onNothingSelected() {

            }
        });





        return root;
    }
}