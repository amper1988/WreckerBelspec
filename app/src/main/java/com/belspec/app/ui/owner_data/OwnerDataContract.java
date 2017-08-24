package com.belspec.app.ui.owner_data;

import android.content.Context;
import android.content.Intent;

interface OwnerDataContract {
    interface View{
        void setLoading(boolean bool);
        int getDocId();
        String getSeriesDL();
        String getSeriesRC();
        String getNumberDL();
        String getNumberRC();
        String getNameRC();
        String getNameDL();
        String getAddressRC();
        String getAddressDL();
        String getContact();
        String getCode();
        boolean getPrintPhoto();
        void showMessage(String message);
        String getManufacture();
        String getCarId();
        Context getContext();
        void showActivity(Intent intent);
        void close();
    }
    interface Presenter{
        void onRegisterClick();
        void onDestroy();
    }
}
