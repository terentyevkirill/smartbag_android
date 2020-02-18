package com.pnit.smartbag.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pnit.smartbag.R;
import com.pnit.smartbag.database.user.User;
import com.pnit.smartbag.database.user.UserViewModel;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
    @BindView(R.id.btn_demo)
    Button demoButton;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        weekdayTextView.setText(homeViewModel.getFormattedData("EEEE"));
        dateTextView.setText(homeViewModel.getFormattedData("MMMM, dd"));
        goalProgressBar.setMax(homeViewModel.getGoal());

        homeViewModel.getText().observe(this, s -> {
            stepsTextView.setText(s);
        });

        goalProgressBar.setProgress(goalProgressBar.getProgress() + 1);
        demoButton.setOnClickListener(v -> homeViewModel.newStep());

        return root;
    }
}