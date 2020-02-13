package com.pnit.smartbag.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private final int GOAL_DAILY_STEPS = 100; //just for demonstrating purposes
    private int currentSteps = 0;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue(String.valueOf(currentSteps));
    }

    public LiveData<String> getText() {
        return mText;
    }

    public int getGoal(){return GOAL_DAILY_STEPS;}

    public void newStep(){
        currentSteps++;
        mText.setValue(String.valueOf(currentSteps));
    }
}