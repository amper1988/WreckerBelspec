package com.belspec.app.ui.extradition_fragment;


import android.support.v7.widget.RecyclerView;

import com.belspec.app.retrofit.model.getCarOnEvacuation.response.EvacuationData;

import java.util.List;

interface ExtraditionContract {
    interface View{
        void setAdapter(RecyclerView.Adapter adapter);
        void setLoading(boolean bool);
        void setPolicemans(List<String> policemans);
        void setWreckers(List<String> wreckers);
        void setOrganizations(List<String> organizaiton);
        void setPoliceDepartments(List<String> policeDepartments);
        void setPoliceDepartment(String policeDepartment);
        void setOrganization(String organization);
        void setPoliceman(String policeman);
        void setWrecker(String wrecker);
        String getOrganization();
        String getPoliceDepartment();
        String getWrecker();
        String getPoliceman();
        String getCarId();
        void showMessage(String message);
    }

    interface Presenter{
        void onCreate();
        void onDestroy();
        void onFindClick();
        int getUserType();
        void getPolicemans(String policeDepartment);
        void getWreckers(String organization);
        String getManufactureFromAdapter(int position);
        String getModelFromAdapter(int position);
        String getCarIdFromAdapter(int position);
        String getPhotoFromAdapter(int position);
        int getDocIdFromAdapter(int position);
        void onOwnerDataStart();
    }
}
