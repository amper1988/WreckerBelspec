package com.belspec.app.ui.control;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

interface ControlContract {
    interface View{
        FragmentManager getAppFragmentManager();
        void close();
        void logout();
        void startPolicemanDialog();
        void setNavigationData(String login, String userType, String organization, String fullName);
        void setPagerAdapter(FragmentPagerAdapter adapter);
        void showMessage(String message);

    }
    interface Presenter{
        void onCreate(Bundle savedInstanceState);
        void onResume();
        void onCloseClick();
        void onRefreshClick();
        void onAddPolicemanClick();
        void onLogout();
    }
}
