package com.belspec.app.ui.owner_data;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.belspec.app.utils.Converter;
import com.belspec.app.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OwnerDataActivity extends AppCompatActivity implements View.OnClickListener, OwnerDataContract.View {
    @BindView(R.id.txvManufacture) TextView txvManufacture;
    @BindView(R.id.txvModel) TextView txvModel;
    @BindView(R.id.txvCarId) TextView txvCarId;
    @BindView(R.id.edtSeriesRC) EditText edtSeriesRC;
    @BindView(R.id.edtNumberRC) EditText edtNumberRC;
    @BindView(R.id.edtAddressRC) EditText edtAddressRC;
    @BindView(R.id.edtLastNameRC) EditText edtLastNameRC;
    @BindView(R.id.edtSeriesDL) EditText edtSeriesDL;
    @BindView(R.id.edtNumberDL) EditText edtNumberDL;
    @BindView(R.id.edtAddressDL) EditText edtAddressDL;
    @BindView(R.id.edtLastNameDL) EditText edtLastNameDL;
    @BindView(R.id.edtContact) EditText edtContact;
    @BindView(R.id.edtCode) EditText edtCode;
    @BindView(R.id.svMain) ScrollView svMain;
    @BindView(R.id.chbPrintPhoto) CheckBox chbPrintPhoto;
    @BindView(R.id.llBottomContainer) LinearLayout llBottomContent;
    @BindView(R.id.tilSeriesRC) TextInputLayout tilSeriesRC;
    @BindView(R.id.tilNumberRC) TextInputLayout tilNumberRC;
    @BindView(R.id.tilAddressRC) TextInputLayout tilAddressRC;
    @BindView(R.id.tilLastNameRC) TextInputLayout tilLastNameRC;
    @BindView(R.id.tilSeriesDL) TextInputLayout tilSeriesDL;
    @BindView(R.id.tilNumberDL) TextInputLayout tilNumberDL;
    @BindView(R.id.tilAddressDL) TextInputLayout tilAddressDL;
    @BindView(R.id.tilLastNameDL) TextInputLayout tilLastNameDL;
    @BindView(R.id.tilContact) TextInputLayout tilContact;
    @BindView(R.id.imvPhoto) ImageView imvPhoto;
    @BindView(R.id.imvLoading) ImageView imvLoading;
    @BindView(R.id.btnCopy) Button btnCopy;
    @BindView(R.id.btnRegister) Button btnRegister;

    int docId;
    OwnerDataPresenter presenter;
    public static final String MANUFACTURE = "MANUFACTURE";
    public static final String MODEL = "MODEL";
    public static final String CAR_ID = "CAR_ID";
    public static final String PHOTO = "PHOTO";
    public static final String DOC_ID = "DOC_ID";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extradition_activity);
        ButterKnife.bind(this);
        presenter = new OwnerDataPresenter(this);
        initViews();
        initComponents();
    }

    private void initViews(){
        txvManufacture.setSelected(true);
        txvModel.setSelected(true);
        txvCarId.setSelected(true);
        tilSeriesDL.setErrorEnabled(false);
        tilNumberDL.setErrorEnabled(false);
        tilLastNameDL.setErrorEnabled(false);
        tilAddressDL.setErrorEnabled(false);
        tilSeriesRC.setErrorEnabled(false);
        tilNumberRC.setErrorEnabled(false);
        tilLastNameRC.setErrorEnabled(false);
        tilAddressRC.setErrorEnabled(false);
        tilContact.setErrorEnabled(false);
        btnCopy.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnCopy.setOnClickListener(this);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
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

    private void initComponents(){
        Intent intent = getIntent();
        if(intent != null){
            txvModel.setText(intent.getStringExtra(MODEL));
            txvManufacture.setText(intent.getStringExtra(MANUFACTURE));
            txvCarId.setText(intent.getStringExtra(CAR_ID));
            imvPhoto.setImageBitmap(Converter.getBitmapFromBase64Stirng(this, intent.getStringExtra(PHOTO)));
            docId = intent.getIntExtra(DOC_ID, -1);
        }else{
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRegister:
                Utils.hideKeyboard(this);
                if(confirmData()){
                    presenter.onRegisterClick();
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
           edtSeriesDL.getText().toString().equals("")||edtNumberDL.getText().toString().equals("")||edtLastNameDL.getText().toString().equals("")||edtAddressDL.getText().toString().equals("") ||
                edtContact.getText().toString().equals("")){

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
            if(edtContact.getText().toString().equals("")){
                tilContact.setErrorEnabled(true);
                tilContact.setError("Контакт должен быть заполнен");
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
        tilContact.setErrorEnabled(false);
        return true;
    }



    @Override
    public void setLoading(boolean bool) {
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
    public int getDocId() {
        return docId;
    }

    @Override
    public String getSeriesDL() {
        return edtSeriesDL.getText().toString();
    }

    @Override
    public String getSeriesRC() {
        return edtSeriesRC.getText().toString();
    }

    @Override
    public String getNumberDL() {
        return edtNumberDL.getText().toString();
    }

    @Override
    public String getNumberRC() {
        return edtNumberRC.getText().toString();
    }

    @Override
    public String getNameRC() {
        return edtLastNameRC.getText().toString();
    }

    @Override
    public String getNameDL() {
        return edtLastNameDL.getText().toString();
    }

    @Override
    public String getAddressRC() {
        return edtAddressRC.getText().toString();
    }

    @Override
    public String getAddressDL() {
        return edtAddressDL.getText().toString();
    }

    @Override
    public String getContact() {
        return edtContact.getText().toString();
    }

    @Override
    public String getCode() {
        return edtCode.getText().toString();
    }

    @Override
    public boolean getPrintPhoto() {
        return chbPrintPhoto.isChecked();
    }

    @Override
    public void showMessage(String message) {
        Utils.showMessage(this, message);
    }

    @Override
    public String getManufacture() {
        return txvManufacture.getText().toString();
    }

    @Override
    public String getCarId() {
        return txvCarId.getText().toString();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showActivity(Intent intent) {
        if(intent != null)
        startActivity(intent);
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
