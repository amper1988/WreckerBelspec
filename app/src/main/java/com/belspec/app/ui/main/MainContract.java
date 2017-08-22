package com.belspec.app.ui.main;

import android.content.Context;


interface MainContract {
    interface View {
        void showMessage(String message);
        void setLoading(boolean loading);
        void afterLogin();
    }
    interface Presenter {
        void login(String login, String password);
        void startGPSTracker(Context context);
        void stopGPSTracker();
    }
}
