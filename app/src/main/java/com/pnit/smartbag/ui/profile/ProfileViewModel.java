package com.pnit.smartbag.ui.profile;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pnit.smartbag.R;

public class ProfileViewModel extends ViewModel {

    public ProfileViewModel() {
    }

    public String BMIcalc (Float hight, Float weight){
        Float bmicalc = (weight/(hight*hight))*10000;
        return bmicalc.toString();
    }

    public String getBMIcalc(Float hight, Float weight){return BMIcalc(hight, weight); }

    /*public LiveData<String> getText() {
        return mText;
    }*/
}