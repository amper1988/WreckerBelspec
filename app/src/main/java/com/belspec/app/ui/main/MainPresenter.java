package com.belspec.app.ui.main;

import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;

import com.belspec.app.gps.GPSTracker;
import com.belspec.app.retrofit.Api;
import com.belspec.app.retrofit.RetrofitService;
import com.belspec.app.retrofit.model.checkUpdate.request.CheckUpdateRequestEnvelope;
import com.belspec.app.retrofit.model.checkUpdate.response.CheckUpdateResponseEnvelope;
import com.belspec.app.retrofit.model.test.request.TestRequestEnvelope;
import com.belspec.app.retrofit.model.test.response.TestResponseEnvelope;
import com.belspec.app.utils.AppHolder;
import com.belspec.app.utils.Encode;
import com.belspec.app.utils.FileManager;
import com.belspec.app.utils.UserManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
    public void login(String version) {
        view.setLoading(true, "Проверка обновления");
        Api.createRetrofitService().executeCheckUpdate(
                Encode.getBasicAuthTemplate(
                        view.getName(),
                        view.getPassword()
                ),
                new CheckUpdateRequestEnvelope(version)
        ).enqueue(new Callback<CheckUpdateResponseEnvelope>() {
            @Override
            public void onResponse(Call<CheckUpdateResponseEnvelope> call, Response<CheckUpdateResponseEnvelope> response) {
                view.setLoading(false, "Обновления получены");
                if(response.code() == 200){
                    if(response.body().getBody().getCode() == 1){
                        try {
                            File tmpFile = FileManager.createApkFile(AppHolder.getInstance().getContext());
                            FileOutputStream fos = new FileOutputStream(tmpFile);
                            fos.write(Base64.decode(response.body().getBody().getDescription(), Base64.DEFAULT));
                            fos.close();
                            Uri path = Uri.fromFile(tmpFile);
                            view.installApkFromUri(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else if(response.body().getBody().getCode() == 5){
                        login();
                    }else{
                        view.showMessage("Ошибка ответа сервера " + response.body().getBody().getCode() + ": " + response.body().getBody().getDescription());
                    }
                }else{
                    view.showMessage("Ошибка сервера " + response.code() + ": " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CheckUpdateResponseEnvelope> call, Throwable t) {
                view.setLoading(false, "Ошибка при получении обновлений");
                view.showMessage("Ошибка при отправлении запроса: " + t.getMessage());
            }
        });
    }

    private void login(){
        String login = view.getName();
        String password = view.getPassword();
        UserManager.getInstanse().setmLogin(login);
        UserManager.getInstanse().setmPassword(password);
        view.setLoading(true, "Проверка введенных данных");
        RetrofitService retrofit = Api.createRetrofitService();
        retrofit.executeTestOperation(Encode.getBasicAuthTemplate(login, password), new TestRequestEnvelope())
                .enqueue(new Callback<TestResponseEnvelope>() {
                    @Override
                    public void onResponse(Call<TestResponseEnvelope> call, Response<TestResponseEnvelope> response) {
                        view.setLoading(false, "Введенные данные проверены");
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
                        view.setLoading(false, "Ошибка при проверке введенных данных");
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
