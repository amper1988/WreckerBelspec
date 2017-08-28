package com.belspec.app.ui.main;

import android.location.Location;
import android.os.Bundle;

import com.belspec.app.gps.GPSTracker;
import com.belspec.app.retrofit.Api;
import com.belspec.app.retrofit.RetrofitService;
import com.belspec.app.retrofit.model.test.request.TestRequestEnvelope;
import com.belspec.app.retrofit.model.test.response.TestResponseEnvelope;
import com.belspec.app.utils.AppHolder;
import com.belspec.app.utils.Encode;
import com.belspec.app.utils.UserManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class MainPresenter implements MainContract.Presenter, GPSTracker.LocationDataChangeListener{
    private GPSTracker gpsTracker;
    private MainContract.View view;

    MainPresenter(MainContract.View view){
        this.view = view;
        AppHolder.getInstance().setmContext(view.getContext());
    }

    @Override
    public void login() {
        String login = view.getName();
        String password = view.getPassword();
        UserManager.getInstanse().setmLogin(login);
        UserManager.getInstanse().setmPassword(password);
        view.setLoading(true);
        RetrofitService retrofit = Api.createRetrofitService();
        retrofit.executeTestOperation(Encode.getBasicAuthTemplate(login, password), new TestRequestEnvelope())
                .enqueue(new Callback<TestResponseEnvelope>() {
                    @Override
                    public void onResponse(Call<TestResponseEnvelope> call, Response<TestResponseEnvelope> response) {
                        view.setLoading(false);
                        if(response.code() == 200){
                            view.showMessage("Через мгновение все начнется...");
                            UserManager user = UserManager.getInstanse();
                            TestResponseEnvelope responseEnvelope = response.body();
                            user.setUserData(user.getmLogin(), user.getmPassword(), responseEnvelope.getTestData().getFullName(), responseEnvelope.getTestData().getUserType(), true, responseEnvelope.getTestData().getOrganization());
                            view.afterLogin();
                        }else{
                            UserManager.getInstanse().logout();
                            view.showMessage("Код сервера " + response.code() + ": " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<TestResponseEnvelope> call, Throwable t) {
                        view.setLoading(false);
                        UserManager.getInstanse().logout();
                        view.showMessage(t.getMessage());
                    }
                });
    }

    @Override
    public void onCreate(Bundle bundle) {
        view.setName(UserManager.getInstanse().getmLogin());
        view.setPassword(UserManager.getInstanse().getmPassword());
    }

    @Override
    public void onResume() {
        if(gpsTracker == null)
            gpsTracker = GPSTracker.getInstance();
        if(gpsTracker.canGetLocation())
            gpsTracker.startUsingGPS();
        else
            gpsTracker.showSettingsAlert();
        gpsTracker.setDataChangeListener(this);
    }

    @Override
    public void onPause() {
        if(gpsTracker != null){
            gpsTracker.unsetDataChangeListener(this);
        }
    }

    @Override
    public void onLocationDataChange(String locationAction, Location location) {
        if(locationAction.equals(GPSTracker.PROVIDER_DISABLED)){
            if(!gpsTracker.canGetLocation())
                gpsTracker.showSettingsAlert();
        }
    }
}
