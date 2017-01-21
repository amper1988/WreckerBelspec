package com.belspec.app.ui;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.belspec.app.R;
import com.belspec.app.interfaces.MyCallback;
import com.belspec.app.interfaces.NetworkDataUpdate;
import com.belspec.app.interfaces.ResponseListener;
import com.belspec.app.retrofit.Api;
import com.belspec.app.retrofit.RetrofitService;
import com.belspec.app.retrofit.model.PoliceDepartment;
import com.belspec.app.retrofit.model.Position;
import com.belspec.app.retrofit.model.Rank;
import com.belspec.app.retrofit.model.createPoliceman.request.CreatePolicemanRequestEnvelope;
import com.belspec.app.retrofit.model.createPoliceman.response.CreatePolicemanResponseEnvelope;
import com.belspec.app.utils.Encode;
import com.belspec.app.utils.NetworkDataManager;
import com.belspec.app.utils.UserManager;
import com.belspec.app.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class DialogFragmentCreatePoliceman extends DialogFragment implements NetworkDataUpdate {
    View mView;
    Spinner spnPoliceDepartment;
    Spinner spnRank;
    Spinner spnPosition;
    EditText edtName;
    EditText edtCode;
    Button btnRegister;
    NetworkDataManager networkDataManager;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mView = inflater.inflate(R.layout.fragment_create_policeman, null);
        spnPoliceDepartment = (Spinner) mView.findViewById(R.id.spnPoliceDepartment);
        spnRank = (Spinner) mView.findViewById(R.id.spnRank);
        spnPosition = (Spinner) mView.findViewById(R.id.spnPosition);
        edtName = (EditText)mView.findViewById(R.id.edtName);
        edtCode = (EditText)mView.findViewById(R.id.edtCode);
        btnRegister = (Button)mView.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(confirmData()){
                    RetrofitService retrofitService = Api.createRetrofitService();
                    MyCallback<CreatePolicemanResponseEnvelope> call = new MyCallback<>();
                    call.addResponseListener(new ResponseListener() {
                        @Override
                        public void AuthorizationOK(Response response) {
                            CreatePolicemanResponseEnvelope responseEnvelope = (CreatePolicemanResponseEnvelope)response.body();
                            if(responseEnvelope.getData().getCode() == 1){
                                Toast.makeText(getActivity(), responseEnvelope.getData().getDescription(), Toast.LENGTH_SHORT).show();
                                Utils.showMessage(getActivity(), responseEnvelope.getData().getDescription());
                                close();
                            }else{
                                Utils.showMessage(getActivity(), "Fault. Code: " + responseEnvelope.getData().getCode() + "Server answer: "+responseEnvelope.getData().getDescription());
                            }

                        }

                        @Override
                        public void AuthorizationBad(Response response) {
                            Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void AuthorizationFail(Throwable t) {
                            Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                        }
                    });
                    retrofitService.executeCreatePoliceman( Encode.getBasicAuthTemplate(
                            UserManager.getInstanse().getmLogin(),
                            UserManager.getInstanse().getmPassword()),
                            new CreatePolicemanRequestEnvelope(
                                    edtName.getText().toString(),
                                    spnPoliceDepartment.getSelectedItem().toString(),
                                    spnRank.getSelectedItem().toString(),
                                    spnPosition.getSelectedItem().toString(),
                                    edtCode.getText().toString())).enqueue(call);
                }else{
                    Toast.makeText(getActivity(), "Проверьте введенные данные на корректрость", Toast.LENGTH_SHORT).show();
                }
            }
        });
        networkDataManager = NetworkDataManager.getInstance();
        networkDataManager.setListener(this);
        networkDataManager.getRanksListFromServer();
        networkDataManager.getPoliceDepartmetFromServer();
        networkDataManager.getPositionsFromServer();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(mView);
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        networkDataManager.setListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        networkDataManager.unregister(this);
    }

    private boolean confirmData(){
        if(edtCode.getText().toString().equals(""))
            return false;
        if(edtName.getText().toString().equals(""))
            return false;
        return true;
    }

    @Override
    public void onDefaultDataUpdate(NetworkDataManager netDataManager) {

    }

    private void close(){
        networkDataManager.unregister(this);
        this.dismiss();
    }

    @Override
    public void onRanksUpdate(List<Rank> rankList) {
        ArrayList<String> arrayList = new ArrayList<String>();
        for(Rank rank: rankList){
            arrayList.add(rank.getName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, arrayList);
        spnRank.setAdapter(arrayAdapter);
    }

    @Override
    public void onPositionsUpdate(List<Position> positionList) {
        ArrayList<String> arrayList = new ArrayList<String>();
        for(Position position: positionList){
            arrayList.add(position.getName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, arrayList);
        spnPosition.setAdapter(arrayAdapter);
    }

    @Override
    public void onPoliceDepartmentUpdate(List<PoliceDepartment> policeDepartmentList) {
        ArrayList<String> arrayList = new ArrayList<String>();
        for(PoliceDepartment policeDepartment: policeDepartmentList){
            arrayList.add(policeDepartment.getName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, arrayList);
        spnPoliceDepartment.setAdapter(arrayAdapter);
    }
}
