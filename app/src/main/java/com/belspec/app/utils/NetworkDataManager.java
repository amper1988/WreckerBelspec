package com.belspec.app.utils;

import com.belspec.app.interfaces.MyCallback;
import com.belspec.app.interfaces.NetworkDataUpdate;
import com.belspec.app.interfaces.ResponseListener;
import com.belspec.app.retrofit.Api;
import com.belspec.app.retrofit.RetrofitService;
import com.belspec.app.retrofit.model.Clause;
import com.belspec.app.retrofit.model.Color;
import com.belspec.app.retrofit.model.Manufacture;
import com.belspec.app.retrofit.model.Model;
import com.belspec.app.retrofit.model.ModelItem;
import com.belspec.app.retrofit.model.Organization;
import com.belspec.app.retrofit.model.Parking;
import com.belspec.app.retrofit.model.PoliceDepartment;
import com.belspec.app.retrofit.model.Policeman;
import com.belspec.app.retrofit.model.PolicemanItem;
import com.belspec.app.retrofit.model.Position;
import com.belspec.app.retrofit.model.Rank;
import com.belspec.app.retrofit.model.Wrecker;
import com.belspec.app.retrofit.model.WreckerItem;
import com.belspec.app.retrofit.model.getDefaultData.request.GetDefaultDataRequestEnvelope;
import com.belspec.app.retrofit.model.getDefaultData.response.GetDefaultDataResponseEnvelope;
import com.belspec.app.retrofit.model.getPoliceDepartment.request.GetPoliceDepartmentRequestEnvelope;
import com.belspec.app.retrofit.model.getPoliceDepartment.response.GetPoliceDepartmentResponseEnvelope;
import com.belspec.app.retrofit.model.getPositions.request.GetPositionsRequestEnvelope;
import com.belspec.app.retrofit.model.getPositions.response.GetPositionsResponseEnvelope;
import com.belspec.app.retrofit.model.getRanks.request.GetRanksRequestEnvelope;
import com.belspec.app.retrofit.model.getRanks.response.GetRanksResponseEnvelope;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class NetworkDataManager implements ResponseListener {
    private static NetworkDataManager instance;
    private List<PoliceDepartment> policeDepartmentList = new ArrayList<>();
    private List<Parking> parkingList = new ArrayList<>();
    private List<Manufacture> manufactureList = new ArrayList<>();
    private List<Clause> clauseList = new ArrayList<>();
    private List<Organization> organizationList = new ArrayList<>();
    private List<Color> colorList = new ArrayList<>();
    private List<NetworkDataUpdate> listeners = new ArrayList<>();
    private List<Rank> rankList = new ArrayList<>();
    private List<Position> positionList = new ArrayList<>();

    public void setListener(NetworkDataUpdate ndu){
        if(!listeners.contains(ndu)){
            listeners.add(ndu);
        }
    }

    public void unregister(NetworkDataUpdate ndu){
        if(listeners.contains(ndu)){
            listeners.remove(ndu);
        }
    }
    private NetworkDataManager(){
       super();
    }

    public static NetworkDataManager getInstance(){
        if(instance == null){
            instance = new NetworkDataManager();

        }
        return instance;
    }

    public void getDefaultData(){
        RetrofitService retrofitService = Api.createRetrofitService();
        MyCallback<GetDefaultDataResponseEnvelope> call = new MyCallback<>();
        call.addResponseListener(this);
        retrofitService.executeGetDefaultData(
                Encode.getBasicAuthTemplate(
                        UserManager.getInstanse().getmLogin(),
                        UserManager.getInstanse().getmPassword()
                ),
                new GetDefaultDataRequestEnvelope()
        ).enqueue(call);
    }

    public void getRanksListFromServer(){
        RetrofitService retrofitService = Api.createRetrofitService();
        MyCallback<GetRanksResponseEnvelope> call = new MyCallback<>();
        call.addResponseListener(this);
        retrofitService.executeGetRanks(
                Encode.getBasicAuthTemplate(
                        UserManager.getInstanse().getmLogin(),
                        UserManager.getInstanse().getmPassword()
                ),
                new GetRanksRequestEnvelope()
        ).enqueue(call);
    }

    public void getPositionsFromServer(){
        RetrofitService retrofitService = Api.createRetrofitService();
        MyCallback<GetPositionsResponseEnvelope> call = new MyCallback<>();
        call.addResponseListener(this);
        retrofitService.executeGetPositions(
                Encode.getBasicAuthTemplate(
                        UserManager.getInstanse().getmLogin(),
                        UserManager.getInstanse().getmPassword()
                ),
                new GetPositionsRequestEnvelope()
        ).enqueue(call);
    }

    public void getPoliceDepartmetFromServer(){
        RetrofitService retrofitService = Api.createRetrofitService();
        MyCallback<GetPoliceDepartmentResponseEnvelope> call = new MyCallback<>();
        call.addResponseListener(this);
        retrofitService.executeGetPoliceDepartment(
                Encode.getBasicAuthTemplate(
                        UserManager.getInstanse().getmLogin(),
                        UserManager.getInstanse().getmPassword()
                ),
                new GetPoliceDepartmentRequestEnvelope()
        ).enqueue(call);
    }

    public List<Manufacture> getManufactureList(){
        return manufactureList;
    }

    public List<String> getManufactureListAsString(){
        if(manufactureList!=null){
            ArrayList<String> arrayList = new ArrayList<>();
            for(Manufacture manufacture : manufactureList){
                arrayList.add(manufacture.getName());
            }
            return arrayList;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        return arrayList;
    }

    public List<String> getModelListAsStirng(int pos){
        if (manufactureList!= null){
            ModelItem modelItem = manufactureList.get(pos).getModelItemList();
            if(modelItem != null) {
                List<Model> modelList = modelItem.getModelList();
                ArrayList<String> arrayList = new ArrayList<>();
                for (Model model : modelList) {
                    arrayList.add(model.getName());
                }
                return arrayList;
            }
        }
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        return arrayList;

    }

    public List<String> getModelListAsString(String manufacture){
        if(manufactureList!=null){
            for(Manufacture manuf: manufactureList){
                if(manuf.getName().equals(manufacture)){
                    return getModelListAsStirng(manufactureList.indexOf(manuf));
                }
            }
        }
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        return arrayList;

    }

    public List<Clause> getClauseList(){
        return clauseList;
    }

    public List<String> getClauseListAsString(){
        if(clauseList!=null){
            ArrayList<String> arrayList = new ArrayList<>();
            for (Clause clause: clauseList ){
                arrayList.add(clause.getName());
            }
            return arrayList;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        return arrayList;

    }

    public List<PoliceDepartment> getPoliceDepartmentList(){
        return policeDepartmentList;
    }

    public List<String> getPoliceDepartmentListAsStirng(){
        if(policeDepartmentList!=null){
            ArrayList<String> arrayList = new ArrayList<>();
            for(PoliceDepartment polDep: policeDepartmentList){
                arrayList.add(polDep.getName());
            }
            return arrayList;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        return arrayList;

    }

    public List<String> getPolicemanListAsStirng(int pos){
        if(policeDepartmentList!=null){
            PolicemanItem policemanItem = policeDepartmentList.get(pos).getPolicemanItemList();
            if(policemanItem!=null){
                List<Policeman> policemanList = policemanItem.getPolicemanList();
                ArrayList<String> arrayList = new ArrayList<>();
                for(Policeman policeman: policemanList){
                    arrayList.add(policeman.getName());
                }
                return arrayList;
            }
        }
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        return arrayList;

    }

    public List<String> getPolicemanListAsString(String polDep){
        if(policeDepartmentList!=null){
            for(PoliceDepartment policeDepartment: policeDepartmentList){
                if(policeDepartment.getName().equals(polDep)){
                    return getPolicemanListAsStirng(policeDepartmentList.indexOf(policeDepartment));
                }
            }
        }
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        return arrayList;

    }

    public List<Parking> getParkingList(){
        return parkingList;
    }

    public List<String> getParkingListAsString(){
        if(parkingList!=null){
            ArrayList<String> arrayList = new ArrayList<>();
            for(Parking parking: parkingList){
                arrayList.add(parking.getName());
            }
            return arrayList;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        return arrayList;

    }

    public List<Organization> getOrganizationList(){
        return organizationList;
    }

    public List<String> getOrganizationListAsString(){
        if(organizationList!=null){
            ArrayList<String> arrayList = new ArrayList<>();
            for(Organization organization: organizationList){
                arrayList.add(organization.getName());
            }
            return arrayList;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        return arrayList;

    }

    public List<String> getWreckerListAsStirng(int pos){
        if(organizationList!=null){
            WreckerItem wreckerItem = organizationList.get(pos).getWreckerItemList();
            if(wreckerItem!=null){
                ArrayList<String> arrayList = new ArrayList<>();
                List<Wrecker> wreckerList = wreckerItem.getWreckerList();
                for (Wrecker wrecker: wreckerList){
                    arrayList.add(wrecker.getName());
                }
                return arrayList;
            }
        }
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        return arrayList;

    }

    public List<String> getWreckerListAsStirng(String organiz){
        if(organizationList!=null){
            for(Organization organization: organizationList){
                if(organization.getName().equals(organiz)){
                    return getWreckerListAsStirng(organizationList.indexOf(organization));
                }
            }
        }
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        return arrayList;
    }

    public List<Color> getColorList(){
        return this.colorList;
    }

    public List<String> getColorListAsString(){
        if(this.colorList != null){
            ArrayList<String> arrayList = new ArrayList<>();
            for(Color color: colorList){
                arrayList.add(color.getName());
            }
            return arrayList;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        return arrayList;
    }

    public List<NetworkDataUpdate> getListeners() {
        return listeners;
    }

    public List<String> getRankListAsString(){
        if(this.rankList!=null){
            ArrayList<String> arrayList = new ArrayList<>();
            for(Rank rank: rankList){
                arrayList.add(rank.getName());
            }
            return arrayList;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        return arrayList;
    }

    public List<Rank> getRankList() {
        return rankList;
    }

    public List<Position> getPositionList() {
        return positionList;
    }

    public List<String> getPositionListAsString(){
        if(this.positionList!=null){
            ArrayList<String> arrayList = new ArrayList<>();
            for(Position position: positionList){
                arrayList.add(position.getName());
            }
            return arrayList;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        return arrayList;
    }

    @Override
    public void AuthorizationOK(Response response) {
        if(response.body().getClass() == GetDefaultDataResponseEnvelope.class){
            GetDefaultDataResponseEnvelope responseEnvelope = (GetDefaultDataResponseEnvelope)response.body();
            this.manufactureList = responseEnvelope.getBody().getManufactureList();
            this.policeDepartmentList = responseEnvelope.getBody().getPoliceDepartmentList();
            this.parkingList = responseEnvelope.getBody().getParkingList();
            this.clauseList = responseEnvelope.getBody().getClauseList();
            this.organizationList = responseEnvelope.getBody().getOrganizationList();
            this.colorList = responseEnvelope.getBody().getColorList();
            if (listeners != null) {
                for (NetworkDataUpdate listener:listeners) {
                    listener.onDefaultDataUpdate(this);
                }
            }
        }
        if(response.body().getClass() == GetRanksResponseEnvelope.class){
            GetRanksResponseEnvelope responseEnvelope = (GetRanksResponseEnvelope) response.body();
            this.rankList = responseEnvelope.getBody().getRankList();
            if(listeners !=null){
                for (NetworkDataUpdate listener:listeners) {
                    listener.onRanksUpdate(this.rankList);
                }
            }
        }
        if(response.body().getClass() == GetPositionsResponseEnvelope.class){
            GetPositionsResponseEnvelope responseEnvelope = (GetPositionsResponseEnvelope) response.body();
            this.positionList = responseEnvelope.getBody().getPositionList();
            if(listeners !=null){
                for (NetworkDataUpdate listener:listeners) {
                    listener.onPositionsUpdate(this.positionList);
                }
            }
        }
        if(response.body().getClass() == GetPoliceDepartmentResponseEnvelope.class){
            GetPoliceDepartmentResponseEnvelope responseEnvelope = (GetPoliceDepartmentResponseEnvelope) response.body();
            this.policeDepartmentList = responseEnvelope.getBody().getPoliceDepartment();
            if(listeners !=null){
                for (NetworkDataUpdate listener:listeners) {
                    listener.onPoliceDepartmentUpdate(this.policeDepartmentList);
                }
            }

        }
    }

    @Override
    public void AuthorizationBad(Response response) {
    if(listeners !=null){

    }

    }

    @Override
    public void AuthorizationFail(Throwable t) {
        if(listeners !=null){

        }
    }
}
