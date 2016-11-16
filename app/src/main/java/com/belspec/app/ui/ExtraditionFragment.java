package com.belspec.app.ui;


import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.belspec.app.R;
import com.belspec.app.adapters.CarOnEvacuationAdapter;
import com.belspec.app.interfaces.MyCallback;
import com.belspec.app.interfaces.NetworkDataUpdate;
import com.belspec.app.interfaces.RecyclerItemClickListener;
import com.belspec.app.interfaces.ResponseListener;
import com.belspec.app.retrofit.Api;
import com.belspec.app.retrofit.RetrofitService;
import com.belspec.app.retrofit.model.getCarOnEvacuation.request.GetCarOnEvacuationRequestEnvelope;
import com.belspec.app.retrofit.model.getCarOnEvacuation.response.EvacuationData;
import com.belspec.app.retrofit.model.getCarOnEvacuation.response.GetCarOnEvacuationResponseEnvelope;
import com.belspec.app.utils.Encode;
import com.belspec.app.utils.NetworkDataManager;
import com.belspec.app.utils.UserManager;
import com.belspec.app.utils.Utils;

import java.util.List;

import retrofit2.Response;

public class ExtraditionFragment extends Fragment implements NetworkDataUpdate, ResponseListener, View.OnClickListener {
    View mView;
    ImageView imvLoading;
    RecyclerView rvListCarOnEvacuation;
    LinearLayout llSearch;
    AutoCompleteTextView actvOrganization;
    AutoCompleteTextView actvWrecker;
    AutoCompleteTextView actvPoliceDepartment;
    AutoCompleteTextView actvPoliceman;
    TextView edtCarId;
    Button btnFind;
    NetworkDataManager networkDataManager;
    CarOnEvacuationAdapter carOnEvacuationAdapter;
    List<EvacuationData> evacuationDataList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.extradition_fragment_policeman, container, false);
        initViews(savedInstanceState);
        return  mView;
    }

    private void initViews(Bundle bundle){
        imvLoading = (ImageView)mView.findViewById(R.id.imvLoading);
        rvListCarOnEvacuation = (RecyclerView) mView.findViewById(R.id.rvCarOnEvacuationList);
        carOnEvacuationAdapter = new CarOnEvacuationAdapter();
        networkDataManager = new NetworkDataManager(this);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rvListCarOnEvacuation.setLayoutManager(llm);
        rvListCarOnEvacuation.setAdapter(carOnEvacuationAdapter);
        rvListCarOnEvacuation.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(view.getContext(), OwnerDataActivity.class);
                intent.putExtra("evacuationData", evacuationDataList.get(position));
                view.getContext().startActivity(intent);
            }
        }));
        actvOrganization = (AutoCompleteTextView)mView.findViewById(R.id.actvOrganization);
        actvPoliceman = (AutoCompleteTextView)mView.findViewById(R.id.actvPoliceman);
        actvPoliceDepartment = (AutoCompleteTextView)mView.findViewById(R.id.actvPoliceDepartment);
        actvPoliceman.setEnabled(!actvPoliceDepartment.getText().toString().equals(""));
        actvWrecker = (AutoCompleteTextView)mView.findViewById(R.id.actvWrecker);
        actvWrecker.setEnabled(!actvOrganization.getText().toString().equals(""));
        edtCarId = (EditText)mView.findViewById(R.id.edtCarID);
        btnFind = (Button)mView.findViewById(R.id.btnFindDetection);
        btnFind.setOnClickListener(this);
        llSearch = (LinearLayout)mView.findViewById(R.id.llSearch);
        int userType = UserManager.getInstanse().getUserType();
        RetrofitService retrofitService = Api.createRetrofitService();
        MyCallback<GetCarOnEvacuationResponseEnvelope> call = new MyCallback<>();
        call.addResponseListener(this);
        switch (userType){
            case 1:
                actvPoliceDepartment.setVisibility(View.GONE);
                actvPoliceman.setVisibility(View.GONE);
                retrofitService.executeGetCarOnEvacuation(
                        Encode.getBasicAuthTemplate(
                                UserManager.getInstanse().getmLogin(),
                                UserManager.getInstanse().getmPassword()
                        ),
                        new GetCarOnEvacuationRequestEnvelope(UserManager.getInstanse().getUserType(), "",UserManager.getInstanse().getmLogin(),"","", "")
                ).enqueue(call);
                setLoading(true);
                break;
            case 2:
                actvOrganization.setVisibility(View.GONE);
                actvWrecker.setVisibility(View.GONE);
                retrofitService.executeGetCarOnEvacuation(
                        Encode.getBasicAuthTemplate(
                                UserManager.getInstanse().getmLogin(),
                                UserManager.getInstanse().getmPassword()
                        ),
                        new GetCarOnEvacuationRequestEnvelope(UserManager.getInstanse().getUserType(), "","","",UserManager.getInstanse().getmLogin(), "")
                ).enqueue(call);
                setLoading(true);
                break;
            case 3:
                setLoading(true);
                break;
        }
        networkDataManager.getDefaultData();



    }

    private void setLoading(Boolean bool) {
        AnimationDrawable animation = (AnimationDrawable) imvLoading.getBackground();
        if(bool){
            llSearch.setVisibility(View.GONE);
            imvLoading.setVisibility(View.VISIBLE);
            animation.start();
            rvListCarOnEvacuation.setVisibility(View.GONE);
        }else{
            imvLoading.setVisibility(View.GONE);
            animation.stop();

            rvListCarOnEvacuation.setVisibility(View.VISIBLE);
                llSearch.setVisibility(View.VISIBLE);


        }
    }

    @Override
    public void onNetworkDataUpdate(final NetworkDataManager netDataManager) {
        if(netDataManager!=null) {
            this.networkDataManager = netDataManager;
            ArrayAdapter<String> arrayAdapterPoliceDepartment = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, networkDataManager.getPoliceDepartmentListAsStirng());
            actvPoliceDepartment.setAdapter(arrayAdapterPoliceDepartment);
            actvPoliceman.setEnabled(!actvPoliceDepartment.getText().toString().equals(""));
            actvPoliceDepartment.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    actvPoliceman.setEnabled(!actvPoliceDepartment.getText().toString().equals(""));
                    actvPoliceman.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, networkDataManager.getPolicemanListAsString(editable.toString())));
                }
            });
            actvPoliceman.setAdapter(new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, netDataManager.getPolicemanListAsString(actvPoliceDepartment.getText().toString())));

            ArrayAdapter<String> arrayAdapterOrganization = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, networkDataManager.getOrganizationListAsString());
            actvOrganization.setAdapter(arrayAdapterOrganization);
            actvOrganization.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    actvWrecker.setEnabled(!actvOrganization.getText().toString().equals(""));
                    actvWrecker.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, networkDataManager.getWreckerListAsStirng(actvOrganization.getText().toString())));
                }
            });
            actvWrecker.setAdapter(new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, netDataManager.getWreckerListAsStirng(actvOrganization.getText().toString())));

            setLoading(false);
        }
    }

    @Override
    public void AuthorizationOK(Response response) {
        GetCarOnEvacuationResponseEnvelope responseEnvelope = (GetCarOnEvacuationResponseEnvelope) response.body();
        evacuationDataList = responseEnvelope.getDataList().getData().getEvacuationDataList();
        carOnEvacuationAdapter.addListEvacuationData(evacuationDataList);
        setLoading(false);
    }

    @Override
    public void AuthorizationBad(Response response) {
        Toast.makeText(getActivity(), "Bad", Toast.LENGTH_SHORT).show();
        setLoading(false);
    }

    @Override
    public void AuthorizationFail(Throwable t) {
        Toast.makeText(getActivity(), "FAil", Toast.LENGTH_SHORT).show();
        setLoading(false);
    }

    @Override
    public void onClick(View view) {
        if(carOnEvacuationAdapter!=null) {
            carOnEvacuationAdapter.clear();
        }
        Utils.hideKeyboard(getActivity());
        RetrofitService retrofitService = Api.createRetrofitService();
        MyCallback<GetCarOnEvacuationResponseEnvelope> call = new MyCallback<>();
        call.addResponseListener(this);
        String polDep = actvPoliceDepartment.getText().toString();
        String policeman = actvPoliceman.getText().toString();
        String organization = actvOrganization.getText().toString();
        String wrecker = actvWrecker.getText().toString();
        String carId = edtCarId.getText().toString();
        switch (UserManager.getInstanse().getUserType()){
            case 1:
                polDep = "";
                policeman = UserManager.getInstanse().getmLogin();
                break;

            case 2:
                organization = "";
                wrecker = UserManager.getInstanse().getmLogin();
                break;

            case 3:
                break;
        }
        retrofitService.executeGetCarOnEvacuation(
                Encode.getBasicAuthTemplate(
                        UserManager.getInstanse().getmLogin(),
                        UserManager.getInstanse().getmPassword()
                ),
                new GetCarOnEvacuationRequestEnvelope(

                        UserManager.getInstanse().getUserType(),
                        polDep,
                        policeman,
                        organization,
                        wrecker,
                        carId)
        ).enqueue(call);
        setLoading(true);
    }
}
