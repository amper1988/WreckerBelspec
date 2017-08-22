package com.belspec.app.ui.control.create_policeman_dialog;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.belspec.app.R;
import com.belspec.app.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogFragmentCreatePoliceman extends DialogFragment implements CreatePolicemanContract.View {
    View mView;
    CreatePolicemanPresenter presenter;
    @BindView(R.id.spnPoliceDepartment) Spinner spnPoliceDepartment;
    @BindView(R.id.spnRank) Spinner spnRank;
    @BindView(R.id.spnPosition) Spinner spnPosition;
    @BindView(R.id.edtName) EditText edtName;
    @BindView(R.id.edtCode) EditText edtCode;
    @BindView(R.id.btnRegister) Button btnRegister;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mView = inflater.inflate(R.layout.fragment_create_policeman, null);
        ButterKnife.bind(this, mView);
        presenter = new CreatePolicemanPresenter(this);
        presenter.onCreateDialog();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(confirmData()){
                    presenter.onRegisterClick();
                }else{
                    Toast.makeText(getActivity(), "Проверьте введенные данные на корректрость", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(mView);
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    private boolean confirmData(){
        if(edtCode.getText().toString().equals(""))
            return false;
        if(edtName.getText().toString().equals(""))
            return false;
        return true;
    }

    @Override
    public void close(){
        this.dismiss();
    }

    @Override
    public void setLoading(boolean bool) {
        spnPoliceDepartment.setEnabled(bool);
        spnPosition.setEnabled(bool);
        spnRank.setEnabled(bool);
        edtName.setEnabled(bool);
        edtCode.setEnabled(bool);
        btnRegister.setEnabled(bool);
    }

    @Override
    public String getPoliceDepartment() {
        return spnPoliceDepartment.getSelectedItem().toString();
    }

    @Override
    public String getPosition() {
        return spnPosition.getSelectedItem().toString();
    }

    @Override
    public String getRank() {
        return spnRank.getSelectedItem().toString();
    }

    @Override
    public String getName() {
        return edtName.getText().toString();
    }

    @Override
    public String getCode() {
        return edtCode.getText().toString();
    }

    @Override
    public void onRanksReceive(List<String> ranksList) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, ranksList);
        spnRank.setAdapter(arrayAdapter);
    }

    @Override
    public void onPositionReceive(List<String> positionList) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, positionList);
        spnPosition.setAdapter(arrayAdapter);
    }

    @Override
    public void onPoliceDepartmentReceive(List<String> policeDepartmentList) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, policeDepartmentList);
        spnPoliceDepartment.setAdapter(arrayAdapter);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


    public void showDialogMessage(String message){
        Utils.showMessage(getContext(), message);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
    }
}
