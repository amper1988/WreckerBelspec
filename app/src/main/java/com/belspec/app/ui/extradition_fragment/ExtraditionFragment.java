package com.belspec.app.ui.extradition_fragment;


import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.belspec.app.R;
import com.belspec.app.interfaces.RecyclerItemClickListener;
import com.belspec.app.ui.owner_data.OwnerDataActivity;
import com.belspec.app.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExtraditionFragment extends Fragment implements View.OnClickListener, ExtraditionContract.View {
    View mView;
    @BindView(R.id.imvLoading) ImageView imvLoading;
    @BindView(R.id.rvCarOnEvacuationList) RecyclerView rvListCarOnEvacuation;
    @BindView(R.id.tilCarID) TextInputLayout tilCarID;
    @BindView(R.id.llPoliceData) LinearLayout llPoliceData;
    @BindView(R.id.llOrganizationWrecker) LinearLayout llOrganizationWrecker;
    @BindView(R.id.actvOrganization) AutoCompleteTextView actvOrganization;
    @BindView(R.id.actvWrecker) AutoCompleteTextView actvWrecker;
    @BindView(R.id.actvPoliceDepartment) AutoCompleteTextView actvPoliceDepartment;
    @BindView(R.id.actvPoliceman) AutoCompleteTextView actvPoliceman;
    @BindView(R.id.edtCarID) TextView edtCarId;
    @BindView(R.id.btnFindDetection) Button btnFind;
    @BindView(R.id.btnHide) Button btnHide;
    @BindView(R.id.btnShow) Button btnShow;
    boolean srchHiden;
    ExtraditionPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.extradition_fragment_policeman, container, false);
            ButterKnife.bind(this, mView);
            presenter = new ExtraditionPresenter(this);
            presenter.onCreate();
            initViews();

            srchHiden = false;
        }
        return  mView;
    }

    private void initViews(){
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rvListCarOnEvacuation.setLayoutManager(llm);
        imvLoading.setBackgroundResource(R.drawable.pb_loading);
        configureOnClickListener();
        configureSpecialFields();
        showHide();
    }

    private void configureOnClickListener(){
        rvListCarOnEvacuation.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(view.getContext(), OwnerDataActivity.class);
                intent.putExtra(OwnerDataActivity.EVACUATION_DATA, presenter.getDataForOwnerActivity(position));
                view.getContext().startActivity(intent);
            }
        }));
        actvPoliceDepartment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                boolean empty = actvPoliceDepartment.getText().toString().equals("");
                actvPoliceman.setEnabled(!empty);
                if (empty){
                    actvPoliceman.setText("");
                }else{
                    presenter.getPolicemans(actvPoliceDepartment.getText().toString());
                }
            }
        });
        actvOrganization.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                boolean empty = actvOrganization.getText().toString().equals("");
                actvWrecker.setEnabled(!empty);
                if (empty){
                    actvWrecker.setText("");
                }else{
                    presenter.getWreckers(actvOrganization.getText().toString());
                }
            }
        });
        btnFind.setOnClickListener(this);
        btnHide.setOnClickListener(this);
        btnShow.setOnClickListener(this);
    }

    private void configureSpecialFields(){
        actvPoliceman.setEnabled(!actvPoliceDepartment.getText().toString().equals(""));
        actvWrecker.setEnabled(!actvOrganization.getText().toString().equals(""));
        switch (presenter.getUserType()){
            case 1:
                actvPoliceDepartment.setVisibility(View.GONE);
                actvPoliceman.setVisibility(View.GONE);
                break;
            case 2:
                actvOrganization.setVisibility(View.GONE);
                actvWrecker.setVisibility(View.GONE);
                break;
        }
    }

    private void showHide(){
        if (srchHiden){
            btnHide.setVisibility(View.GONE);
            btnShow.setVisibility(View.VISIBLE);
            llOrganizationWrecker.setVisibility(View.GONE);
            llPoliceData.setVisibility(View.GONE);
            tilCarID.setVisibility(View.GONE);
            btnFind.setVisibility(View.GONE);
        }else{
            btnHide.setVisibility(View.VISIBLE);
            btnShow.setVisibility(View.GONE);
            llOrganizationWrecker.setVisibility(View.VISIBLE);
            llPoliceData.setVisibility(View.VISIBLE);
            tilCarID.setVisibility(View.VISIBLE);
            btnFind.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnFindDetection:
                Utils.hideKeyboard(getActivity());
                srchHiden = true;
                showHide();
                presenter.onFindClick();
                break;
            case R.id.btnHide:
                srchHiden = true;
                showHide();
                break;
            case R.id.btnShow:
                srchHiden = false;
                showHide();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        rvListCarOnEvacuation.setAdapter(adapter);
    }

    @Override
    public void setLoading(boolean bool) {
        AnimationDrawable animation = (AnimationDrawable) imvLoading.getBackground();
        if(bool){
            llOrganizationWrecker.setVisibility(View.GONE);
            llPoliceData.setVisibility(View.GONE);
            tilCarID.setVisibility(View.GONE);
            btnFind.setVisibility(View.GONE);
            imvLoading.setVisibility(View.VISIBLE);
            btnHide.setVisibility(View.GONE);
            btnShow.setVisibility(View.GONE);
            animation.start();
            rvListCarOnEvacuation.setVisibility(View.GONE);
        }else{
            imvLoading.setVisibility(View.GONE);
            animation.stop();
            rvListCarOnEvacuation.setVisibility(View.VISIBLE);
            llOrganizationWrecker.setVisibility(View.VISIBLE);
            llPoliceData.setVisibility(View.VISIBLE);
            tilCarID.setVisibility(View.VISIBLE);
            btnFind.setVisibility(View.VISIBLE);
            showHide();
        }
    }

    @Override
    public void setPolicemans(List<String> policemans) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, policemans);
        actvPoliceman.setAdapter(arrayAdapter);
    }

    @Override
    public void setWreckers(List<String> wreckers) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, wreckers);
        actvWrecker.setAdapter(arrayAdapter);
    }

    @Override
    public void setOrganizations(List<String> organizaiton) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, organizaiton);
        actvOrganization.setAdapter(arrayAdapter);
    }

    @Override
    public void setPoliceDepartments(List<String> policeDepartments) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, policeDepartments);
        actvPoliceDepartment.setAdapter(arrayAdapter);
    }

    @Override
    public void setPoliceDepartment(String policeDepartment) {
        actvPoliceDepartment.setText(policeDepartment);
    }

    @Override
    public void setOrganization(String organization) {
        actvOrganization.setText(organization);
    }

    @Override
    public void setPoliceman(String policeman) {
        actvPoliceman.setText(policeman);
    }

    @Override
    public void setWrecker(String wrecker) {
        actvWrecker.setText(wrecker);
    }

    @Override
    public String getOrganization() {
        return actvOrganization.getText().toString();
    }

    @Override
    public String getPoliceDepartment() {
        return actvPoliceDepartment.getText().toString();
    }

    @Override
    public String getWrecker() {
        return actvWrecker.getText().toString();
    }

    @Override
    public String getPoliceman() {
        return actvPoliceman.getText().toString();
    }

    @Override
    public String getCarId() {
        return edtCarId.getText().toString();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
