package com.belspec.app.ui.control.create_policeman_dialog;

import android.content.Context;

import java.util.List;

interface CreatePolicemanContract {
    interface View{
        Context getContext();
        void setRanks(List<String> ranksList);
        void setPosition(List<String> positionList);
        void setPoliceDepartment(List<String> policeDepartmentList);
        void showMessage(String message);
        void showDialogMessage(String message);
        void close();
        void setLoading(boolean bool);
        void setErrorRanks(String message);
        void setErrorPosition(String message);
        void setErrorPoliceDepartment(String message);
        void setErrorName(String message);
        void setErrorCode(String message);
        String getPoliceDepartment();
        String getPosition();
        String getRank();
        String getName();
        String getCode();

    }
    interface Presenter{
        void onCreateDialog();
        void onDestroy();
        void onRegisterClick();
    }
}
