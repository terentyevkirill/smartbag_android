package com.pnit.smartbag.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.pnit.smartbag.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        final TextView text_weekday = root.findViewById(R.id.text_weekday);
        final TextView text_date = root.findViewById(R.id.text_date);

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        text_weekday.setText(new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()));
        text_date.setText(new SimpleDateFormat("MMMM, dd", Locale.ENGLISH).format(date.getTime()));

        final ProgressBar pb_goal = root.findViewById(R.id.pb_goal);
        pb_goal.setMax(homeViewModel.getGoal());

        final TextView text_steps = root.findViewById(R.id.text_steps);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                text_steps.setText(s);
                pb_goal.setProgress(pb_goal.getProgress() + 1);
            }
        });

        final Button btn_demo = root.findViewById(R.id.btn_demo);
        btn_demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.newStep();
            }
        });
        return root;
    }
}