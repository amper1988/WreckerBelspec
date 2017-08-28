package com.belspec.app.ui.main;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;


interface MainContract {
    interface View {
        Context getContext();
        void showMessage(String message);
        void setLoading(boolean loading);
        void afterLogin();
        void setName(String name);
        String getName();
        void setPassword(String password);
        String getPassword();
    }
    interface Presenter {
        void login();
        void onCreate(Bundle bundle);
        void onResume();
        void onPause();
    }
}
