package com.pnit.smartbag.ui.profile;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.pnit.smartbag.MainActivity;
import com.pnit.smartbag.R;
import com.pnit.smartbag.R2;
import com.pnit.smartbag.ui.home.HomeFragment;
import com.pnit.smartbag.ui.home.HomeViewModel;

import java.util.ArrayList;

import static com.pnit.smartbag.MainActivity.getLoggedInUser;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    Button calcButton;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel = ViewModelProviders.of(this, new ProfileViewModel.Factory(getContext())).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        /*EditText bmi = root.findViewById(R.id.bmi);
        EditText height = root.findViewById(R.id.number_hight);
        EditText weight = root.findViewById(R.id.number_weight);
        EditText userName = root.findViewById(R.id.display_name);
        TextView userNameText = root.findViewById(R.id.text_userName);*/

        String loggedInUserName = profileViewModel.getUserName((MainActivity)getActivity());
        /*if (loggedInUserName== null) {
            userName.setVisibility(View.INVISIBLE);
            userNameText.setVisibility(View.INVISIBLE);
        } else
            userName.setText(loggedInUserName);

        weight.setText(String.valueOf(profileViewModel.getWeight()));
        height.setText(String.valueOf(profileViewModel.getHeight()));

        calcButton = root.findViewById(R.id.calc);
        calcButton.setOnClickListener(v -> bmi.setText(profileViewModel.getBMIcalc(Float.valueOf(height.getText().toString()), Float.valueOf(weight.getText().toString()))));
*/
        BarChart barChart = root.findViewById(R.id.barchart);
        barChart.setDescription(null);

        BarData data = new BarData(profileViewModel.labels(),profileViewModel.barDataSet(profileViewModel.entities()));
        barChart.setData(data);

        Legend legend = barChart.getLegend();
//        legend.setEnabled(false);

        barChart.getAxisLeft().setAxisMinValue(0);
        barChart.getAxisRight().setAxisMinValue(0);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        LimitLine limitLine = new LimitLine(profileViewModel.getUserGoal());
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