package com.belspec.app.ui.owner_data;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Base64;

import com.belspec.app.retrofit.Api;
import com.belspec.app.retrofit.RetrofitService;
import com.belspec.app.retrofit.model.createExtradition.request.CreateExtraditionRequestEnvelope;
import com.belspec.app.retrofit.model.createExtradition.response.CreateExtraditionResponseEnvelope;
import com.belspec.app.utils.Converter;
import com.belspec.app.utils.Encode;
import com.belspec.app.utils.FileManager;
import com.belspec.app.utils.UserManager;
import com.belspec.app.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerDataPresenter implements OwnerDataContract.Presenter {
    private OwnerDataContract.View view;
    private Context context;

    OwnerDataPresenter(OwnerDataContract.View view) {
        this.view = view;
        this.context = view.getContext();
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
}
