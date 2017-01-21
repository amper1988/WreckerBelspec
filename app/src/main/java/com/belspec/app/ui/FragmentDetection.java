package com.belspec.app.ui;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
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
import com.belspec.app.retrofit.Api;
import com.belspec.app.retrofit.RetrofitService;
import com.belspec.app.retrofit.model.PoliceDepartment;
import com.belspec.app.retrofit.model.Position;
import com.belspec.app.retrofit.model.Rank;
import com.belspec.app.retrofit.model.createEvacuation.request.CreateEvacuationRequestEnvelope;
import com.belspec.app.retrofit.model.createEvacuation.response.CreateEvacuationResponseEnvelope;
import com.belspec.app.utils.Converter;
import com.belspec.app.utils.Encode;
import com.belspec.app.utils.FileManager;
import com.belspec.app.utils.NetworkDataManager;
import com.belspec.app.utils.UserManager;
import com.belspec.app.utils.Utils;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    RecyclerView rvImageList;
    Button btnAddImage;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.detection_fragment, container, false);
        initViews(savedInstanceState);
        return mView;
    }

    private void initViews(Bundle savedInstanceState) {

        //init default value
        chbWithoutEvacuation = (CheckBox)mView.findViewById(R.id.chbWithoutEvacuation);
        actvManufacture = (AutoCompleteTextView) mView.findViewById(R.id.actvManufacture);
        edtCode = (EditText) mView.findViewById(R.id.edtCode);
        actvModel = (AutoCompleteTextView) mView.findViewById(R.id.actvModel);
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
        txvPoliceman = (TextView) mView.findViewById(R.id.txvPoliceman);
        spnPoliceman = (Spinner) mView.findViewById(R.id.spnPoliceman);
        btnRegistrate = (Button) mView.findViewById(R.id.btnRegister);
        btnAddWitness1 = (Button) mView.findViewById(R.id.btnAddWitness1);
        btnAddWitness1.setOnClickListener(this);
        btnAddWitness2 = (Button) mView.findViewById(R.id.btnAddWitness2);
        btnAddWitness2.setOnClickListener(this);
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
        edtStreet = (EditText) mView.findViewById(R.id.edtStreet);
        rgCarType = (RadioGroup) mView.findViewById(R.id.rgType);
        edtRevisionResult = (EditText) mView.findViewById(R.id.edtResultInspection);
        rbCarTypeLight = (RadioButton) mView.findViewById(R.id.rbCarTypeLight);
        rbCarTypeStrong = (RadioButton) mView.findViewById(R.id.rbCarTypeStrong);
        actvColor = (AutoCompleteTextView) mView.findViewById(R.id.actvColor);
        rvImageList = (RecyclerView) mView.findViewById(R.id.rvImageList);
        btnAddImage = (Button) mView.findViewById(R.id.btnAddImage);
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
        setLoading(true);

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
            setLoading(true);
            imageListAdapter = new ImageListAdapter();
            actvModel.setText("");
            actvManufacture.setText("");
            actvColor.setText("");
            edtCarID.setText("");
            edtCode.setText("");
            edtStreet.setText(Utils.getAdress(getContext()));
            edtRevisionResult.setText("");
            txvWtns1Address.setText("");
            txvWtns1Contact.setText("");
            txvWtns1LastName.setText("");
            txvWtns2LastName.setText("");
            txvWtns2Address.setText("");
            txvWtns2Contact.setText("");
            imvWtns1Signature.setImageDrawable(null);
            imvWtns2Signature.setImageDrawable(null);
            imvPolicemanSignature.setImageDrawable(null);
            clausePos = 0;
            organizationPos = 0;
            wreckerPos = 0;
            policemanPos = 0;
            policeDepartmentPos = 0;
            parkingPos = 0;
            chbWithoutEvacuation.setChecked(false);
            plea1 = "";
            plea2 = "";
        } else {
            edtStreet.setText(savedInstanceState.getString("adress"));
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


        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvImageList.setLayoutManager(llm);
        rvImageList.setAdapter(imageListAdapter);
        btnAddImage.setOnClickListener(this);
        btnRegistrate.setOnClickListener(this);
        chbWithoutEvacuation.setOnClickListener(this);
        showHideOrganizationWrecker();
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.btnRegister):

                Utils.hideKeyboard(getActivity());
                if (correctData()) {
                    setLoading(true);
                    RetrofitService createDataRetrofit = Api.createRetrofitService();
                    MyCallback<CreateEvacuationResponseEnvelope> createDataCall = new MyCallback<>();
                    createDataCall.addResponseListener(this);

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
                                            policemanSinature, edtRevisionResult.getText().toString(), chbWithoutEvacuation.isChecked(), spnParking.getSelectedItem().toString()
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
                                            UserManager.getInstanse().getmLogin(),
                                            UserManager.getInstanse().getOrganization(),
                                            ((rbCarTypeStrong.isChecked()) ? 1 : 2),
                                            UserManager.getInstanse().getUserType(), Build.MANUFACTURER + " " + Build.MODEL + " " + Build.SERIAL, code,
                                            txvWtns1LastName.getText().toString(), txvWtns1Address.getText().toString(), txvWtns1Contact.getText().toString(), witness1Signature, plea1,
                                            txvWtns2LastName.getText().toString(), txvWtns2Address.getText().toString(), txvWtns2Contact.getText().toString(), witness2Signature, plea2,
                                            policemanSinature, edtRevisionResult.getText().toString(), chbWithoutEvacuation.isChecked(), spnParking.getSelectedItem().toString()
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
                                            policemanSinature, edtRevisionResult.getText().toString(), chbWithoutEvacuation.isChecked(), spnParking.getSelectedItem().toString()
                                    )
                            ).enqueue(createDataCall);
                            break;
                    }

                }

                break;
            case (R.id.btnAddImage):
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
        }

    }

    private void showHideOrganizationWrecker(){
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_CODE_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                GPSTracker gps = new GPSTracker(getActivity());
                Converter.compressImage(imageListAdapter.getCurrentPath(), Bitmap.CompressFormat.JPEG, 80, 1024);
                if (gps.setGpsToFile(imageListAdapter.getCurrentPath())) {
                    BitmapFactory.Options opt = new BitmapFactory.Options();
                    opt.inSampleSize = 8;
                    Bitmap bitmap = BitmapFactory.decodeFile(imageListAdapter.getCurrentPath(), opt);
                    imageListAdapter.add(bitmap);
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
            if (!(lastName.equals("") || address.equals("") || contact.equals("") || buttonID == 0)) {
                switch (buttonID) {
                    case 1:
                        txvWtns1LastName.setText(intent.getStringExtra("lastName"));
                        txvWtns1Address.setText(intent.getStringExtra("address"));
                        txvWtns1Contact.setText(intent.getStringExtra("contact"));
                        plea1 = intent.getStringExtra("plea");
                        byte[] byteArray = intent.getByteArrayExtra("signature");
                        imvWtns1Signature.setImageBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));
                        break;

                    case 2:
                        txvWtns2LastName.setText(intent.getStringExtra("lastName"));
                        txvWtns2Address.setText(intent.getStringExtra("address"));
                        txvWtns2Contact.setText(intent.getStringExtra("contact"));
                        plea2 = intent.getStringExtra("plea");
                        byte[] byteArray2 = intent.getByteArrayExtra("signature");
                        imvWtns2Signature.setImageBitmap(BitmapFactory.decodeByteArray(byteArray2, 0, byteArray2.length));
                        break;
                }
            }
        }
        if (requestCode == REQUEST_CODE_POLICEMAN_SIGNATURE) {
            byte[] byteArray = intent.getByteArrayExtra("signature");
            if (byteArray != null) {
                imvPolicemanSignature.setImageBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));
            }
        }
    }

    @Override
    public void AuthorizationOK(Response response) {
        if (response.body().getClass() == CreateEvacuationResponseEnvelope.class) {
            CreateEvacuationResponseEnvelope responseEnvelope = (CreateEvacuationResponseEnvelope) response.body();
            if (responseEnvelope != null) {
                if (responseEnvelope.getData().getCode() == 1) {
                    setLoading(false);
                    Utils.showMessage(getContext(), "Зарегистрирован");
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
        Utils.showMessage(this.getActivity(), "Wrong authorization. Response code: " + response.code() + " " + response.message());
        setLoading(false);
    }

    @Override
    public void AuthorizationFail(Throwable t) {
        Utils.showMessage(this.getActivity(), "Network fail. " + t.getMessage());
        setLoading(false);
    }

    private void clear() {
        if (imageListAdapter != null) {
            imageListAdapter.clear();
        }
        initViews(null);
    }

    private void setLoading(Boolean bool) {
        AnimationDrawable animation = (AnimationDrawable) imvLoading.getBackground();
        if (bool) {
            imvLoading.setVisibility(View.VISIBLE);
            animation.start();
            svMain.setVisibility(View.GONE);
        } else {
            imvLoading.setVisibility(View.GONE);
            animation.stop();
            svMain.setVisibility(View.VISIBLE);

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
            spnClause.setSelection(clausePos);
            spnClause.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    clausePos = adapterView.getSelectedItemPosition();
                    Utils.hideKeyboard(getActivity());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    clausePos = 0;
                }
            });

            //configure spnPoliceDepartment
            ArrayAdapter<String> arrayAdapterPoliceDepartment = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, netDataManager.getPoliceDepartmentListAsStirng());
            spnPoliceDepartment.setAdapter(arrayAdapterPoliceDepartment);
            spnPoliceDepartment.setSelection(policeDepartmentPos);
            spnPoliceDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    policeDepartmentPos = adapterView.getSelectedItemPosition();
                    ArrayAdapter<String> arrayAdapterPoliceman = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, netDataManager.getPolicemanListAsStirng(policeDepartmentPos));
                    spnPoliceman.setAdapter(arrayAdapterPoliceman);
                    spnPoliceman.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            policemanPos = adapterView.getSelectedItemPosition();
                            Utils.hideKeyboard(getActivity());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            Utils.hideKeyboard(getActivity());
                        }

                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
//                    policeDepartmentPos = 0;
//                    ArrayAdapter<String> arrayAdapterPoliceman = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, netDataManager.getPolicemanListAsStirng(policeDepartmentPos));
//                    spnPoliceman.setAdapter(arrayAdapterPoliceman);
//                    spnPoliceman.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                            policemanPos = adapterView.getSelectedItemPosition();
//                            Utils.hideKeyboard(getActivity());
//                        }
//                    });
                }
            });

            //configure spnPoliceman
            spnPoliceman.setAdapter(new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, netDataManager.getPolicemanListAsStirng(policeDepartmentPos)));
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
            spnOrganization.setSelection(organizationPos);
            spnOrganization.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    organizationPos = adapterView.getSelectedItemPosition();
                    ArrayAdapter<String> arrayAdapterWrecker = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, netDataManager.getWreckerListAsStirng(organizationPos));
                    spnWrecker.setAdapter(arrayAdapterWrecker);
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
//                    organizationPos = 0;
//                    ArrayAdapter<String> arrayAdapterWrecker = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, netDataManager.getWreckerListAsStirng(organizationPos));
//                    spnWrecker.setAdapter(arrayAdapterWrecker);
//                    spnWrecker.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                            wreckerPos = adapterView.getSelectedItemPosition();
//                            Utils.hideKeyboard(getActivity());
//                        }
//                    });
                }
            });

            //configure spnWrecker
            spnWrecker.setAdapter(new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, netDataManager.getWreckerListAsStirng(organizationPos)));
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
            networkDataManager.getDefaultData();
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
}
