package com.pnit.smartbag.ui.rating;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pnit.smartbag.R;
import com.pnit.smartbag.ui.settings.SettingsViewModel;

public class RatingFragment extends Fragment {

    private RatingViewModel ratingViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ratingViewModel =
                ViewModelProviders.of(this).get(RatingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rating, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        ratingViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

}
