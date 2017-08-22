package com.belspec.app.ui.control.create_policeman_dialog;

import android.content.Context;

import java.util.List;

interface CreatePolicemanContract {
    interface View{
        Context getContext();
        void onRanksReceive(List<String> ranksList);
        void onPositionReceive(List<String> positionList);
        void onPoliceDepartmentReceive(List<String> policeDepartmentList);
        void showMessage(String message);
        void showDialogMessage(String message);
        void close();
        void setLoading(boolean bool);
        String getPoliceDepartment();
        String getPosition();
        String getRank();
        String getName();
        String getCode();

    }
    interface Presenter{
        void onCreateDialog();
        void onStart();
        void onStop();
        void onDestroy();
        void onRegisterClick();
    }
}
