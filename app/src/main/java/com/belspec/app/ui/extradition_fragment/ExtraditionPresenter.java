package com.belspec.app.ui.extradition_fragment;

import com.belspec.app.adapters.CarOnEvacuationAdapter;
import com.belspec.app.interfaces.NetworkDataUpdate;
import com.belspec.app.retrofit.Api;
import com.belspec.app.retrofit.RetrofitService;
import com.belspec.app.retrofit.model.getCarOnEvacuation.request.GetCarOnEvacuationRequestEnvelope;
import com.belspec.app.retrofit.model.getCarOnEvacuation.response.EvacuationData;
import com.belspec.app.retrofit.model.getCarOnEvacuation.response.GetCarOnEvacuationResponseEnvelope;
import com.belspec.app.ui.owner_data.OwnerDataSendEvent;
import com.belspec.app.utils.Encode;
import com.belspec.app.utils.NetworkDataManager;
import com.belspec.app.utils.UserManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ExtraditionPresenter implements ExtraditionContract.Presenter, NetworkDataUpdate {
    private ExtraditionContract.View mView;
    private CarOnEvacuationAdapter carOnEvacuationAdapter;

    ExtraditionPresenter(ExtraditionContract.View view){
        this.mView = view;
        carOnEvacuationAdapter = new CarOnEvacuationAdapter();
    }

    @Override
    public void onCreate() {
        mView.setLoading(true);
        NetworkDataManager.getInstance().setListener(this);
        NetworkDataManager.getInstance().getDefaultData();
    }

    @Override
    public void onDestroy() {
        NetworkDataManager.getInstance().unregister(this);
    }

    @Override
    public void onFindClick() {
        mView.setLoading(true);
        if(carOnEvacuationAdapter!=null) {
            carOnEvacuationAdapter.clear();
        }
        RetrofitService retrofitService = Api.createRetrofitService();
        retrofitService.executeGetCarOnEvacuation(
                Encode.getBasicAuthTemplate(
                    UserManager.getInstanse().getmLogin(),
                    UserManager.getInstanse().getmPassword()
            ),
                new GetCarOnEvacuationRequestEnvelope(
                        UserManager.getInstanse().getUserType(),
                        mView.getPoliceDepartment(),
                        mView.getPoliceman(),
                        mView.getOrganization(),
                        mView.getWrecker(),
                        mView.getCarId()
                )
        ).enqueue(new Callback<GetCarOnEvacuationResponseEnvelope>() {
            @Override
            public void onResponse(Call<GetCarOnEvacuationResponseEnvelope> call, Response<GetCarOnEvacuationResponseEnvelope> response) {
                mView.setLoading(false);
                if(response.code() == 200){
                    List<EvacuationData> dataList = response.body().getDataList().getData().getEvacuationDataList();
                    if(dataList != null)
                        carOnEvacuationAdapter.addListEvacuationData(dataList);
                    else
                        mView.showMessage("Значений нет.");
                    mView.setAdapter(carOnEvacuationAdapter);
                }else{
                    mView.showMessage("Ошбика сервера "+response.code());
                }
            }

            @Override
            public void onFailure(Call<GetCarOnEvacuationResponseEnvelope> call, Throwable t) {
                mView.setLoading(false);
                mView.showMessage(t.getMessage());
            }
        });
    }

    @Override
    public int getUserType() {
        return UserManager.getInstanse().getUserType();
    }

    @Override
    public void getPolicemans(String policeDepartment) {
        mView.setPolicemans(NetworkDataManager.getInstance().getPolicemanListAsString(policeDepartment));
    }

    @Override
    public void getWreckers(String organization) {
        mView.setWreckers(NetworkDataManager.getInstance().getWreckerListAsStirng(organization));
    }

    @Override
    public String getManufactureFromAdapter(int position) {
        EvacuationData evacuationData = carOnEvacuationAdapter.getEvacuationData(position);
        if(evacuationData != null){
            return evacuationData.getManufacture();
        }
        return "";
    }

    @Override
    public String getModelFromAdapter(int position) {
        EvacuationData evacuationData = carOnEvacuationAdapter.getEvacuationData(position);
        if(evacuationData != null){
            return evacuationData.getModel();
        }
        return "";
    }

    @Override
    public String getCarIdFromAdapter(int position) {
        EvacuationData evacuationData = carOnEvacuationAdapter.getEvacuationData(position);
        if(evacuationData != null){
            return evacuationData.getCarId();
        }
        return "";
    }

    @Override
    public String getPhotoFromAdapter(int position) {
        EvacuationData evacuationData = carOnEvacuationAdapter.getEvacuationData(position);
        if(evacuationData != null){
            return evacuationData.getPhoto1();
        }
        return "";
    }

    @Override
    public int getDocIdFromAdapter(int position) {
        EvacuationData evacuationData = carOnEvacuationAdapter.getEvacuationData(position);
        if(evacuationData != null){
            return evacuationData.getId();
        }
        return -1;
    }

    @Override
    public void onOwnerDataStart() {
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Subscribe
    void onEvent(OwnerDataSendEvent event){
        EventBus.getDefault().unregister(this);
        if(event.getDocId() != -1){
            int docId = event.getDocId();
            for (int i=0; i <= carOnEvacuationAdapter.getItemCount(); i++){
                EvacuationData evacuationData = carOnEvacuationAdapter.getEvacuationData(i);
                if(evacuationData.getId() == docId){
                    carOnEvacuationAdapter.removeEvacuationData(evacuationData);
                }
            }
        }
    }


    @Override
    public void onDefaultDataUpdate(NetworkDataManager netDataManager) {
        mView.setOrganizations(netDataManager.getOrganizationListAsString());
        mView.setPoliceDepartments(netDataManager.getPoliceDepartmentListAsString());
        switch (UserManager.getInstanse().getUserType()){
            case 1:
                mView.setPoliceDepartment(UserManager.getInstanse().getOrganization());
                mView.setPoliceman(UserManager.getInstanse().getmFullName());
                break;
            case 2:
                mView.setOrganization(UserManager.getInstanse().getOrganization());
                mView.setWrecker(UserManager.getInstanse().getmFullName());
                break;
        }
        mView.setLoading(false);
    }

    @Override
    public void onRanksUpdate(NetworkDataManager netDataManager) {

    }

    @Override
    public void onPositionsUpdate(NetworkDataManager netDataManager) {

    }

    @Override
    public void onPoliceDepartmentUpdate(NetworkDataManager netDataManager) {

    }

    @Override
    public void onRoadLowPointUpdate(NetworkDataManager netDataManager) {

    }
}
