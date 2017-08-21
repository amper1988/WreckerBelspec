package com.belspec.app.ui.control;


import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

interface ControlContract {
    interface View{
        void initialize();
        Context getBaseContext();
        void close();
        void logout();
        void showMessage(String message);
        void startPolicemanDialog();
        void setNavigationData(String login, String userType, String organization, String fullName);
        void setPagerAdapter(FragmentPagerAdapter adapter);

    }
    interface Presenter{
        void onCreate(Bundle savedInstanceState);
        void initializeViewPager(FragmentManager manager);
        void onCloseClick();
        void onRefreshClick();
        void onAddPolicemanClick();
        void onLogout();
        void onResume();
        void onBackPressed();
    }
}
