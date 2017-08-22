package com.belspec.app.ui.owner_data;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.belspec.app.R;
import com.belspec.app.interfaces.MyCallback;
import com.belspec.app.interfaces.ResponseListener;
import com.belspec.app.retrofit.Api;
import com.belspec.app.retrofit.RetrofitService;
import com.belspec.app.retrofit.model.createExtradition.request.CreateExtraditionRequestEnvelope;
import com.belspec.app.retrofit.model.createExtradition.response.CreateExtraditionResponseEnvelope;
import com.belspec.app.retrofit.model.getCarOnEvacuation.response.EvacuationData;
import com.belspec.app.utils.Converter;
import com.belspec.app.utils.Encode;
import com.belspec.app.utils.FileManager;
import com.belspec.app.utils.UserManager;
import com.belspec.app.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Response;

public class OwnerDataActivity extends AppCompatActivity implements View.OnClickListener, ResponseListener {
    TextView txvManufacture;
    TextView txvModel;
    TextView txvCarId;
    EditText edtSeriesRC;
    EditText edtNumberRC;
    EditText edtAddressRC;
    EditText edtLastNameRC;
    EditText edtSeriesDL;
    EditText edtNumberDL;
    EditText edtAddressDL;
    EditText edtLastNameDL;
    EditText edtContact;
    EditText edtCode;
    ScrollView svMain;
    CheckBox chbPrintPhoto;
    LinearLayout llBottomContent;
    TextInputLayout tilSeriesRC;
    TextInputLayout tilNumberRC;
    TextInputLayout tilAddressRC;
    TextInputLayout tilLastNameRC;
    TextInputLayout tilSeriesDL;
    TextInputLayout tilNumberDL;
    TextInputLayout tilAddressDL;
    TextInputLayout tilLastNameDL;
    ImageView imvPhoto;
    ImageView imvLoading;
    Button btnCopy;
    Button btnRegister;
    EvacuationData evacuationData;

    public static final String EVACUATION_DATA = "EVACUATION_DATA";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extradition_activity);
        initViews();
        initComponents(savedInstanceState);
    }

    private void initViews(){
        svMain = (ScrollView)findViewById(R.id.svMain);
        txvManufacture = (TextView) findViewById(R.id.txvManufacture);
        txvManufacture.setSelected(true);
        txvModel = (TextView)findViewById(R.id.txvModel);
        txvModel.setSelected(true);
        txvCarId = (TextView)findViewById(R.id.txvCarId);
        txvCarId.setSelected(true);
        llBottomContent = (LinearLayout)findViewById(R.id.llBottomContainer);
        chbPrintPhoto = (CheckBox)findViewById(R.id.chbPrintPhoto);
        edtSeriesRC = (EditText)findViewById(R.id.edtSeriesRC);
        edtNumberRC = (EditText)findViewById(R.id.edtNumberRC);
        edtAddressRC = (EditText) findViewById(R.id.edtAddressRC);
        edtLastNameRC = (EditText)findViewById(R.id.edtLastNameRC);
        edtSeriesDL = (EditText)findViewById(R.id.edtSeriesDL);
        edtNumberDL = (EditText)findViewById(R.id.edtNumberDL);
        edtAddressDL = (EditText)findViewById(R.id.edtAddressDL);
        edtLastNameDL = (EditText)findViewById(R.id.edtLastNameDL);
        edtContact = (EditText)findViewById(R.id.edtContact);
        edtCode = (EditText)findViewById(R.id.edtCode);
        tilSeriesDL = (TextInputLayout)findViewById(R.id.tilSeriesDL);
        tilSeriesDL.setErrorEnabled(false);
        tilNumberDL = (TextInputLayout)findViewById(R.id.tilNumberDL);
        tilNumberDL.setErrorEnabled(false);
        tilLastNameDL = (TextInputLayout)findViewById(R.id.tilLastNameDL);
        tilLastNameDL.setErrorEnabled(false);
        tilAddressDL = (TextInputLayout)findViewById(R.id.tilAddressDL);
        tilAddressDL.setErrorEnabled(false);
        tilSeriesRC = (TextInputLayout)findViewById(R.id.tilSeriesRC);
        tilSeriesRC.setErrorEnabled(false);
        tilNumberRC = (TextInputLayout)findViewById(R.id.tilNumberRC);
        tilNumberRC.setErrorEnabled(false);
        tilLastNameRC = (TextInputLayout)findViewById(R.id.tilLastNameRC);
        tilLastNameRC.setErrorEnabled(false);
        tilAddressRC = (TextInputLayout)findViewById(R.id.tilAddressRC);
        tilAddressRC.setErrorEnabled(false);
        imvPhoto = (ImageView)findViewById(R.id.imvPhoto);
        imvLoading = (ImageView) findViewById(R.id.imvLoading);
        btnCopy = (Button)findViewById(R.id.btnCopy);
        btnCopy.setOnClickListener(this);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        btnCopy.setOnClickListener(this);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setLoading(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initComponents(Bundle bundle){
        evacuationData = getIntent().getParcelableExtra(EVACUATION_DATA);
        txvModel.setText(evacuationData.getModel());
        txvManufacture.setText(evacuationData.getManufacture());
        txvCarId.setText(evacuationData.getCarId());
        imvPhoto.setImageBitmap(Converter.getBitmapFromBase64Stirng(this, evacuationData.getPhoto1()));


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRegister:
                Utils.hideKeyboard(this);
                if(confirmData()){
                    setLoading(true);
                    RetrofitService createDataRetrofit = Api.createRetrofitService();
                    MyCallback<CreateExtraditionResponseEnvelope> createDataCall = new MyCallback<>();
                    createDataCall.addResponseListener(this);
                    String pre = Utils.random(4);
                    String post = Utils.random(2);
                    String code = Converter.encodeToBase64(pre + edtCode.getText().toString() + post);
                    createDataRetrofit.executeCreateExtradition( Encode.getBasicAuthTemplate(
                            UserManager.getInstanse().getmLogin(),
                            UserManager.getInstanse().getmPassword()
                    ),
                            new CreateExtraditionRequestEnvelope(evacuationData.getId(), edtSeriesRC.getText().toString(), edtNumberRC.getText().toString(), edtLastNameRC.getText().toString(), edtAddressRC.getText().toString(),
                                                                edtSeriesDL.getText().toString(), edtNumberDL.getText().toString(), edtLastNameDL.getText().toString(), edtAddressDL.getText().toString(), edtContact.getText().toString(),
                                                                code, UserManager.getInstanse().getUserType(), UserManager.getInstanse().getmLogin(), chbPrintPhoto.isChecked())
                    ).enqueue(createDataCall);
                }
                break;

            case R.id.btnCopy:
                edtLastNameDL.setText(edtLastNameRC.getText().toString());
                edtAddressDL.setText(edtAddressRC.getText().toString());
                break;

        }

    }

    private boolean confirmData(){
        if(edtSeriesRC.getText().toString().equals("")||edtNumberRC.getText().toString().equals("")||edtLastNameRC.getText().toString().equals("")||edtAddressRC.getText().toString().equals("")||
           edtSeriesDL.getText().toString().equals("")||edtNumberDL.getText().toString().equals("")||edtLastNameDL.getText().toString().equals("")||edtAddressDL.getText().toString().equals("")){

            if(edtSeriesDL.getText().toString().equals("")){
                tilSeriesDL.setErrorEnabled(true);
                tilSeriesDL.setError("Input series of driver's license");
            }
            if(edtNumberDL.getText().toString().equals("")){
                tilNumberDL.setErrorEnabled(true);
                tilNumberDL.setError("Input number of driver's license");
            }
            if(edtLastNameDL.getText().toString().equals("")){
                tilLastNameDL.setErrorEnabled(true);
                tilLastNameDL.setError("Input first and last name of driver's license");
            }
            if(edtAddressDL.getText().toString().equals("")){
                tilAddressDL.setErrorEnabled(true);
                tilAddressDL.setError("Input address of driver's license");
            }
            if(edtSeriesRC.getText().toString().equals("")){
                tilSeriesRC.setErrorEnabled(true);
                tilSeriesRC.setError("Input series of registration certificate");
            }
            if(edtNumberRC.getText().toString().equals("")){
                tilNumberRC.setErrorEnabled(true);
                tilNumberRC.setError("Input number of registration certificate");
            }
            if(edtLastNameRC.getText().toString().equals("")){
                tilLastNameRC.setErrorEnabled(true);
                tilLastNameRC.setError("Input first and last name of registration certificate");
            }
            if(edtAddressRC.getText().toString().equals("")){
                tilAddressRC.setErrorEnabled(true);
                tilAddressRC.setError("Input address of registration certificate");
            }

            return false;
        }
        tilSeriesDL.setErrorEnabled(false);
        tilNumberDL.setErrorEnabled(false);
        tilLastNameDL.setErrorEnabled(false);
        tilAddressDL.setErrorEnabled(false);
        tilSeriesRC.setErrorEnabled(false);
        tilNumberRC.setErrorEnabled(false);
        tilLastNameRC.setErrorEnabled(false);
        tilAddressRC.setErrorEnabled(false);
        return true;
    }

    private void setLoading(Boolean bool) {
        AnimationDrawable animation = (AnimationDrawable) imvLoading.getBackground();
        if (bool) {
            imvLoading.setVisibility(View.VISIBLE);
            animation.start();
            svMain.setVisibility(View.GONE);
            llBottomContent.setVisibility(View.GONE);

        } else {
            imvLoading.setVisibility(View.GONE);
            animation.stop();
            svMain.setVisibility(View.VISIBLE);
            llBottomContent.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void AuthorizationOK(Response response) {
        if (response.body().getClass() == CreateExtraditionResponseEnvelope.class) {
            CreateExtraditionResponseEnvelope responseEnvelope = (CreateExtraditionResponseEnvelope) response.body();
            if (responseEnvelope != null) {
                if (responseEnvelope.getData().getCode() == 1) {
                    setLoading(false);
                    Utils.showMessage(this, "Съем зарегистрирован");
                    try {
                        File tmpFile = FileManager.createPdfFile(this);
                        FileOutputStream fos=new FileOutputStream(tmpFile);

                        fos.write(Base64.decode(responseEnvelope.getData().getDescription(), Base64.DEFAULT));
                        fos.close();

                        Uri path = Uri.fromFile(tmpFile);
                        Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                        pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        pdfOpenintent.setDataAndType(path, "application/pdf");
                        try {
                            startActivity(pdfOpenintent);
                        }
                        catch (ActivityNotFoundException e) {
                            Utils.showMessage(this, "Установите программу для просмотра Pdf файлов");
                        }
                    } catch (IOException e) {

                    }
                    finish();

                } else {
                    setLoading(false);
                    Utils.showMessage(this, "Ошибка. Код ошибки: " + responseEnvelope.getData().getCode() + " Описание ошибки: " + responseEnvelope.getData().getDescription());
                }
            } else {
                setLoading(false);
                Utils.showMessage(this, "Неожиданно пустой ответ от сервера. Повторите попытку.");
            }
        }
    }

    @Override
    public void AuthorizationBad(Response response) {
        Utils.showMessage(this, "Wrong authorization. Response code: " + response.code() + " " + response.message());
        setLoading(false);
    }

    @Override
    public void AuthorizationFail(Throwable t) {
        Utils.showMessage(this, "Network fail. " + t.getMessage());
        setLoading(false);
    }
}
