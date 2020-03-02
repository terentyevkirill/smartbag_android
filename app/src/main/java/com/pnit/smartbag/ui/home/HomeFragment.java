package com.pnit.smartbag.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pnit.smartbag.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

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

        goalProgressBar.setMax(homeViewModel.getGoal());
        dailyStepsTextView.setText(String.valueOf(homeViewModel.getGoal()));

        /*homeViewModel.getSteps().observe(getViewLifecycleOwner(), s -> {
            goalProgressBar.setProgress(homeViewModel.getCurrentSteps());
            stepsTextView.setText(String.valueOf(s));
            caloriesTextView.setText(String.valueOf(homeViewModel.getCalories()));
        });*/

//        homeViewModel.getSteps().observe(getViewLifecycleOwner(), s -> {
//            if (s != null) {
//                goalProgressBar.setProgress(s);
//                stepsTextView.setText(String.valueOf(s));
//                caloriesTextView.setText(String.valueOf(homeViewModel.getCalories(s)));
//            } else {
//                goalProgressBar.setProgress(0);
//                stepsTextView.setText("0");
//                caloriesTextView.setText("0");
//            }
//        });
        demoButton.setOnClickListener(v -> {homeViewModel.setSteps(100);});
        homeViewModel.getCurrentDate().observe(getViewLifecycleOwner(), s -> {
            weekdayTextView.setText(homeViewModel.getFormattedData("EEEE"));
            dateTextView.setText(homeViewModel.getFormattedData("MMMM, dd"));
            if (DateUtils.isToday(homeViewModel.getCurrentDate().getValue().getTime())) {
                upButton.setVisibility(View.INVISIBLE);
            } else
                upButton.setVisibility(View.VISIBLE);
                homeViewModel.getSteps().observe(getViewLifecycleOwner(), d -> {
                    if (d != null) {
                        goalProgressBar.setProgress(d);
                        stepsTextView.setText(String.valueOf(d));
                        caloriesTextView.setText(String.valueOf(homeViewModel.getCalories(d)));
                    } else {
                        goalProgressBar.setProgress(0);
                        stepsTextView.setText("0");
                        caloriesTextView.setText("0");
                    }
                });
        });

        upButton.setOnClickListener(v -> homeViewModel.setDatePlus1());
        downButton.setOnClickListener(v -> homeViewModel.setDateMinus1());
        upButton.setOnLongClickListener(v -> {
            homeViewModel.resetToCurrentDate();
            return true;
        });
        return root;
    }
}