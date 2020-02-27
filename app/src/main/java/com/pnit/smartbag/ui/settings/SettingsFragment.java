package com.pnit.smartbag.ui.settings;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.pnit.smartbag.MainActivity;
import com.pnit.smartbag.R;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    Button calcButton;
    Button saveGoalButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                ViewModelProviders.of(this, new SettingsViewModel.Factory(getContext())).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        EditText bmi = root.findViewById(R.id.bmi);
        EditText height = root.findViewById(R.id.number_hight);
        EditText weight = root.findViewById(R.id.number_weight);
        EditText userName = root.findViewById(R.id.display_name);
        TextView userNameText = root.findViewById(R.id.text_userName);

        TextView dailyGoal = root.findViewById(R.id.goal);


        String loggedInUserName = settingsViewModel.getUserName((MainActivity)getActivity());
        if (loggedInUserName== null) {
            userName.setVisibility(View.INVISIBLE);
            userNameText.setVisibility(View.INVISIBLE);
        } else
            userName.setText(loggedInUserName);

        weight.setText(String.valueOf(settingsViewModel.getWeight()));
        height.setText(String.valueOf(settingsViewModel.getHeight()));

        dailyGoal.setText(String.valueOf(settingsViewModel.getGoal()));


        calcButton = root.findViewById(R.id.calc);
        calcButton.setOnClickListener(v -> bmi.setText(settingsViewModel.getBMIcalc(Float.valueOf(height.getText().toString()), Float.valueOf(weight.getText().toString()))));


        saveGoalButton = root.findViewById(R.id.button_save_goal);
        saveGoalButton.setOnClickListener(v -> settingsViewModel.setGoal(Integer.valueOf(Integer.parseInt(dailyGoal.getText().toString()))));


        TextView bonusPoints = root.findViewById(R.id.number_points);
        bonusPoints.setText(String.valueOf(settingsViewModel.getBonus()));

        return root;
    }
}