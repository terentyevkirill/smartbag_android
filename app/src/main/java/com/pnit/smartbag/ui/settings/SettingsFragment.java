package com.pnit.smartbag.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.pnit.smartbag.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsFragment extends Fragment {

    @BindView(R.id.test_switch)
    Switch testSwitch;
    @BindView(R.id.testTextView)
    TextView test_tv;
    @BindView(R.id.logoutLayout)
    ConstraintLayout logoutLayout;
    @BindView(R.id.logoutString)
    TextView logoutString;

    private SettingsViewModel settingsViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, root);

        testSwitch.setChecked(settingsViewModel.getSwitchState());
        test_tv.setText(settingsViewModel.getTestString());
        logoutLayout.setOnClickListener(v -> logoutString.setText("Test if clickable layout works"));
        logoutString.setText(settingsViewModel.getLogout());
        return root;
    }

}