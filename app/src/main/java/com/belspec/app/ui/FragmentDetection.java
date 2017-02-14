package com.belspec.app.ui;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.belspec.app.R;
import com.belspec.app.adapters.ImageListAdapter;
import com.belspec.app.gps.GPSTracker;
import com.belspec.app.interfaces.MyCallback;
import com.belspec.app.interfaces.NetworkDataUpdate;
import com.belspec.app.interfaces.ResponseListener;
import com.belspec.app.model.Witness;
import com.belspec.app.retrofit.Api;
import com.belspec.app.retrofit.RetrofitService;
import com.belspec.app.retrofit.model.PoliceDepartment;
import com.belspec.app.retrofit.model.Position;
import com.belspec.app.retrofit.model.Rank;
import com.belspec.app.retrofit.model.RoadLawPoint;
import com.belspec.app.retrofit.model.createEvacuation.request.CreateEvacuationRequestEnvelope;
import com.belspec.app.retrofit.model.createEvacuation.response.CreateEvacuationResponseEnvelope;
import com.belspec.app.utils.Converter;
import com.belspec.app.utils.Encode;
import com.belspec.app.utils.FileManager;
import com.belspec.app.utils.NetworkDataManager;
import com.belspec.app.utils.SavingManager;
import com.belspec.app.utils.UserManager;
import com.belspec.app.utils.Utils;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class FragmentDetection extends Fragment implements View.OnClickListener, ResponseListener, TextView.OnEditorActionListener, NetworkDataUpdate {
    AutoCompleteTextView actvManufacture;
    AutoCompleteTextView actvModel;
    AutoCompleteTextView actvColor;
    Spinner spnOrganization;
    Spinner spnWrecker;
    Spinner spnPoliceDepartment;
    Spinner spnPoliceman;
    Spinner spnParking;
    Spinner spnRoadLawPoint;
    ScrollView svMain;
    Spinner spnClause;
    EditText edtCarID;
    CheckBox chbWithoutEvacuation;
    EditText edtStreet;
    RadioGroup rgCarType;
    RadioButton rbCarTypeStrong;
    RadioButton rbCarTypeLight;
    EditText edtCode;
    EditText edtRevisionResult;
    EditText edtRoadLawPoint;
    TextView txvPoliceDepartment;
    TextView txvPoliceman;
    TextView txvOrganization;
    TextView txvWrecker;
    TextView txvWtns1LastName;
    TextView txvWtns2LastName;
    TextView txvWtns1Address;
    TextView txvWtns2Address;
    TextView txvWtns1Contact;
    TextView txvWtns2Contact;
    ImageView imvWtns1Signature;
    ImageView imvWtns2Signature;
    Button btnRegistrate;
    Button btnAddWitness1;
    Button btnAddWitness2;
    Button btnRefreshStreet;
    Button btnClear;
    RecyclerView rvImageList;
    Button btnAddImage;
    Button btnCancel;
    View mView;
    ImageListAdapter imageListAdapter;
    ImageView imvLoading;
    ImageView imvPolicemanSignature;
    Button btnPolicemanSignature;
    TextInputLayout tilManuf;
    TextInputLayout tilCarId;
    TextInputLayout tilStreet;
    TextInputLayout tilRevisionResult;
    int clausePos;
    int organizationPos;
    int wreckerPos;
    int policeDepartmentPos;
    int policemanPos;
    int parkingPos;
    NetworkDataManager networkDataManager;
    String adress;
    String plea1;
    String plea2;
    final int REQUEST_CODE_PHOTO = 1;
    final int REQUEST_CODE_WITNESS_SIGNATURE = 0;
    final int REQUEST_CODE_POLICEMAN_SIGNATURE = 2;
    int fragmentId;
    SharedPreferences sPref;
    FragmentDetection main;
    boolean regisrationInProcess;

    public void setWitness(Intent intent){
        String lastName = intent.getStringExtra("lastName");
        String address = intent.getStringExtra("address");
        String contact = intent.getStringExtra("contact");
        int buttonID = intent.getIntExtra("buttonID", 0);
        if (!(lastName.equals("") || address.equals("") || buttonID == 0)) {
            switch (buttonID) {
                case 1:
                    if(txvWtns1LastName.getText().toString().equals(lastName)){
                        break;
                    }
                    if(txvWtns1LastName.getText().toString().equals("") || txvWtns1Address.getText().toString().equals("")) {
                        txvWtns1LastName.setText(lastName);
                        txvWtns1Address.setText(address);
                        txvWtns1Contact.setText(contact);
                        plea1 = intent.getStringExtra("plea");
                        byte[] byteArray = intent.getByteArrayExtra("signature");
                        Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                        SavingManager.saveWitness(getActivity(), fragmentId, lastName, address, bm, contact, 1);
                        SavingManager.savePlea1(getActivity(), fragmentId, plea1);
                        imvWtns1Signature.setImageBitmap(bm);
                        break;
                    }



                case 2:
                    if(txvWtns2LastName.getText().toString().equals(lastName)){
                        break;
                    }
                    if(txvWtns2LastName.getText().toString().equals("") || txvWtns2Address.getText().toString().equals("")){
                        txvWtns2LastName.setText(lastName);
                        txvWtns2Address.setText(address);
                        txvWtns2Contact.setText(contact);
                        plea2 = intent.getStringExtra("plea");
                        byte[] byteArray2 = intent.getByteArrayExtra("signature");
                        Bitmap bm = BitmapFactory.decodeByteArray(byteArray2, 0, byteArray2.length);
                        SavingManager.saveWitness(getActivity(), fragmentId, lastName, address, bm, contact, 2);
                        SavingManager.savePlea2(getActivity(), fragmentId, plea2);
                        imvWtns2Signature.setImageBitmap(bm);
                    }else{
                        if(txvWtns1LastName.getText().toString().equals(lastName)){
                            break;
                        }
                        if(txvWtns1LastName.getText().toString().equals("") || txvWtns1Address.getText().toString().equals("")) {
                            txvWtns1LastName.setText(lastName);
                            txvWtns1Address.setText(address);
                            txvWtns1Contact.setText(contact);
                            plea1 = intent.getStringExtra("plea");
                            byte[] byteArray = intent.getByteArrayExtra("signature");
                            Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                            SavingManager.saveWitness(getActivity(), fragmentId, lastName, address, bm, contact, 1);
                            SavingManager.savePlea1(getActivity(), fragmentId, plea1);
                            imvWtns1Signature.setImageBitmap(bm);
                        }
                    }

                    break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         if(mView == null) {
            mView = inflater.inflate(R.layout.detection_fragment, container, false);
            initViews(savedInstanceState);
        }
        main = this;
        return mView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        networkDataManager.unregister(this);
    }


    private void initViews(Bundle savedInstanceState) {

        //init default value
        chbWithoutEvacuation = (CheckBox)mView.findViewById(R.id.chbWithoutEvacuation);
        actvManufacture = (AutoCompleteTextView) mView.findViewById(R.id.actvManufacture);
        edtCode = (EditText) mView.findViewById(R.id.edtCode);
        actvModel = (AutoCompleteTextView) mView.findViewById(R.id.actvModel);
        actvModel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SavingManager.saveModel(getActivity(), fragmentId, s.toString());
            }
        });
        actvManufacture.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (networkDataManager != null) {
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, networkDataManager.getModelListAsString(actvManufacture.getText().toString()));
                    actvModel.setAdapter(arrayAdapter);
                }
                if (editable.toString().equals("")) {
                    actvModel.setEnabled(false);
                    actvModel.setText("");
                } else {
                    actvModel.setEnabled(true);
//                        actvModel.requestFocus();
                }
                SavingManager.saveManufacture(getActivity(), fragmentId, editable.toString());
            }
        });
        actvModel.setEnabled(!actvManufacture.getText().toString().equals(""));
        spnClause = (Spinner) mView.findViewById(R.id.spnClause);
        txvOrganization = (TextView) mView.findViewById(R.id.txvOrganization);
        spnOrganization = (Spinner) mView.findViewById(R.id.spnOrganization);
        spnParking = (Spinner)mView.findViewById(R.id.spnParking);
        txvWrecker = (TextView) mView.findViewById(R.id.txvWrecker);
        spnWrecker = (Spinner) mView.findViewById(R.id.spnWrecker);
        txvPoliceDepartment = (TextView) mView.findViewById(R.id.txvPoliceDepartment);
        spnPoliceDepartment = (Spinner) mView.findViewById(R.id.spnPoliceDepartment);
        spnRoadLawPoint = (Spinner) mView.findViewById(R.id.spnRoadLawPoint);
        txvPoliceman = (TextView) mView.findViewById(R.id.txvPoliceman);
        spnPoliceman = (Spinner) mView.findViewById(R.id.spnPoliceman);
        btnRegistrate = (Button) mView.findViewById(R.id.btnRegister);
        btnClear = (Button)mView.findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);
        btnAddWitness1 = (Button) mView.findViewById(R.id.btnAddWitness1);
        btnAddWitness1.setOnClickListener(this);
        btnAddWitness2 = (Button) mView.findViewById(R.id.btnAddWitness2);
        btnAddWitness2.setOnClickListener(this);
        btnCancel = (Button)mView.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
        btnRefreshStreet = (Button)mView.findViewById(R.id.btnRefreshStreet);
        btnRefreshStreet.setOnClickListener(this);
        txvWtns1LastName = (TextView) mView.findViewById(R.id.txvWtns1Name);
        txvWtns1Address = (TextView) mView.findViewById(R.id.txvWtns1Address);
        txvWtns1Contact = (TextView) mView.findViewById(R.id.txvWtns1Contact);
        imvWtns1Signature = (ImageView) mView.findViewById(R.id.imvWtns1Signature);
        txvWtns2LastName = (TextView) mView.findViewById(R.id.txvWtns2Name);
        txvWtns2Address = (TextView) mView.findViewById(R.id.txvWtns2Address);
        txvWtns2Contact = (TextView) mView.findViewById(R.id.txvWtns2Contact);
        imvWtns2Signature = (ImageView) mView.findViewById(R.id.imvWtns2Signature);
        edtCarID = (EditText) mView.findViewById(R.id.edtCarID);
        edtCarID.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    SavingManager.saveCarId(getActivity(), fragmentId, (((EditText)v).getText().toString()));
                }
            }
        });
        edtStreet = (EditText) mView.findViewById(R.id.edtStreet);
        edtRoadLawPoint = (EditText)mView.findViewById(R.id.edtRoadLawPoint);
        edtRoadLawPoint.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SavingManager.saveRoadLawPoint(getActivity(), fragmentId, s.toString());
            }
        });
        rgCarType = (RadioGroup) mView.findViewById(R.id.rgType);
        edtRevisionResult = (EditText) mView.findViewById(R.id.edtResultInspection);
        edtRevisionResult.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    SavingManager.saveRevisionResult(getActivity(), fragmentId, ((EditText)v).getText().toString());
                }
            }
        });
        rbCarTypeLight = (RadioButton) mView.findViewById(R.id.rbCarTypeLight);
        rbCarTypeStrong = (RadioButton) mView.findViewById(R.id.rbCarTypeStrong);
        actvColor = (AutoCompleteTextView) mView.findViewById(R.id.actvColor);
        actvColor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SavingManager.saveColor(getActivity(), fragmentId, s.toString());
            }
        });
        rvImageList = (RecyclerView) mView.findViewById(R.id.rvImageList);
        btnAddImage = (Button) mView.findViewById(R.id.btnAddImage);
        btnAddImage.setFocusableInTouchMode(true);
        btnAddImage.requestFocus();
        btnAddImage.setFocusableInTouchMode(false);
        svMain = (ScrollView) mView.findViewById(R.id.svDetection);
        imvLoading = (ImageView) mView.findViewById(R.id.imvLoading);
        imvLoading.setBackgroundResource(R.drawable.pb_loading);
        imvPolicemanSignature = (ImageView) mView.findViewById(R.id.imvPolicemanSignature);
        btnPolicemanSignature = (Button) mView.findViewById(R.id.btnPolicemanSignature);
        btnPolicemanSignature.setOnClickListener(this);
        tilManuf = (TextInputLayout) mView.findViewById(R.id.tilManuf);
        tilManuf.setErrorEnabled(false);
        tilCarId = (TextInputLayout) mView.findViewById(R.id.tilCarID);
        tilCarId.setErrorEnabled(false);
        tilStreet = (TextInputLayout) mView.findViewById(R.id.tilStreet);
        tilStreet.setErrorEnabled(false);
        tilRevisionResult = (TextInputLayout) mView.findViewById(R.id.tilResultInspection);
        tilRevisionResult.setErrorEnabled(false);
        regisrationInProcess = false;
        fragmentId = getFragmentManager().getFragments().indexOf(this);
        //set visible for special user's field
        switch (UserManager.getInstanse().getUserType()) {
            case 1:
                txvPoliceDepartment.setVisibility(View.GONE);
                spnPoliceDepartment.setVisibility(View.GONE);
                txvPoliceman.setVisibility(View.GONE);
                spnPoliceman.setVisibility(View.GONE);
                break;
            case 2:
                txvOrganization.setVisibility(View.GONE);
                spnOrganization.setVisibility(View.GONE);
                txvWrecker.setVisibility(View.GONE);
                spnWrecker.setVisibility(View.GONE);
                rgCarType.setVisibility(View.GONE);
                break;
        }
        if (savedInstanceState == null) {
            Activity activity = getActivity();
            imageListAdapter = new ImageListAdapter();
            String path1 = SavingManager.loadFilePath(activity, fragmentId, 1);
            String path2 = SavingManager.loadFilePath(activity, fragmentId, 2);
            String path3 = SavingManager.loadFilePath(activity, fragmentId, 3);
            String path4 = SavingManager.loadFilePath(activity, fragmentId, 4);
            if(!path1.equals("")){
                imageListAdapter.addFilePath(path1);
                imageListAdapter.add(SavingManager.loadBitmapFromPath(path1));
            }
            if(!path2.equals("")){
                imageListAdapter.addFilePath(path2);
                imageListAdapter.add(SavingManager.loadBitmapFromPath(path2));
            }
            if(!path3.equals("")){
                imageListAdapter.addFilePath(path3);
                imageListAdapter.add(SavingManager.loadBitmapFromPath(path3));
            }
            if(!path4.equals("")){
                imageListAdapter.addFilePath(path4);
                imageListAdapter.add(SavingManager.loadBitmapFromPath(path4));
            }
            actvModel.setText(SavingManager.loadModel(activity, fragmentId));
            actvManufacture.setText(SavingManager.loadManufacture(activity, fragmentId));
            actvColor.setText(SavingManager.loadColor(activity, fragmentId));
            edtCarID.setText(SavingManager.loadCarId(activity, fragmentId));
            edtCode.setText("");
            edtStreet.setText(Utils.getAdress(activity));
            edtRevisionResult.setText(SavingManager.loadRevisionResult(activity, fragmentId));

            Witness witness1 = SavingManager.loadWitness(activity, fragmentId, 1);
            txvWtns1Address.setText(witness1.getAddress());
            txvWtns1Contact.setText(witness1.getContact());
            txvWtns1LastName.setText(witness1.getName());
            Witness witness2 = SavingManager.loadWitness(activity, fragmentId, 2);
            txvWtns2LastName.setText(witness2.getName());
            txvWtns2Address.setText(witness2.getAddress());
            txvWtns2Contact.setText(witness2.getContact());
            edtRoadLawPoint.setText(SavingManager.loadRoadLawPoint(activity, fragmentId));
            imvWtns1Signature.setImageBitmap(witness1.getSignature());
            imvWtns2Signature.setImageBitmap(witness2.getSignature());
            imvPolicemanSignature.setImageBitmap(SavingManager.loadSignaturePol(activity, fragmentId));
            loadDefaultValue();
            chbWithoutEvacuation.setChecked(false);
            plea1 = SavingManager.loadPlea1(getActivity(), fragmentId);
            plea2 = SavingManager.loadPlea2(getActivity(), fragmentId);
        } else {
            edtStreet.setText(savedInstanceState.getString("adress"));
            edtRoadLawPoint.setText(savedInstanceState.getString("roadLawPoint"));
            clausePos = savedInstanceState.getInt("clausePos");
            organizationPos = savedInstanceState.getInt("organizationPos");
            imageListAdapter = savedInstanceState.getParcelable("imageListAdapter");
            wreckerPos = savedInstanceState.getInt("wreckerPos");
            policeDepartmentPos = savedInstanceState.getInt("policeDepartmentPos");
            policemanPos = savedInstanceState.getInt("policemanPos");
            parkingPos = savedInstanceState.getInt("parkingPos");
            plea1 = savedInstanceState.getString("plea1");
            plea2 = savedInstanceState.getString("plea2");
            chbWithoutEvacuation.setChecked(savedInstanceState.getBoolean("withoutEvacuation"));
        }
        networkDataManager = NetworkDataManager.getInstance();
        networkDataManager.setListener(this);
        networkDataManager.getDefaultData();
        networkDataManager.getRoadLawPointFromServer();
        setLoading(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvImageList.setLayoutManager(llm);
        rvImageList.setAdapter(imageListAdapter);
        btnAddImage.setOnClickListener(this);
        btnRegistrate.setOnClickListener(this);
        chbWithoutEvacuation.setOnClickListener(this);
        showHideOrganizationWrecker();
        spnRoadLawPoint.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(edtRoadLawPoint.getText().toString().equals("")){
                    edtRoadLawPoint.setText(parent.getSelectedItem().toString());
                }else{
                    edtRoadLawPoint.setText(edtRoadLawPoint.getText().toString() + "; " + parent.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void loadDefaultValue(){
        sPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        clausePos = sPref.getInt("clausePos", 0);
        organizationPos = sPref.getInt("organizationPos", 0);
        wreckerPos = sPref.getInt("wreckerPos", 0);
        policemanPos = sPref.getInt("policemanPos", 0);
        policeDepartmentPos = sPref.getInt("policeDepartmentPos", 0);
        parkingPos = sPref.getInt("parkingPos", 0);
    }

    private void saveDefaultValue(){
        sPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt("clausePos", clausePos);
        ed.putInt("organizationPos", organizationPos);
        ed.putInt("wreckerPos", wreckerPos);
        ed.putInt("policemanPos", policemanPos);
        ed.putInt("policeDepartmentPos", policeDepartmentPos);
        ed.putInt("parkingPos", parkingPos);
        ed.apply();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("adress", adress);
        outState.putInt("clausePos", clausePos);
        outState.putInt("organizationPos", organizationPos);
        outState.putParcelable("imageListAdapter", imageListAdapter);
        outState.putInt("wreckerPos", wreckerPos);
        outState.putInt("policeDepartmentPos", policeDepartmentPos);
        outState.putInt("policemanPos", policemanPos);
        outState.putString("plea1", plea1);
        outState.putString("plea2", plea2);
        outState.putBoolean("withouEvacuation", chbWithoutEvacuation.isChecked());
        outState.putInt("parkingPos", parkingPos);
        outState.putString("roadLawPoint", edtRoadLawPoint.getText().toString());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.btnRegister):

                Utils.hideKeyboard(getActivity());
                if (correctData()) {
                    final String clause = spnClause.getSelectedItem().toString();
                    String policeman = spnPoliceman.getSelectedItem().toString();
                    String wrecker = spnWrecker.getSelectedItem().toString();
                    final String parking = spnParking.getSelectedItem().toString();
                    final String adress = edtStreet.getText().toString();
                    switch (UserManager.getInstanse().getUserType()){
                        case 1:
                            policeman = UserManager.getInstanse().getmFullName();
                            break;
                        case 2:
                            wrecker = UserManager.getInstanse().getmFullName();
                            break;
                    }
                    String message = "Водитель эвакуатора ? " + "\n" + wrecker;
                    Utils.showYesNoDialog(getActivity(), message, new Utils.DialogYesNoListener() {
                        @Override
                        public void onNegativePress() {

                        }

                        @Override
                        public void onPositivePress() {
                            String message = "Стоянка ?" + "\n" + parking;
                            Utils.showYesNoDialog(getActivity(), message, new Utils.DialogYesNoListener() {
                                @Override
                                public void onNegativePress() {

                                }

                                @Override
                                public void onPositivePress() {
                                    String policeman = spnPoliceman.getSelectedItem().toString();
                                    String wrecker = spnWrecker.getSelectedItem().toString();
                                    switch (UserManager.getInstanse().getUserType()){
                                        case 1:
                                            policeman = UserManager.getInstanse().getmFullName();
                                            break;
                                        case 2:
                                            wrecker = UserManager.getInstanse().getmFullName();
                                            break;
                                    }
                                    String message = "Статья: " + clause + "\n"
                                            + "Инспектор: " + policeman + "\n"
                                            + "Водитель эвакуатора: " + wrecker + "\n"
                                            + "Стоянка: " + parking + "\n"
                                            + "Адрес эвакуации: " + adress + "\n"+
                                            "Вы желаете отправить данные?";
                                    Utils.showYesNoDialog(getActivity(), message, new Utils.DialogYesNoListener() {
                                        @Override
                                        public void onNegativePress() {

                                        }

                                        @Override
                                        public void onPositivePress() {
                                            regisrationInProcess = true;
                                            setLoading(true);
                                            RetrofitService createDataRetrofit = Api.createRetrofitService();
                                            MyCallback<CreateEvacuationResponseEnvelope> createDataCall = new MyCallback<>();
                                            createDataCall.addResponseListener(main);

                                            String photo1 = "";
                                            String photo2 = "";
                                            String photo3 = "";
                                            String photo4 = "";

                                            int count = imageListAdapter.getItemCount();
                                            if (count == 4) {
                                                photo1 = Converter.encodeFileToBase64String(imageListAdapter.getFilePath(0));
                                                photo2 = Converter.encodeFileToBase64String(imageListAdapter.getFilePath(1));
                                                photo3 = Converter.encodeFileToBase64String(imageListAdapter.getFilePath(2));
                                                photo4 = Converter.encodeFileToBase64String(imageListAdapter.getFilePath(3));
                                            } else if (count == 3) {
                                                photo1 = Converter.encodeFileToBase64String(imageListAdapter.getFilePath(0));
                                                photo2 = Converter.encodeFileToBase64String(imageListAdapter.getFilePath(1));
                                                photo3 = Converter.encodeFileToBase64String(imageListAdapter.getFilePath(2));
                                            } else if (count == 2) {
                                                photo1 = Converter.encodeFileToBase64String(imageListAdapter.getFilePath(0));
                                                photo2 = Converter.encodeFileToBase64String(imageListAdapter.getFilePath(1));
                                            } else if (count == 1) {
                                                photo1 = Converter.encodeFileToBase64String(imageListAdapter.getFilePath(0));
                                            }

                                            String pre = Utils.random(4);
                                            String post = Utils.random(2);
                                            String code = Converter.encodeToBase64(pre + edtCode.getText().toString() + post);

                                            String witness1Signature = Converter.encodeImageViewToBase64String(imvWtns1Signature, Bitmap.CompressFormat.PNG);
                                            String witness2Signature = Converter.encodeImageViewToBase64String(imvWtns2Signature, Bitmap.CompressFormat.PNG);
                                            String policemanSinature = Converter.encodeImageViewToBase64String(imvPolicemanSignature, Bitmap.CompressFormat.PNG);

                                            switch (UserManager.getInstanse().getUserType()) {
                                                case 1:
                                                    createDataRetrofit.executeCreateEvacuation(
                                                            Encode.getBasicAuthTemplate(
                                                                    UserManager.getInstanse().getmLogin(),
                                                                    UserManager.getInstanse().getmPassword()
                                                            ),
                                                            new CreateEvacuationRequestEnvelope(
                                                                    actvManufacture.getText().toString(),
                                                                    actvModel.getText().toString(),
                                                                    edtCarID.getText().toString(),
                                                                    actvColor.getText().toString(),
                                                                    photo1, photo2, photo3, photo4,
                                                                    edtStreet.getText().toString(), spnClause.getSelectedItem().toString(),
                                                                    UserManager.getInstanse().getOrganization(), UserManager.getInstanse().getmFullName(),
                                                                    spnWrecker.getSelectedItem().toString(),
                                                                    spnOrganization.getSelectedItem().toString(),
                                                                    ((rbCarTypeStrong.isChecked()) ? 1 : 2),
                                                                    UserManager.getInstanse().getUserType(), Build.MANUFACTURER + " " + Build.DEVICE + " " + Build.SERIAL, code,
                                                                    txvWtns1LastName.getText().toString(), txvWtns1Address.getText().toString(), txvWtns1Contact.getText().toString(), witness1Signature, plea1,
                                                                    txvWtns2LastName.getText().toString(), txvWtns2Address.getText().toString(), txvWtns2Contact.getText().toString(), witness2Signature, plea2,
                                                                    policemanSinature, edtRevisionResult.getText().toString(), chbWithoutEvacuation.isChecked(), spnParking.getSelectedItem().toString(),
                                                                    edtRoadLawPoint.getText().toString()
                                                            )
                                                    ).enqueue(createDataCall);
                                                    break;

                                                case 2:
                                                    createDataRetrofit.executeCreateEvacuation(
                                                            Encode.getBasicAuthTemplate(
                                                                    UserManager.getInstanse().getmLogin(),
                                                                    UserManager.getInstanse().getmPassword()
                                                            ),
                                                            new CreateEvacuationRequestEnvelope(
                                                                    actvManufacture.getText().toString(),
                                                                    actvModel.getText().toString(),
                                                                    edtCarID.getText().toString(),
                                                                    actvColor.getText().toString(),
                                                                    photo1, photo2, photo3, photo4,
                                                                    edtStreet.getText().toString(), spnClause.getSelectedItem().toString(),
                                                                    spnPoliceDepartment.getSelectedItem().toString(), spnPoliceman.getSelectedItem().toString(),
                                                                    UserManager.getInstanse().getmFullName(),
                                                                    UserManager.getInstanse().getOrganization(),
                                                                    ((rbCarTypeStrong.isChecked()) ? 1 : 2),
                                                                    UserManager.getInstanse().getUserType(), Build.MANUFACTURER + " " + Build.MODEL + " " + Build.SERIAL, code,
                                                                    txvWtns1LastName.getText().toString(), txvWtns1Address.getText().toString(), txvWtns1Contact.getText().toString(), witness1Signature, plea1,
                                                                    txvWtns2LastName.getText().toString(), txvWtns2Address.getText().toString(), txvWtns2Contact.getText().toString(), witness2Signature, plea2,
                                                                    policemanSinature, edtRevisionResult.getText().toString(), chbWithoutEvacuation.isChecked(), spnParking.getSelectedItem().toString(),
                                                                    edtRoadLawPoint.getText().toString()
                                                            )
                                                    ).enqueue(createDataCall);
                                                    break;

                                                case 3:
                                                    createDataRetrofit.executeCreateEvacuation(
                                                            Encode.getBasicAuthTemplate(
                                                                    UserManager.getInstanse().getmLogin(),
                                                                    UserManager.getInstanse().getmPassword()
                                                            ),
                                                            new CreateEvacuationRequestEnvelope(
                                                                    actvManufacture.getText().toString(),
                                                                    actvModel.getText().toString(),
                                                                    edtCarID.getText().toString(),
                                                                    actvColor.getText().toString(),
                                                                    photo1, photo2, photo3, photo4,
                                                                    edtStreet.getText().toString(), spnClause.getSelectedItem().toString(),
                                                                    spnPoliceDepartment.getSelectedItem().toString(), spnPoliceman.getSelectedItem().toString(),
                                                                    spnWrecker.getSelectedItem().toString(),
                                                                    spnOrganization.getSelectedItem().toString(),
                                                                    ((rbCarTypeStrong.isChecked()) ? 1 : 2),
                                                                    UserManager.getInstanse().getUserType(), Build.MANUFACTURER + " " +  Build.DEVICE + " " + Build.SERIAL, code,
                                                                    txvWtns1LastName.getText().toString(), txvWtns1Address.getText().toString(), txvWtns1Contact.getText().toString(), witness1Signature, plea1,
                                                                    txvWtns2LastName.getText().toString(), txvWtns2Address.getText().toString(), txvWtns2Contact.getText().toString(), witness2Signature, plea2,
                                                                    policemanSinature, edtRevisionResult.getText().toString(), chbWithoutEvacuation.isChecked(), spnParking.getSelectedItem().toString(),
                                                                    edtRoadLawPoint.getText().toString()
                                                            )
                                                    ).enqueue(createDataCall);
                                                    break;
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    });



                }
                break;
            case (R.id.btnAddImage):
                this.onClick(btnRefreshStreet);
                Utils.hideKeyboard(getActivity());
                btnAddImage.setFocusableInTouchMode(true);
                btnAddImage.requestFocus();
                btnAddImage.setFocusableInTouchMode(false);

                if (imageListAdapter.getItemCount() < 4) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        File photoFile = null;
                        try {
                            photoFile = FileManager.createImageFile(getActivity());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                        if (photoFile != null) {
                            imageListAdapter.addFilePath(photoFile.getAbsolutePath());
                            Uri photoURI = Uri.fromFile(photoFile);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            startActivityForResult(intent, REQUEST_CODE_PHOTO);
                        }
                    }
                } else
                    Toast.makeText(getActivity(), "Maximum 4 files.", Toast.LENGTH_SHORT).show();

                break;

            case (R.id.btnAddWitness1):
                Utils.hideKeyboard(getActivity());
                btnAddWitness1.setFocusableInTouchMode(true);
                btnAddWitness1.requestFocus();
                btnAddWitness1.setFocusableInTouchMode(false);
                WitnessDialogFragment witnessDialogFragment = new WitnessDialogFragment();
                witnessDialogFragment.buttonID = 1;
                witnessDialogFragment.address = txvWtns1Address.getText().toString();
                witnessDialogFragment.lastName = txvWtns1LastName.getText().toString();
                witnessDialogFragment.contact= txvWtns1Contact.getText().toString();
                witnessDialogFragment.bm = Converter.convertImageViewToBitmap(imvWtns1Signature);
                witnessDialogFragment.setTargetFragment(this, REQUEST_CODE_WITNESS_SIGNATURE);
                witnessDialogFragment.show(getFragmentManager(), witnessDialogFragment.getClass().toString());
                break;

            case (R.id.btnAddWitness2):
                Utils.hideKeyboard(getActivity());
                btnAddWitness2.setFocusableInTouchMode(true);
                btnAddWitness2.requestFocus();
                btnAddWitness2.setFocusableInTouchMode(false);
                WitnessDialogFragment witnessDialogFragment2 = new WitnessDialogFragment();
                witnessDialogFragment2.buttonID = 2;
                witnessDialogFragment2.address = txvWtns2Address.getText().toString();
                witnessDialogFragment2.lastName = txvWtns2LastName.getText().toString();
                witnessDialogFragment2.contact= txvWtns2Contact.getText().toString();
                witnessDialogFragment2.bm = Converter.convertImageViewToBitmap(imvWtns2Signature);
                witnessDialogFragment2.setTargetFragment(this, REQUEST_CODE_WITNESS_SIGNATURE);
                witnessDialogFragment2.show(getFragmentManager(), witnessDialogFragment2.getClass().toString());
                break;

            case (R.id.btnPolicemanSignature):
                Utils.hideKeyboard(getActivity());
                btnPolicemanSignature.setFocusableInTouchMode(true);
                btnPolicemanSignature.requestFocus();
                btnPolicemanSignature.setFocusableInTouchMode(false);
                PolicemanSignatureDialogFragment policemanSignatureDialogFragment = new PolicemanSignatureDialogFragment();
                policemanSignatureDialogFragment.setTargetFragment(this, REQUEST_CODE_POLICEMAN_SIGNATURE);
                policemanSignatureDialogFragment.show(getFragmentManager(), policemanSignatureDialogFragment.getClass().toString());
                break;
            case (R.id.btnRefreshStreet):
                Utils.hideKeyboard(getActivity());
                btnRefreshStreet.setFocusableInTouchMode(true);
                btnRefreshStreet.requestFocus();
                btnRefreshStreet.setFocusableInTouchMode(false);
                edtStreet.setText(Utils.getAdress(getActivity()));
                break;
            case (R.id.chbWithoutEvacuation):
                showHideOrganizationWrecker();
                break;
            case(R.id.btnClear):
                if(imageListAdapter != null)
                    imageListAdapter.clear();

                SavingManager.saveFilePath(getActivity(), "", fragmentId, 1);
                SavingManager.saveFilePath(getActivity(), "", fragmentId, 2);
                SavingManager.saveFilePath(getActivity(), "", fragmentId, 3);
                SavingManager.saveFilePath(getActivity(), "", fragmentId, 4);
                break;
            case(R.id.btnCancel):
                if(imageListAdapter !=null)
                    imageListAdapter.clear();
                SavingManager.clear(getActivity(), fragmentId);
                initViews(null);
                break;
        }

    }

    private void showHideOrganizationWrecker(){
        if(UserManager.getInstanse().getUserType() != 2){
            if(chbWithoutEvacuation.isChecked()){
                spnOrganization.setVisibility(View.GONE);
                spnWrecker.setVisibility(View.GONE);
                txvOrganization.setVisibility(View.GONE);
                txvWrecker.setVisibility(View.GONE);
            }else{
                spnOrganization.setVisibility(View.VISIBLE);
                spnWrecker.setVisibility(View.VISIBLE);
                txvOrganization.setVisibility(View.VISIBLE);
                txvWrecker.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_CODE_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                GPSTracker gps = new GPSTracker(getActivity());
                Converter.compressImage(imageListAdapter.getCurrentPath(), Bitmap.CompressFormat.JPEG, 70, 1536);
                if (gps.setGpsToFile(imageListAdapter.getCurrentPath())) {
                    BitmapFactory.Options opt = new BitmapFactory.Options();
                    opt.inSampleSize = 8;
                    Bitmap bitmap = BitmapFactory.decodeFile(imageListAdapter.getCurrentPath(), opt);
                    imageListAdapter.add(bitmap);
                    SavingManager.saveFilePath(getActivity(), imageListAdapter.getCurrentPath(), fragmentId, imageListAdapter.getItemCount());
                } else {
                    imageListAdapter.deleteCurrentPath();
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                imageListAdapter.deleteCurrentPath();
            }
        }
        if (requestCode == REQUEST_CODE_WITNESS_SIGNATURE) {
            String lastName = intent.getStringExtra("lastName");
            String address = intent.getStringExtra("address");
            String contact = intent.getStringExtra("contact");
            int buttonID = intent.getIntExtra("buttonID", 0);
            if (!(lastName.equals("") || address.equals("") || buttonID == 0)) {
                switch (buttonID) {
                    case 1:
                        txvWtns1LastName.setText(lastName);

                        txvWtns1Address.setText(address);
                        txvWtns1Contact.setText(contact);
                        plea1 = intent.getStringExtra("plea");
                        byte[] byteArray = intent.getByteArrayExtra("signature");
                        Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                        imvWtns1Signature.setImageBitmap(bm);
                        SavingManager.saveWitness(getActivity(), fragmentId, lastName, address, bm, contact, buttonID);
                        SavingManager.savePlea1(getActivity(), fragmentId, plea1);
                        break;

                    case 2:
                        txvWtns2LastName.setText(lastName);
                        txvWtns2Address.setText(address);
                        txvWtns2Contact.setText(contact);
                        plea2 = intent.getStringExtra("plea");
                        byte[] byteArray2 = intent.getByteArrayExtra("signature");
                        Bitmap bm2 = BitmapFactory.decodeByteArray(byteArray2, 0, byteArray2.length);
                        imvWtns2Signature.setImageBitmap(bm2);
                        SavingManager.saveWitness(getActivity(), fragmentId, lastName, address, bm2, contact, buttonID);
                        SavingManager.savePlea2(getActivity(), fragmentId, plea2);
                        break;
                }
            }
        }
        if (requestCode == REQUEST_CODE_POLICEMAN_SIGNATURE) {
            byte[] byteArray = intent.getByteArrayExtra("signature");
            if (byteArray != null) {
                Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                imvPolicemanSignature.setImageBitmap(bm);
                SavingManager.saveSignaturePol(getActivity(), fragmentId, bm);
            }
        }
    }

    @Override
    public void AuthorizationOK(Response response) {
        regisrationInProcess = false;
        if (response.body().getClass() == CreateEvacuationResponseEnvelope.class) {
            CreateEvacuationResponseEnvelope responseEnvelope = (CreateEvacuationResponseEnvelope) response.body();
            if (responseEnvelope != null) {
                if (responseEnvelope.getData().getCode() == 1) {
                    setLoading(false);
                    Utils.showMessage(getContext(), "Зарегистрирован");
                    SavingManager.clear(getActivity(), fragmentId);
                    try {
                        File tmpFile = FileManager.createPdfFile(getContext());
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
                            Utils.showMessage(getActivity(), "Установите программу для просмотра Pdf файлов");
                        }
                    } catch (IOException e) {

                    }
                    setLoading(false);
                    saveDefaultValue();
                    clear();
                } else {
                    setLoading(false);
                    Utils.showMessage(getActivity(), "Ошибка. Код ошибки: " + responseEnvelope.getData().getCode() + " Описание ошибки: " + responseEnvelope.getData().getDescription());
                }
            } else {
                setLoading(false);
                Utils.showMessage(getActivity(), "Неожиданно пустой ответ от сервера. Повторите попытку.");
            }
        }
    }

    @Override
    public void AuthorizationBad(Response response) {
        regisrationInProcess = false;
        Utils.showMessage(this.getActivity(), "Wrong authorization. Response code: " + response.code() + " " + response.message());
        setLoading(false);
    }

    @Override
    public void AuthorizationFail(Throwable t) {
        regisrationInProcess = false;
        Utils.showMessage(this.getActivity(), "Network fail. " + t.getMessage());
        setLoading(false);
    }

    private void clear() {
        if (imageListAdapter != null) {
            imageListAdapter.clear();
        }
        initViews(null);
    }

    private void setLoading(boolean bool) {
        AnimationDrawable animation = (AnimationDrawable) imvLoading.getBackground();
        if(regisrationInProcess){
            imvLoading.setVisibility(View.VISIBLE);
            animation.start();
            svMain.setVisibility(View.GONE);
        } else {
            if(bool){
                imvLoading.setVisibility(View.VISIBLE);
                animation.start();
                svMain.setVisibility(View.GONE);
            }else {
                imvLoading.setVisibility(View.GONE);
                animation.stop();
                svMain.setVisibility(View.VISIBLE);
            }

        }


    }

    private boolean correctData() {
        if (actvManufacture.getText().toString().equals("")) {
            tilManuf = (TextInputLayout) mView.findViewById(R.id.tilManuf);
            tilManuf.setError("Введите марку");
            Toast.makeText(getActivity(), "Введите марку", Toast.LENGTH_LONG).show();
            return false;
        }
        if (edtCarID.getText().toString().equals("")) {
            tilCarId = (TextInputLayout) mView.findViewById(R.id.tilCarID);
            tilCarId.setError("Введите регзнак");
            Toast.makeText(getActivity(), "Введите регзнак", Toast.LENGTH_LONG).show();
            return false;
        }
        if (edtStreet.getText().toString().equals("")) {
            tilStreet = (TextInputLayout) mView.findViewById(R.id.tilStreet);
            tilStreet.setError("Введите адрес эвакуации");
            Toast.makeText(getActivity(), "Введите адрес эвакуации", Toast.LENGTH_LONG).show();
            return false;
        }
        if (edtRevisionResult.getText().toString().equals("")) {
            tilRevisionResult = (TextInputLayout) mView.findViewById(R.id.tilResultInspection);
            tilRevisionResult.setError("Введите результаты осмотра");
            Toast.makeText(getActivity(), "Введите результаты осмотра", Toast.LENGTH_LONG).show();
            return false;
        }
        if (imageListAdapter.getItemCount() < 4) {
            Toast.makeText(getActivity(), "Сделайте 4 фотографии", Toast.LENGTH_LONG).show();
            return false;
        }
        if (imvWtns1Signature.getDrawable() == null){
            Toast.makeText(getActivity(), "Понятой 1 не подписал документ", Toast.LENGTH_LONG).show();
            return false;
        }else{
            if (Utils.DrawableIsEmptyInImageView(imvWtns1Signature)){
                Toast.makeText(getActivity(), "Понятой 1 не подписал документ", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if (imvWtns2Signature.getDrawable() == null){
            Toast.makeText(getActivity(), "Понятой 2 не подписал документ", Toast.LENGTH_LONG).show();
            return false;
        }else{
            if (Utils.DrawableIsEmptyInImageView(imvWtns2Signature)){
                Toast.makeText(getActivity(), "Понятой 2 не подписал документ", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        if (imvPolicemanSignature.getDrawable() == null){
            Toast.makeText(getActivity(), "Сотрудник ГАИ не подписал документ", Toast.LENGTH_LONG).show();
            return false;

        }else{
            if(Utils.DrawableIsEmptyInImageView(imvPolicemanSignature)){
                Toast.makeText(getActivity(), "Сотрудник ГАИ не подписал документ", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

        return false;
    }

    @Override
    public void onDefaultDataUpdate(final NetworkDataManager netDataManager) {
        if (netDataManager != null) {
            this.networkDataManager = netDataManager;
            //configure actvManufacture
            ArrayAdapter<String> arrayAdapterManufacture = new ArrayAdapter<>(this.getActivity(), R.layout.spinner_item, netDataManager.getManufactureListAsString());
            actvManufacture.setThreshold(1);
            actvManufacture.setAdapter(arrayAdapterManufacture);

            //configure actvModel
            ArrayAdapter<String> arrayAdapterModel = new ArrayAdapter<>(this.getActivity(), R.layout.spinner_item, netDataManager.getModelListAsString(""));
            actvModel.setThreshold(1);
            actvModel.setAdapter(arrayAdapterModel);

            //configure spnClause
            ArrayAdapter<String> arrayAdapterClause = new ArrayAdapter<>(this.getActivity(), R.layout.spinner_item, netDataManager.getClauseListAsString());
            spnClause.setAdapter(arrayAdapterClause);
            clausePos = (arrayAdapterClause.getCount() < clausePos + 1 ? 0: clausePos);
            spnClause.setSelection(clausePos);
            spnClause.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    clausePos = adapterView.getSelectedItemPosition();
                    Utils.hideKeyboard(getActivity());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            //configure spnPoliceDepartment
            ArrayAdapter<String> arrayAdapterPoliceDepartment = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, netDataManager.getPoliceDepartmentListAsStirng());
            spnPoliceDepartment.setAdapter(arrayAdapterPoliceDepartment);
            policeDepartmentPos =  (arrayAdapterPoliceDepartment.getCount() < policeDepartmentPos + 1) ? 0: policeDepartmentPos;
            spnPoliceDepartment.setSelection(policeDepartmentPos);
            spnPoliceDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    policeDepartmentPos = adapterView.getSelectedItemPosition();
                    ArrayAdapter<String> arrayAdapterPoliceman = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, netDataManager.getPolicemanListAsStirng(policeDepartmentPos));
                    spnPoliceman.setAdapter(arrayAdapterPoliceman);
                    policemanPos = (arrayAdapterPoliceman.getCount() < policemanPos + 1) ? 0: policemanPos;
                    spnPoliceman.setSelection(policemanPos);
                    spnPoliceman.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            policemanPos = adapterView.getSelectedItemPosition();
                            Utils.hideKeyboard(getActivity());
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            //configure spnPoliceman
            ArrayAdapter<String> arrayAdapterPoliceman = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, netDataManager.getPolicemanListAsStirng(policeDepartmentPos));
            spnPoliceman.setAdapter(arrayAdapterPoliceman);
            policemanPos = (arrayAdapterPoliceman.getCount() < policemanPos + 1) ? 0: policemanPos;
            spnPoliceman.setSelection(policemanPos);
            spnPoliceman.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    policemanPos = adapterView.getSelectedItemPosition();
                    Utils.hideKeyboard(getActivity());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            //configure spnParking
            ArrayAdapter<String> arrayAdapterParking = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, netDataManager.getParkingListAsString());
            spnParking.setAdapter(arrayAdapterParking);
            parkingPos = (arrayAdapterParking.getCount() < parkingPos + 1) ? 0: parkingPos;
            spnParking.setSelection(parkingPos);
            spnParking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    parkingPos = spnParking.getSelectedItemPosition();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            //configure spnOrganization
            ArrayAdapter<String> arrayAdapterOrganization = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, netDataManager.getOrganizationListAsString());
            spnOrganization.setAdapter(arrayAdapterOrganization);
            organizationPos = (arrayAdapterOrganization.getCount() < organizationPos + 1)? 0: organizationPos;
            spnOrganization.setSelection(organizationPos);
            spnOrganization.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    organizationPos = adapterView.getSelectedItemPosition();
                    ArrayAdapter<String> arrayAdapterWrecker = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, netDataManager.getWreckerListAsStirng(organizationPos));
                    spnWrecker.setAdapter(arrayAdapterWrecker);
                    wreckerPos = (arrayAdapterWrecker.getCount() < wreckerPos + 1) ? 0: wreckerPos;
                    spnWrecker.setSelection(wreckerPos);
                    spnWrecker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            wreckerPos = adapterView.getSelectedItemPosition();
                            Utils.hideKeyboard(getActivity());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }

                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            //configure spnWrecker
            ArrayAdapter<String> arrayAdapterWrecker = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, netDataManager.getWreckerListAsStirng(organizationPos));
            spnWrecker.setAdapter(arrayAdapterWrecker);
            wreckerPos = (arrayAdapterWrecker.getCount() < wreckerPos + 1) ? 0 : wreckerPos;
            spnWrecker.setSelection(wreckerPos);
            spnWrecker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    wreckerPos = adapterView.getSelectedItemPosition();
                    Utils.hideKeyboard(getActivity());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            //configure actvColor
            ArrayAdapter<String> arrayAdapterColor = new ArrayAdapter<>(this.getActivity(), R.layout.spinner_item, netDataManager.getColorListAsString());
            actvColor.setThreshold(1);
            actvColor.setAdapter(arrayAdapterColor);
            setLoading(false);
        } else {
            setLoading(false);
        }
    }

    @Override
    public void onRanksUpdate(List<Rank> rankList) {

    }

    @Override
    public void onPositionsUpdate(List<Position> positionList) {

    }

    @Override
    public void onPoliceDepartmentUpdate(List<PoliceDepartment> policeDepartmentList) {

    }

    @Override
    public void onRoadLowPointUpdate(List<RoadLawPoint> roadLawPoints) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        for(RoadLawPoint item : roadLawPoints){
            arrayList.add(item.getName());
        }
        spnRoadLawPoint.setAdapter(new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item,  arrayList));
    }
}
