package com.belspec.app.ui.owner_data;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Base64;

import com.belspec.app.retrofit.Api;
import com.belspec.app.retrofit.RetrofitService;
import com.belspec.app.retrofit.model.DriverData;
import com.belspec.app.retrofit.model.OwnerData;
import com.belspec.app.retrofit.model.createExtradition.request.CreateExtraditionRequestEnvelope;
import com.belspec.app.retrofit.model.createExtradition.response.CreateExtraditionResponseEnvelope;
import com.belspec.app.retrofit.model.getDriverData.request.GetDriverDataRequestEnvelope;
import com.belspec.app.retrofit.model.getDriverData.response.GetDriverDataResponseEnvelope;
import com.belspec.app.retrofit.model.getOwnerData.request.GetOwnerDataRequestEnvelope;
import com.belspec.app.retrofit.model.getOwnerData.response.GetOwnerDataResponseEnvelope;
import com.belspec.app.utils.Converter;
import com.belspec.app.utils.Encode;
import com.belspec.app.utils.FileManager;
import com.belspec.app.utils.UserManager;
import com.belspec.app.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerDataPresenter implements OwnerDataContract.Presenter {
    private OwnerDataContract.View view;
    private Context context;
    private List<OwnerData> ownerDataList = null;
    private List<DriverData> driverDataList = null;
    private boolean ownerDataReceive;
    private boolean driverDataReceive;

    OwnerDataPresenter(OwnerDataContract.View view) {
        this.view = view;
        this.context = view.getContext();
    }


    @Override
    public void onCreate() {
        view.setLoading(true);
        Api.createRetrofitService().executeGetOwnerData(
                Encode.getBasicAuthTemplate(
                        UserManager.getInstanse().getmLogin(),
                        UserManager.getInstanse().getmPassword()),
                new GetOwnerDataRequestEnvelope(view.getCarId())
        ).enqueue(new Callback<GetOwnerDataResponseEnvelope>() {
            @Override
            public void onResponse(Call<GetOwnerDataResponseEnvelope> call, Response<GetOwnerDataResponseEnvelope> response) {
                if (response.code() == 200) {
                    ownerDataList = response.body().getBody().getOwnerDataItems();
                } else {
                    view.showMessage(response.message());
                }
                ownerDataReceive = true;
                showOwnerDriverData();
            }

            @Override
            public void onFailure(Call<GetOwnerDataResponseEnvelope> call, Throwable t) {
                ownerDataReceive = true;
                showOwnerDriverData();
            }
        });
        Api.createRetrofitService().executeGetDriverData(
                Encode.getBasicAuthTemplate(
                        UserManager.getInstanse().getmLogin(),
                        UserManager.getInstanse().getmPassword()
                ),
                new GetDriverDataRequestEnvelope(view.getCarId())
        ).enqueue(new Callback<GetDriverDataResponseEnvelope>() {
            @Override
            public void onResponse(Call<GetDriverDataResponseEnvelope> call, Response<GetDriverDataResponseEnvelope> response) {
                if (response.code() == 200) {
                    driverDataList = response.body().getBody().getDriverDataItems();
                } else
                    view.showMessage(response.message());
                driverDataReceive = true;
                showOwnerDriverData();
            }

            @Override
            public void onFailure(Call<GetDriverDataResponseEnvelope> call, Throwable t) {
                driverDataReceive = true;
                showOwnerDriverData();
            }
        });
    }

    private void showOwnerDriverData() {
        if (driverDataReceive && ownerDataReceive) {
            view.setLoading(false);
            if (driverDataList != null) {
                if (driverDataList.size() == 1) {
                    showDriverData(driverDataList.get(0));
                } else if (driverDataList.size() > 0) {
                    ArrayList<String> array = new ArrayList<>();
                    array.add("");
                    for (DriverData item : driverDataList) {
                        array.add(item.getDriverLicenseNumber() + " " + item.getDriverLicenseSeries());
                    }
                    view.setDrivers(array);
                }
            }
            if (ownerDataList != null) {
                if (ownerDataList.size() == 1) {
                    showOwnerData(ownerDataList.get(0));
                } else if (ownerDataList.size() > 0) {
                    ArrayList<String> array = new ArrayList<>();
                    array.add("");
                    for (OwnerData item : ownerDataList) {
                        array.add(item.getTechCertSeries() + " " + item.getTechCertNumber());
                    }
                    view.setOwners(array);
                }
            }
        }
    }

    @Override
    public void onRegisterClick() {
        view.setLoading(true);
        RetrofitService createDataRetrofit = Api.createRetrofitService();
        String pre = Utils.random(4);
        String post = Utils.random(2);
        String code = Converter.encodeToBase64(pre + view.getCode() + post);
        createDataRetrofit.executeCreateExtradition(Encode.getBasicAuthTemplate(
                UserManager.getInstanse().getmLogin(),
                UserManager.getInstanse().getmPassword()
                ),
                new CreateExtraditionRequestEnvelope(view.getDocId(), view.getSeriesRC(), view.getNumberRC(), view.getNameRC(), view.getAddressRC(),
                        view.getSeriesDL(), view.getNumberDL(), view.getNameDL(), view.getAddressDL(), view.getContact(),
                        code, UserManager.getInstanse().getUserType(), UserManager.getInstanse().getmLogin(), view.getPrintPhoto())
        ).enqueue(new Callback<CreateExtraditionResponseEnvelope>() {
            @Override
            public void onResponse(Call<CreateExtraditionResponseEnvelope> call, Response<CreateExtraditionResponseEnvelope> response) {
                view.setLoading(false);
                if (response.code() == 200) {
                    CreateExtraditionResponseEnvelope responseEnvelope = response.body();
                    if (responseEnvelope != null) {
                        if (responseEnvelope.getData().getCode() == 1) {
                            view.showMessage("Съем " + view.getManufacture() + " " + view.getCarId() + " зарегистрирован");
                            EventBus.getDefault().post(new OwnerDataSendEvent(view.getDocId()));
                            try {
                                File tmpFile = FileManager.createPdfFile(view.getContext());
                                FileOutputStream fos = new FileOutputStream(tmpFile);

                                fos.write(Base64.decode(response.body().getData().getDescription(), Base64.DEFAULT));
                                fos.close();
                                Uri path = Uri.fromFile(tmpFile);
                                Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                                pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                pdfOpenintent.setDataAndType(path, "application/pdf");
                                try {
                                    view.showActivity(pdfOpenintent);
                                } catch (ActivityNotFoundException e) {
                                    view.showMessage("Установите программу для просмотра Pdf файлов");
                                }
                                view.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.showMessage("Ошибка. Код ошибки: " + responseEnvelope.getData().getCode() + " Описание ошибки: " + responseEnvelope.getData().getDescription());
                        }
                    } else {
                        view.showMessage("Неожиданно пустой ответ от сервера. Повторите попытку.");
                    }
                } else {
                    view.showMessage("Ошбика сервера, код: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CreateExtraditionResponseEnvelope> call, Throwable t) {
                view.setLoading(false);
                view.showMessage("Ошибка отправления запроса " + t.getMessage());
            }
        });
    }

    @Override
    public void onDestroy() {
        view.setLoading(false);
        EventBus.getDefault().post(new OwnerDataSendEvent(-1));
    }

    @Override
    public void onOwnerSelected(int position) {
        int index = position - 1;
        if (index >= 0 && index < ownerDataList.size()) {
            showOwnerData(ownerDataList.get(index));
        }
    }

    @Override
    public void onDriverSelected(int position) {
        int index = position - 1;
        if (index >= 0 && index < driverDataList.size()) {
            showDriverData(driverDataList.get(index));
        }
    }

    private void showOwnerData(OwnerData ownerData) {
        if (ownerData != null) {
            view.setNameRC(ownerData.getOwnerName());
            view.setAddressRC(ownerData.getOwnerAddress());
            view.setSeriesRC(ownerData.getTechCertSeries());
            view.setNumberRC(ownerData.getTechCertNumber());
        }
    }

    private void showDriverData(DriverData driverData) {
        if (driverData != null) {
            view.setNameDL(driverData.getDriverName());
            view.setAddressDL(driverData.getDriverAddress());
            view.setSeriesDL(driverData.getDriverLicenseSeries());
            view.setNumberDL(driverData.getDriverLicenseNumber());
            view.setContact(driverData.getDriverContact());
        }
    }


}
