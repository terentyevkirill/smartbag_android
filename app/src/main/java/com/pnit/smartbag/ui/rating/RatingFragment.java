package com.pnit.smartbag.ui.rating;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pnit.smartbag.R;
import com.pnit.smartbag.data.user.model.User;
import com.pnit.smartbag.ui.settings.SettingsViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RatingFragment extends Fragment {

    @BindView(R.id.rating_rv)
    RecyclerView recyclerView;

    private RatingViewModel ratingViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ratingViewModel =
                ViewModelProviders.of(this).get(RatingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rating, container, false);
        //final TextView textView = root.findViewById(R.id.text_notifications);

        ButterKnife.bind(this, root);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RatingUserAdapter adapter = new RatingUserAdapter(new ArrayList<User>(), getContext());
        recyclerView.setAdapter(adapter);

        return root;
    }

}