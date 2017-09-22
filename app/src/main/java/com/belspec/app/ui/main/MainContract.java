package com.belspec.app.ui.main;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;


interface MainContract {
    interface View {
        Context getContext();
        void showMessage(String message);
        void setLoading(boolean loading, String status);
        void afterLogin();

        String getName();

        void setName(String name);

        String getPassword();

        void setPassword(String password);

        void installApkFromUri(Uri uri);

        void showGPSAlert();
    }
    interface Presenter {
        void login(String version);
        void onCreate(Bundle bundle);
        void onResume();
        void onPause();
    }
}
