package com.pnit.smartbag.ui.statistics;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.pnit.smartbag.data.activity.ActivityRepository;
import com.pnit.smartbag.data.user.UserRepository;
import com.pnit.smartbag.data.user.model.User;

public class StatisticsViewModel extends ViewModel {

    private ActivityRepository activityRepository;
    private User user;
    private Context context;

    public StatisticsViewModel(Context context) {
        activityRepository = new ActivityRepository(context);
        UserRepository userRepository = new UserRepository(context);
        user = userRepository.findUserWithoutRegistration();
        this.context = context;
    }


    public static class Factory implements ViewModelProvider.Factory {
        private final Context ctxt;

        Factory(Context ctxt) {
            this.ctxt = ctxt.getApplicationContext();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return ((T) new StatisticsViewModel(ctxt));
        }
    }
}