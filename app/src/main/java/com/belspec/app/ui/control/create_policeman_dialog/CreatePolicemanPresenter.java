package com.belspec.app.ui.control.create_policeman_dialog;

import com.belspec.app.interfaces.NetworkDataUpdate;
import com.belspec.app.retrofit.Api;
import com.belspec.app.retrofit.RetrofitService;
import com.belspec.app.retrofit.model.createPoliceman.request.CreatePolicemanRequestEnvelope;
import com.belspec.app.retrofit.model.createPoliceman.response.CreatePolicemanResponseEnvelope;
import com.belspec.app.utils.Encode;
import com.belspec.app.utils.NetworkDataManager;
import com.belspec.app.utils.UserManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class CreatePolicemanPresenter implements CreatePolicemanContract.Presenter, NetworkDataUpdate {
    private CreatePolicemanContract.View mView;
    private boolean rankReceived;
    private boolean policeDepartmentReceived;
    private boolean positionReceived;

    CreatePolicemanPresenter(CreatePolicemanContract.View view){
        this.mView = view;
    }

    @Override
    public void onCreateDialog() {
        rankReceived = false;
        policeDepartmentReceived = false;
        positionReceived = false;
        mView.setLoading(true);
        NetworkDataManager.getInstance().setListener(this);
        NetworkDataManager.getInstance().getRanksListFromServer();
        NetworkDataManager.getInstance().getPoliceDepartmentFromServer();
        NetworkDataManager.getInstance().getPositionsFromServer();
    }

    @Override
    public void onDestroy() {
        NetworkDataManager.getInstance().unregister(this);
    }

    @Override
    public void onRegisterClick() {
        if(confirmData()){
            mView.setLoading(true);
            RetrofitService retrofitService = Api.createRetrofitService();
            retrofitService.executeCreatePoliceman(
                    Encode.getBasicAuthTemplate(
                            UserManager.getInstanse().getmLogin(),
                            UserManager.getInstanse().getmPassword()
                    ),
                    new CreatePolicemanRequestEnvelope(
                            mView.getName(),
                            mView.getPoliceDepartment(),
                            mView.getRank(),
                            mView.getPosition(),
                            mView.getCode()
                    )
            ).enqueue(new Callback<CreatePolicemanResponseEnvelope>() {
                @Override
                public void onResponse(Call<CreatePolicemanResponseEnvelope> call, Response<CreatePolicemanResponseEnvelope> response) {
                    if(response.code() == 200){
                        CreatePolicemanResponseEnvelope responseEnvelope =response.body();
                        if(responseEnvelope.getData().getCode() == 1){
                            mView.showMessage(responseEnvelope.getData().getDescription());
                            mView.showDialogMessage(responseEnvelope.getData().getDescription());
                            mView.close();
                            NetworkDataManager.getInstance().getDefaultData();
                        }else{
                            mView.showMessage("Fault. Code: " + responseEnvelope.getData().getCode() + "Server answer: "+responseEnvelope.getData().getDescription());
                            mView.showDialogMessage("Fault. Code: " + responseEnvelope.getData().getCode() + "Server answer: "+responseEnvelope.getData().getDescription());
                        }
                    }else{
                        mView.showMessage(response.message());
                        mView.showDialogMessage(response.message());
                    }
                }

                @Override
                public void onFailure(Call<CreatePolicemanResponseEnvelope> call, Throwable t) {
                    mView.showMessage(t.getMessage());
                    mView.showDialogMessage(t.getMessage());
                }
            });
        }
    }

    private boolean confirmData(){
        if(mView.getPoliceDepartment().equals("")){
            mView.setErrorPoliceDepartment("Значение не может быть пустым");
            return false;
        }
        if(mView.getRank().equals("")){
            mView.setErrorRanks("Значение не может быть пустым");
            return false;
        }
        if(mView.getPosition().equals("")){
            mView.setErrorPosition("Значение не может быть пустым");
            return false;
        }
        if(mView.getName().equals("")){
            mView.setErrorName("Введите имя фамилию отчество полностью");
            return false;
        }
        if(mView.getCode().equals("")){
            mView.setErrorCode("Код не может быть пустым");
            return false;
        }

        return true;
    }

    @Override
    public void onDefaultDataUpdate(NetworkDataManager netDataManager) {

    }

    @Override
    public void onRanksUpdate(NetworkDataManager netDataManager) {
        rankReceived = true;
        ArrayList<String> array = new ArrayList<>();
        array.add("");
        array.addAll(netDataManager.getRankListAsString());
        mView.setRanks(array);
        stopLoading();
    }

    @Override
    public void onPositionsUpdate(NetworkDataManager netDataManager) {
        positionReceived = true;
        ArrayList<String> array = new ArrayList<>();
        array.add("");
        array.addAll(netDataManager.getPositionListAsString());
        mView.setPosition(array);
        stopLoading();
    }

    @Override
    public void onPoliceDepartmentUpdate(NetworkDataManager netDataManager) {
        policeDepartmentReceived = true;
        ArrayList<String> array = new ArrayList<>();
        array.add("");
        array.addAll(netDataManager.getPoliceDepartmentOnlyListAsString());
        mView.setPoliceDepartment(array);
        stopLoading();
    }

    @Override
    public void onRoadLowPointUpdate(NetworkDataManager netDataManager) {

    }

    private void stopLoading(){
            mView.setLoading(rankReceived && positionReceived && policeDepartmentReceived);
    }
}
