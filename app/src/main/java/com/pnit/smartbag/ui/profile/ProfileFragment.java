package com.pnit.smartbag.ui.profile;

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

import com.pnit.smartbag.R;
import com.pnit.smartbag.R2;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    Button calcButton;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        EditText bmi = root.findViewById(R.id.bmi);
        EditText hight = root.findViewById(R.id.number_hight);
        EditText weight = root.findViewById(R.id.number_weight);
        calcButton = root.findViewById(R.id.calc);
        calcButton.setOnClickListener(v -> bmi.setText(profileViewModel.getBMIcalc(Float.valueOf(hight.getText().toString()), Float.valueOf(weight.getText().toString()))));

        return root;
    }
}