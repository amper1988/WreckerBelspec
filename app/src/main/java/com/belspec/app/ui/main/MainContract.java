package com.belspec.app.ui.main;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;


interface MainContract {
    interface View {
        Context getContext();
        void showMessage(String message);
        void setLoading(boolean loading, String status);
        void afterLogin();
        void setName(String name);
        String getName();
        void setPassword(String password);
        String getPassword();
        void installApkFromUri(Uri uri);
    }
    interface Presenter {
        void login(String version);
        void onCreate(Bundle bundle);
        void onResume();
        void onPause();
    }
}
