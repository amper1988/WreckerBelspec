package com.belspec.app.ui.control;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.belspec.app.adapters.ViewPagerAdapter;
import com.belspec.app.ui.detection.FragmentDetection;
import com.belspec.app.ui.extradition_fragment.ExtraditionFragment;
import com.belspec.app.utils.NetworkDataManager;
import com.belspec.app.utils.UserManager;
import com.belspec.app.utils.Utils;

class ControlPresenter implements ControlContract.Presenter{
    private ControlContract.View mView;
    private Context context;

    ControlPresenter(ControlContract.View view){
        this.mView = view;
        this.context = view.getBaseContext();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (UserManager.getInstanse().ismRegistered()) {
            mView.initialize();
            String userType = "";
            switch (UserManager.getInstanse().getUserType()) {
                case 1:
                    userType = "Сотрудник ГАИ";
                    break;
                case 2:
                    userType = "Водитель эвакуатора";
                    break;
                case 3:
                    userType = "Доступ разрешен";
                    break;

            }
            mView.setNavigationData(UserManager.getInstanse().getmLogin(), userType, UserManager.getInstanse().getOrganization(), UserManager.getInstanse().getmFullName());
        } else {
            mView.logout();
        }
    }

    @Override
    public void initializeViewPager(FragmentManager manager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(manager);
        adapter.addFragment(new FragmentDetection(), "Протокол 1");
        adapter.addFragment(new FragmentDetection(), "Протокол 2");
        adapter.addFragment(new FragmentDetection(), "Протокол 3");
        adapter.addFragment(new FragmentDetection(), "Протокол 4");
        adapter.addFragment(new ExtraditionFragment(), "Съем");
        mView.setPagerAdapter(adapter);
    }

    @Override
    public void onCloseClick() {
        UserManager.getInstanse().logout();
        mView.close();
    }

    @Override
    public void onRefreshClick() {
        NetworkDataManager.getInstance().getDefaultData();
        NetworkDataManager.getInstance().getRoadLawPointFromServer();
        NetworkDataManager.getInstance().getPositionsFromServer();
        NetworkDataManager.getInstance().getRanksListFromServer();
    }

    @Override
    public void onAddPolicemanClick() {
        int userType = UserManager.getInstanse().getUserType();
        if(userType != 1){
            mView.startPolicemanDialog();
        }else{
            mView.showMessage("К сожалению, недоступно");
        }
    }

    @Override
    public void onLogout() {
        UserManager.getInstanse().logout();
        mView.logout();
    }

    @Override
    public void onResume() {
        if(!UserManager.getInstanse().ismRegistered())
            mView.logout();
    }

    @Override
    public void onBackPressed() {
        Utils.showYesNoDialog(context, "Уже закончили работу?", new Utils.DialogYesNoListener() {
            @Override
            public void onNegativePress() {

            }

            @Override
            public void onPositivePress() {
                onCloseClick();
            }
        });
    }
}
