package com.pnit.smartbag.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {

    public static final String TEST_STRING = "This is a test switch";
    public static final String LOGOUT = "Log out";
    private boolean switchState = false;


    public boolean getSwitchState(){
        return switchState;
    }

    public String getTestString(){
        return TEST_STRING;
    }

    public String getLogout(){return LOGOUT;}

    public void logout(){
        // TODO a proper loguot method
    }

}