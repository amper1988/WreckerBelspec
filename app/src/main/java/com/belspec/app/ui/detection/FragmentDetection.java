package com.belspec.app.ui.detection;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.belspec.app.R;
import com.belspec.app.adapters.ImageListAdapter;
import com.belspec.app.ui.detection.signature_dialog.PolicemanSignatureDialogFragment;
import com.belspec.app.ui.detection.witness_dialog.WitnessDialogFragment;
import com.belspec.app.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentDetection extends Fragment implements View.OnClickListener, DetectionContract.View {
    @BindView(R.id.actvManufacture) AutoCompleteTextView actvManufacture;
    @BindView(R.id.actvModel) AutoCompleteTextView actvModel;
    @BindView(R.id.actvColor) AutoCompleteTextView actvColor;
    @BindView(R.id.spnOrganization) Spinner spnOrganization;
    @BindView(R.id.spnWrecker) Spinner spnWrecker;
    @BindView(R.id.spnPoliceDepartment) Spinner spnPoliceDepartment;
    @BindView(R.id.spnPoliceman) Spinner spnPoliceman;
    @BindView(R.id.spnParking) Spinner spnParking;
    @BindView(R.id.spnRoadLawPoint) Spinner spnRoadLawPoint;
    @BindView(R.id.svDetection) ScrollView svMain;
    @BindView(R.id.spnClause) Spinner spnClause;
    @BindView(R.id.edtCarID) EditText edtCarID;
    @BindView(R.id.chbWithoutEvacuation) CheckBox chbWithoutEvacuation;
    @BindView(R.id.edtStreet) EditText edtStreet;
    @BindView(R.id.rgType) RadioGroup rgCarType;
    @BindView(R.id.rbCarTypeStrong) RadioButton rbCarTypeStrong;
    @BindView(R.id.rbCarTypeLight) RadioButton rbCarTypeLight;
    @BindView(R.id.edtCode) EditText edtCode;
    @BindView(R.id.edtResultInspection) EditText edtRevisionResult;
    @BindView(R.id.edtRoadLawPoint) EditText edtRoadLawPoint;
    @BindView(R.id.txvPoliceDepartment) TextView txvPoliceDepartment;
    @BindView(R.id.txvPoliceman) TextView txvPoliceman;
    @BindView(R.id.txvOrganization) TextView txvOrganization;
    @BindView(R.id.txvWrecker) TextView txvWrecker;
    @BindView(R.id.txvWtns1Name) TextView txvWtns1LastName;
    @BindView(R.id.txvWtns2Name) TextView txvWtns2LastName;
    @BindView(R.id.txvWtns1Address) TextView txvWtns1Address;
    @BindView(R.id.txvWtns2Address) TextView txvWtns2Address;
    @BindView(R.id.txvWtns1Contact) TextView txvWtns1Contact;
    @BindView(R.id.txvWtns2Contact) TextView txvWtns2Contact;
    @BindView(R.id.imvWtns1Signature) ImageView imvWtns1Signature;
    @BindView(R.id.imvWtns2Signature) ImageView imvWtns2Signature;
    @BindView(R.id.btnRegister) Button btnRegistrate;
    @BindView(R.id.btnAddWitness1) Button btnAddWitness1;
    @BindView(R.id.btnAddWitness2) Button btnAddWitness2;
    @BindView(R.id.btnRefreshStreet) Button btnRefreshStreet;
    @BindView(R.id.btnClear) Button btnClear;
    @BindView(R.id.rvImageList) RecyclerView rvImageList;
    @BindView(R.id.btnAddImage) Button btnAddImage;
    @BindView(R.id.btnCancel) Button btnCancel;
    @BindView(R.id.imvLoading) ImageView imvLoading;
    @BindView(R.id.imvPolicemanSignature) ImageView imvPolicemanSignature;
    @BindView(R.id.btnPolicemanSignature) Button btnPolicemanSignature;
    @BindView(R.id.tilManuf) TextInputLayout tilManuf;
    @BindView(R.id.tilCarID) TextInputLayout tilCarId;
    @BindView(R.id.tilStreet) TextInputLayout tilStreet;
    @BindView(R.id.tilResultInspection) TextInputLayout tilRevisionResult;

    View mView;
    private String plea1;
    private String plea2;
    int fragmentId;
    DetectionContract.Presenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(mView == null) {
            mView = inflater.inflate(R.layout.detection_fragment, container, false);
            fragmentId = getFragmentManager().getFragments().indexOf(this);
            presenter = new DetectionPresenter(this);
        }
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroyView();
    }

    private void initComponents() {
        btnAddImage.setFocusableInTouchMode(true);
        btnAddImage.requestFocus();
        btnAddImage.setFocusableInTouchMode(false);
        //set action on change data in edit text and spinners
        actvModel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.saveModelBackup(s.toString());
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
                if (editable.toString().equals("")) {
                    actvModel.setEnabled(false);
                    actvModel.setText("");
                } else {
                    actvModel.setEnabled(true);
                }
                presenter.saveManufactureBackup(editable.toString());
                presenter.loadListModel(editable.toString());
            }
        });
        actvModel.setEnabled(!actvManufacture.getText().toString().equals(""));
        actvColor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.saveColorBackup(s.toString());
            }
        });
        edtCarID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.saveCarIdBackup(editable.toString());
            }
        });
        edtStreet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.saveStreetBackup(editable.toString());
            }
        });
        edtRoadLawPoint.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.saveRoadLawPointBackup(s.toString());
            }
        });
        edtRevisionResult.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.saveRevisionResultBackup(editable.toString());
            }
        });
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
        spnPoliceDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //load policeman list
                presenter.loadListPoliceman((String)adapterView.getSelectedItem());
                //save backup value
                presenter.savePoliceDepartmentBackup((String)adapterView.getSelectedItem());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spnPoliceman.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //save backup
                presenter.savePolicemanBackup((String)adapterView.getSelectedItem());
                //hide keyboard.
//                Utils.hideKeyboard(getActivity());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spnParking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.saveParkingBackup((String)parent.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnClause.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                presenter.saveClauseBackup((String)adapterView.getSelectedItem());
//                Utils.hideKeyboard(getActivity());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spnOrganization.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String organization = (String)adapterView.getSelectedItem();
                presenter.saveOrganizationBackup(organization);
                presenter.loadListWrecker(organization);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spnWrecker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                presenter.saveWreckerBackup((String)adapterView.getSelectedItem());
//                Utils.hideKeyboard(getActivity());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
        //reset error message from text input layout
        tilManuf.setErrorEnabled(false);
        tilCarId.setErrorEnabled(false);
        tilStreet.setErrorEnabled(false);
        tilRevisionResult.setErrorEnabled(false);
        //init button action
        btnClear.setOnClickListener(this);
        btnAddWitness1.setOnClickListener(this);
        btnAddWitness2.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnRefreshStreet.setOnClickListener(this);
        btnAddImage.setFocusableInTouchMode(true);
        btnAddImage.requestFocus();
        btnAddImage.setFocusableInTouchMode(false);
        btnAddImage.setOnClickListener(this);
        btnPolicemanSignature.setOnClickListener(this);
        btnRegistrate.setOnClickListener(this);
        chbWithoutEvacuation.setOnClickListener(this);
        //init image loading
        imvLoading.setBackgroundResource(R.drawable.pb_loading);
        showHideOrganizationWrecker();
        showHideSpecialField();
        presenter.configureUserData();
    }

    private void confirmWrecker(){
        String message = "Водитель эвакуатора ? " + "\n" + spnWrecker.getSelectedItem().toString();
        Utils.showYesNoDialog(getActivity(), message, new Utils.DialogYesNoListener() {
            @Override
            public void onNegativePress() {

            }
            @Override
            public void onPositivePress() {
                confirmParking();
            }
        });
    }

    private void confirmParking(){
        String message = "Стоянка ?" + "\n" + spnParking.getSelectedItem().toString();
        Utils.showYesNoDialog(getActivity(), message, new Utils.DialogYesNoListener() {
            @Override
            public void onNegativePress() {
            }

            @Override
            public void onPositivePress() {
                confirmAll();
            }
        });
    }

    private void confirmAll(){
        String message = "Статья: " + spnClause.getSelectedItem().toString() + "\n"
                + "Инспектор: " + spnPoliceman.getSelectedItem().toString() + "\n"
                + "Водитель эвакуатора: " + spnWrecker.getSelectedItem().toString() + "\n"
                + "Стоянка: " + spnParking.getSelectedItem().toString() + "\n"
                + "Адрес эвакуации: " + edtStreet.getText().toString() + "\n"+
                "Вы желаете отправить данные?";
        Utils.showYesNoDialog(getActivity(), message, new Utils.DialogYesNoListener() {
            @Override
            public void onNegativePress() {

            }

            @Override
            public void onPositivePress() {
                presenter.sendEvacuation();
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.btnRegister):
//                Utils.hideKeyboard(getActivity());
                if (correctData()) {
                    confirmWrecker();
                }
                break;
            case (R.id.btnAddImage):
                presenter.getStreet();
//                Utils.hideKeyboard(getActivity());
                btnAddImage.setFocusableInTouchMode(true);
                btnAddImage.requestFocus();
                btnAddImage.setFocusableInTouchMode(false);
                if (rvImageList.getAdapter().getItemCount() < 4) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, presenter.getPhotoUri());
                            startActivityForResult(intent, DetectionContract.REQUEST_CODE_PHOTO);
                    }
                } else
                    Toast.makeText(getActivity(), "Maximum 4 files.", Toast.LENGTH_SHORT).show();

                break;

            case (R.id.btnAddWitness1):
//                Utils.hideKeyboard(getActivity());
                btnAddWitness1.setFocusableInTouchMode(true);
                btnAddWitness1.requestFocus();
                btnAddWitness1.setFocusableInTouchMode(false);
                Intent intent = new Intent();
                intent.putExtra(WitnessDialogFragment.NAME_TAG, txvWtns1LastName.getText().toString());
                intent.putExtra(WitnessDialogFragment.CONTACT_TAG, txvWtns1Contact.getText().toString());
                intent.putExtra(WitnessDialogFragment.PLEA_TAG, plea1);
                intent.putExtra(WitnessDialogFragment.ADDRESS_TAG, txvWtns1Address.getText().toString());
                intent.putExtra(WitnessDialogFragment.SIGNATURE_TAG, presenter.getBitmap(imvWtns1Signature));
                WitnessDialogFragment witnessDialogFragment = new WitnessDialogFragment();
                witnessDialogFragment.setArguments(intent.getExtras());
                witnessDialogFragment.show(getFragmentManager(), witnessDialogFragment.getClass().toString());
                presenter.getWitness(1);
                break;

            case (R.id.btnAddWitness2):
//                Utils.hideKeyboard(getActivity());
                btnAddWitness2.setFocusableInTouchMode(true);
                btnAddWitness2.requestFocus();
                btnAddWitness2.setFocusableInTouchMode(false);
                Intent intent2 = new Intent();
                intent2.putExtra(WitnessDialogFragment.NAME_TAG, txvWtns2LastName.getText().toString());
                intent2.putExtra(WitnessDialogFragment.CONTACT_TAG, txvWtns2Contact.getText().toString());
                intent2.putExtra(WitnessDialogFragment.PLEA_TAG, plea2);
                intent2.putExtra(WitnessDialogFragment.ADDRESS_TAG, txvWtns2Address.getText().toString());
                intent2.putExtra(WitnessDialogFragment.SIGNATURE_TAG, presenter.getBitmap(imvWtns2Signature));
                WitnessDialogFragment witnessDialogFragment2 = new WitnessDialogFragment();
                witnessDialogFragment2.setArguments(intent2.getExtras());
                witnessDialogFragment2.show(getFragmentManager(), witnessDialogFragment2.getClass().toString());
                presenter.getWitness(2);
                break;

            case (R.id.btnPolicemanSignature):
//                Utils.hideKeyboard(getActivity());
                btnPolicemanSignature.setFocusableInTouchMode(true);
                btnPolicemanSignature.requestFocus();
                btnPolicemanSignature.setFocusableInTouchMode(false);
                PolicemanSignatureDialogFragment policemanSignatureDialogFragment = new PolicemanSignatureDialogFragment();
                policemanSignatureDialogFragment.show(getFragmentManager(), policemanSignatureDialogFragment.getClass().toString());
                presenter.getSignaturePol();
                break;
            case (R.id.btnRefreshStreet):
//                Utils.hideKeyboard(getActivity());
                btnRefreshStreet.setFocusableInTouchMode(true);
                btnRefreshStreet.requestFocus();
                btnRefreshStreet.setFocusableInTouchMode(false);
                presenter.getStreet();
                break;
            case (R.id.chbWithoutEvacuation):
                presenter.saveWithoutEvacuationBackup(chbWithoutEvacuation.isChecked()? 1: 0);
                showHideOrganizationWrecker();
                break;
            case(R.id.btnClear):
                presenter.clearImages();
                break;
            case(R.id.btnCancel):
                presenter.clearBackup();
                break;
        }

    }

    private void showHideSpecialField(){
        switch (presenter.getUserType()) {
            //configure for policeman
            case 1:
                txvPoliceDepartment.setVisibility(View.GONE);
                spnPoliceDepartment.setVisibility(View.GONE);
                txvPoliceman.setVisibility(View.GONE);
                spnPoliceman.setVisibility(View.GONE);
                break;
            //configure for wrecker
            case 2:
                txvOrganization.setVisibility(View.GONE);
                spnOrganization.setVisibility(View.GONE);
                txvWrecker.setVisibility(View.GONE);
                spnWrecker.setVisibility(View.GONE);
                rgCarType.setVisibility(View.GONE);
                break;
        }
    }

    private void showHideOrganizationWrecker(){
        if(chbWithoutEvacuation.isChecked()){
            txvOrganization.setVisibility(View.GONE);
            spnOrganization.setVisibility(View.GONE);
            txvWrecker.setVisibility(View.GONE);
            spnWrecker.setVisibility(View.GONE);
        }else if(presenter.getUserType() != 2){
            txvOrganization.setVisibility(View.VISIBLE);
            spnOrganization.setVisibility(View.VISIBLE);
            txvWrecker.setVisibility(View.VISIBLE);
            spnWrecker.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == DetectionContract.REQUEST_CODE_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                presenter.onAddPhoto();

            } else if (resultCode == Activity.RESULT_CANCELED) {
                presenter.onPhotoCancel();
            }
        }
    }

    @Override
    public void initialize() {
        presenter.onViewCreated(mView, null);
        initComponents();
    }

    @Override
    public int getDocId() {
        return fragmentId;
    }

    @Override
    public void setAdapter(ImageListAdapter adapter) {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvImageList.setLayoutManager(llm);
        rvImageList.setAdapter(adapter);
    }

    @Override
    public void setLoading(boolean bool) {
        AnimationDrawable animation = (AnimationDrawable) imvLoading.getBackground();
        if(bool){
            imvLoading.setVisibility(View.VISIBLE);
            animation.start();
            svMain.setVisibility(View.GONE);
            Log.d("Loading", "set true");
        }else {
            imvLoading.setVisibility(View.GONE);
            animation.stop();
            svMain.setVisibility(View.VISIBLE);
            Log.d("Loading", "set false");
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean canSetWitness(int index) {
        switch (index){
            case 1:
                return txvWtns1LastName.getText().equals("");
            case 2:
                return txvWtns2LastName.getText().equals("");
        }
        return false;
    }

    @Override
    public void showPdfFile(Uri uri) {
        Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
        pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pdfOpenintent.setDataAndType(uri, "application/pdf");
        try {
            startActivity(pdfOpenintent);
        } catch (ActivityNotFoundException e) {
            Utils.showMessage(getActivity(), "Установите программу для просмотра Pdf файлов");
        }
    }

    @Override
    public void showMessageDialog(String message) {
        Utils.showMessage(getContext(), message);
    }

    @Override
    public void setManufacture(String manufacture) {
        actvManufacture.setText(manufacture);
        presenter.loadListModel(manufacture);
    }

    @Override
    public String getManufacture() {
        return actvManufacture.getText().toString();
    }

    @Override
    public void setModel(String model) {
        actvModel.setText(model);
    }

    @Override
    public String getModel() {
        return actvModel.getText().toString();
    }

    @Override
    public void setCarId(String carId) {
        edtCarID.setText(carId);
    }

    @Override
    public String getCarId() {
        return edtCarID.getText().toString();
    }

    @Override
    public void setColor(String color) {
        actvColor.setText(color);
    }

    @Override
    public String getColor() {
        return actvColor.getText().toString();
    }

    @Override
    public void setWitnessName(String name, int index) {
        switch (index){
            case 1:
                txvWtns1LastName.setText(name);
                break;
            case 2:
                txvWtns2LastName.setText(name);
                break;
        }
    }

    @Override
    public String getWitnessName(int index) {
        switch (index){
            case 1:
                return txvWtns1LastName.getText().toString();
            case 2:
                return txvWtns2LastName.getText().toString();
        }
        return "";
    }

    @Override
    public void setWitnessAddress(String address,int index) {
        switch (index){
            case 1:
                txvWtns1Address.setText(address);
                break;
            case 2:
                txvWtns2Address.setText(address);
                break;
        }
    }

    @Override
    public String getWitnessAddress(int index) {
        switch (index){
            case 1:
                return txvWtns1Address.getText().toString();
            case 2:
                return txvWtns2Address.getText().toString();
        }
        return "";
    }

    @Override
    public void setWitnessContact(String contact, int index) {
        switch (index){
            case 1:
                txvWtns1Contact.setText(contact);
                break;
            case 2:
                txvWtns2Contact.setText(contact);
                break;
        }
    }

    @Override
    public String getWitnessContact(int index) {
        switch (index){
            case 1:
                return txvWtns1Contact.getText().toString();
            case 2:
                return txvWtns2Contact.getText().toString();
        }
        return "";
    }

    @Override
    public void setWitnessSignature(Bitmap signature, int index) {
        switch (index){
            case 1:
                imvWtns1Signature.setImageBitmap(signature);
                break;
            case 2:
                imvWtns2Signature.setImageBitmap(signature);
                break;
        }
    }

    @Override
    public Bitmap getWitnessSignature(int index) {
        switch (index){
            case 1:
                return presenter.getBitmap(imvWtns1Signature);
            case 2:
                return presenter.getBitmap(imvWtns2Signature);
        }
        return null;
    }

    @Override
    public void setWitnessPlea(String plea, int index) {
        switch (index){
            case 1:
                plea1 = plea;
                break;
            case 2:
                plea2 = plea;
                break;
        }
    }

    @Override
    public String getWitnessPlea(int index) {
        switch (index){
            case 1:
                return plea1;
            case 2:
                return plea2;
        }
        return "";
    }

    @Override
    public void setOrganization(String organization) {
        SpinnerAdapter adapter = spnOrganization.getAdapter();
        int count = adapter.getCount();
        int position = -1;
        for(int i = 0; i <  count; i++){
            if(adapter.getItem(i).equals(organization))
                position = i;
        }
        spnOrganization.setSelection(position != -1 && position < count ? position : 0);
    }

    @Override
    public String getOrganization() {
        return (String)spnOrganization.getSelectedItem();
    }

    @Override
    public void setPoliceDepartment(String policeDepartment) {
        SpinnerAdapter adapter = spnPoliceDepartment.getAdapter();
        int count = adapter.getCount();
        int position = -1;
        for(int i = 0; i <  count; i++){
            if(adapter.getItem(i).equals(policeDepartment))
                position = i;
        }
        //set selection to position (if not detect or value more than list site set 0)
        spnPoliceDepartment.setSelection(position != -1 && count > position  ? position : 0);
    }

    @Override
    public String getPoliceDepartment() {
        return (String)spnPoliceDepartment.getSelectedItem();
    }

    @Override
    public void setPoliceman(String policeman) {
        SpinnerAdapter adapter = spnPoliceman.getAdapter();
        int count = adapter.getCount();
        int position = -1;
        for(int i = 0; i <  count; i++){
            if(adapter.getItem(i).equals(policeman))
                position = i;
        }
        //set selection to position (if not detect or value more than list site set 0)
        spnPoliceman.setSelection(position != -1 && position < count? position : 0);
    }

    @Override
    public String getPoliceman() {
        return (String)spnPoliceman.getSelectedItem();
    }

    @Override
    public void setSignaturePol(Bitmap signaturePol) {
        imvPolicemanSignature.setImageBitmap(signaturePol);
    }

    @Override
    public Bitmap getSignaturePol() {
        return presenter.getBitmap(imvPolicemanSignature);
    }

    @Override
    public void setRoadLawPoint(String roadLawPoint) {
        edtRoadLawPoint.setText(roadLawPoint);
    }

    @Override
    public String getRoadLawPoint() {
        return edtRoadLawPoint.getText().toString();
    }

    @Override
    public void setRevisionResult(String revisionResult) {
        edtRevisionResult.setText(revisionResult);
    }

    @Override
    public String getRevisionResult() {
        return edtRevisionResult.getText().toString();
    }

    @Override
    public void setParking(String parking) {
        SpinnerAdapter adapter = spnParking.getAdapter();
        int count = adapter.getCount();
        int position = -1;
        for(int i = 0; i <  count; i++){
             if(adapter.getItem(i).equals(parking))
                 position = i;
        }
        spnParking.setSelection(position != -1 && position < count ? position : 0);
    }

    @Override
    public String getParking() {
        return (String)spnParking.getSelectedItem();
    }

    @Override
    public void setStreet(String street) {
        edtStreet.setText(street);
    }

    @Override
    public String getStreet() {
        return edtStreet.getText().toString();
    }

    @Override
    public void setWithoutEvacuation(boolean bool) {

    }

    @Override
    public boolean getWithoutEvacuation() {
        return chbWithoutEvacuation.isSelected();
    }

    @Override
    public void setClause(String clause) {
        SpinnerAdapter adapter = spnClause.getAdapter();
        int count = adapter.getCount();
        int position = -1;
        for(int i = 0; i <  count; i++){
            if(adapter.getItem(i).equals(clause))
                position = i;
        }
        spnClause.setSelection(position != -1 && position < count ? position : 0);
    }

    @Override
    public String getClause() {
        return (String)spnClause.getSelectedItem();
    }

    @Override
    public void setWrecker(String wrecker) {
        SpinnerAdapter adapter = spnWrecker.getAdapter();
        int count = adapter.getCount();
        int position = -1;
        for(int i = 0; i <  count; i++){
            if(adapter.getItem(i).equals(wrecker))
                position = i;
        }
        spnWrecker.setSelection(position != -1 && position < count? position : 0);
    }

    @Override
    public String getWrecker() {
        return (String)spnWrecker.getSelectedItem();
    }

    @Override
    public String getCode() {
        return edtCode.getText().toString();
    }

    @Override
    public void setCode(String code) {
        edtCode.setText(code);
    }

    @Override
    public void setListPoliceDepartment(List<String> policeDepartments) {
        //create adapter for list
        ArrayAdapter<String> arrayAdapterPoliceDepartment = new ArrayAdapter<>(this.getActivity(), R.layout.spinner_item, policeDepartments);
        //set adapter
        spnPoliceDepartment.setAdapter(arrayAdapterPoliceDepartment);
        //get backup value
        presenter.loadPoliceDepartmentBackup();
    }

    @Override
    public void setListPoliceman(List<String> policemans) {
        //create adapter for list
        ArrayAdapter<String> arrayAdapterPoliceman = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, policemans);
        //set adapter
        spnPoliceman.setAdapter(arrayAdapterPoliceman);
        //get backup value
        presenter.loadPolicemanBackup();
    }

    @Override
    public void setListManufacture(List<String> manufactures) {
        //create adapter for list
        ArrayAdapter<String> arrayAdapterManufacture = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, manufactures);
        //configure threshold
        actvManufacture.setThreshold(1);
        //set adapter
        actvManufacture.setAdapter(arrayAdapterManufacture);
        //get backup value
        presenter.loadManufactureBackup();
    }

    @Override
    public void setListModels(List<String> models) {
        //set threshold
        actvModel.setThreshold(1);
        //set adapter
        actvModel.setAdapter(new ArrayAdapter<>(this.getActivity(), R.layout.spinner_item, models));
        //set backup value
        presenter.loadModelBackup();
    }

    @Override
    public void setListRoadLawPoints(List<String> roadLawPoints) {
        spnRoadLawPoint.setAdapter(new ArrayAdapter<>(this.getActivity(), R.layout.spinner_item,  roadLawPoints));
    }

    @Override
    public void setListParkings(List<String> parkings) {
        spnParking.setAdapter(new ArrayAdapter<>(this.getActivity(), R.layout.spinner_item, parkings));
        presenter.loadParkingBackup();
    }

    @Override
    public void setListColor(List<String> colors) {
        actvColor.setThreshold(1);
        actvColor.setAdapter(new ArrayAdapter<>(this.getActivity(), R.layout.spinner_item, colors));
        presenter.loadColorBackup();
    }

    @Override
    public void setListOrganization(List<String> organizations) {
        spnOrganization.setAdapter(new ArrayAdapter<>(this.getActivity(), R.layout.spinner_item, organizations));
        presenter.loadOrganizationBackup();
    }

    @Override
    public void setListWrecker(List<String> listWrecker) {
        spnWrecker.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.spinner_item, listWrecker));
        presenter.loadWreckerBackup();
    }

    @Override
    public void setListClause(List<String> listClause){
        spnClause.setAdapter( new ArrayAdapter<>(this.getActivity(), R.layout.spinner_item, listClause));
        presenter.loadClauseBackup();
    }

    private boolean correctData() {
        if (actvManufacture.getText().toString().equals("")) {
            tilManuf.setError("Введите марку");
            return false;
        }
        if (edtCarID.getText().toString().equals("")) {
            tilCarId.setError("Введите регзнак");
            return false;
        }
        if (edtStreet.getText().toString().equals("")) {
            tilStreet.setError("Введите адрес эвакуации");
            return false;
        }
        if (edtRevisionResult.getText().toString().equals("")) {
            tilRevisionResult.setError("Введите результаты осмотра");
            return false;
        }
        if (rvImageList.getAdapter().getItemCount() < 4) {
            Toast.makeText(getActivity(), "Сделайте 4 фотографии", Toast.LENGTH_LONG).show();
            return false;
        }
        if (imvWtns1Signature.getDrawable() == null){
            Toast.makeText(getActivity(), "Понятой 1 не подписал документ", Toast.LENGTH_LONG).show();
            return false;
        }else{
            if (presenter.imageIsEmpty(imvWtns1Signature)){
                Toast.makeText(getActivity(), "Понятой 1 не подписал документ", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if (imvWtns2Signature.getDrawable() == null){
            Toast.makeText(getActivity(), "Понятой 2 не подписал документ", Toast.LENGTH_LONG).show();
            return false;
        }else{
            if (presenter.imageIsEmpty(imvWtns2Signature)){
                Toast.makeText(getActivity(), "Понятой 2 не подписал документ", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        if (imvPolicemanSignature.getDrawable() == null){
            Toast.makeText(getActivity(), "Сотрудник ГАИ не подписал документ", Toast.LENGTH_LONG).show();
            return false;

        }else{
            if(presenter.imageIsEmpty(imvPolicemanSignature)){
                Toast.makeText(getActivity(), "Сотрудник ГАИ не подписал документ", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        return true;

    }

}
