package com.belspec.app.ui.owner_data;

import android.content.Context;
import android.content.Intent;

import java.util.List;

interface OwnerDataContract {
    interface View{
        void setLoading(boolean bool);
        int getDocId();

        void setOwners(List<String> owners);

        void setDrivers(List<String> drivers);

        String getSeriesDL();

        void setSeriesDL(String seriesDL);

        String getSeriesRC();

        void setSeriesRC(String seriesRC);

        String getNumberDL();

        void setNumberDL(String numberDL);

        String getNumberRC();

        void setNumberRC(String numberRC);

        String getNameRC();

        void setNameRC(String nameRC);

        String getNameDL();

        void setNameDL(String nameDL);

        String getAddressRC();

        void setAddressRC(String addressRC);

        String getAddressDL();

        void setAddressDL(String addressDL);

        String getContact();

        void setContact(String contact);

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
        void onCreate();
        void onRegisterClick();
        void onDestroy();

        void onOwnerSelected(int position);

        void onDriverSelected(int position);
    }
}
