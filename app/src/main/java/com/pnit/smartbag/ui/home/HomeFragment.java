package com.pnit.smartbag.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pnit.smartbag.R;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    @BindView(R.id.weekday_tv)
    TextView weekdayTextView;
    @BindView(R.id.date_tv)
    TextView dateTextView;
    @BindView(R.id.goal_pb)
    ProgressBar goalProgressBar;
    @BindView(R.id.steps_tv)
    TextView stepsTextView;
    @BindView(R.id.daily_steps_tv)
    TextView dailyStepsTextView;
    @BindView(R.id.btn_demo)
    Button demoButton;
    @BindView((R.id.calories_tv))
    TextView caloriesTextView;
    @BindView(R.id.down_btn)
    Button downButton;
    @BindView(R.id.up_btn)
    Button upButton;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = ViewModelProviders.of(this, new HomeViewModel.Factory(getContext())).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);

        goalProgressBar.setMax(homeViewModel.getGoal()/100); //(/100) just for demonstrating purposes
        dailyStepsTextView.setText(String.valueOf(homeViewModel.getGoal()/100));

        homeViewModel.getSteps().observe(getViewLifecycleOwner(), s -> {
            goalProgressBar.setProgress(goalProgressBar.getProgress() + 1);
            stepsTextView.setText(s);
            caloriesTextView.setText(String.valueOf(homeViewModel.getCalories()));
        });

        demoButton.setOnClickListener(v -> homeViewModel.newStep());
        homeViewModel.getCurrentDate().observe(getViewLifecycleOwner(), s ->{
            weekdayTextView.setText(homeViewModel.getFormattedData("EEEE"));
            dateTextView.setText(homeViewModel.getFormattedData("MMMM, dd"));
        });
        upButton.setOnClickListener(v -> homeViewModel.setDatePlus1());
        downButton.setOnClickListener(v -> homeViewModel.setDateMinus1());


        return root;
    }
}