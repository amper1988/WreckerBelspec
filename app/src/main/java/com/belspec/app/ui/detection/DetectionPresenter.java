package com.belspec.app.ui.detection;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import com.belspec.app.adapters.ImageListAdapter;
import com.belspec.app.gps.GPSTracker;
import com.belspec.app.interfaces.NetworkDataUpdate;
import com.belspec.app.retrofit.Api;
import com.belspec.app.retrofit.RetrofitService;
import com.belspec.app.retrofit.model.createEvacuation.request.CreateEvacuationRequestEnvelope;
import com.belspec.app.retrofit.model.createEvacuation.response.CreateEvacuationResponseEnvelope;
import com.belspec.app.ui.detection.signature_dialog.SignatureEvent;
import com.belspec.app.ui.detection.witness_dialog.WitnessSendEvent;
import com.belspec.app.utils.Converter;
import com.belspec.app.utils.Encode;
import com.belspec.app.utils.FileManager;
import com.belspec.app.utils.NetworkDataManager;
import com.belspec.app.utils.UserManager;
import com.belspec.app.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class DetectionPresenter implements DetectionContract.Presenter, GPSTracker.LocationDataChangeListener, NetworkDataUpdate {
    private GPSTracker gpsTracker;
    private DetectionContract.View view;
    private boolean getSign = false;
    private int witnessIndex = -1;
    private Context context;
    private int docId;
    private ImageListAdapter imageListAdapter;
    private boolean registerInProgress;

    DetectionPresenter(DetectionContract.View view) {
        this.view = view;
        docId = view.getDocId();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.context = view.getContext();
        startGPSTracker();
        startNetworkDataUpdate();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        initializeListsFromServer();

    }

    @Override
    public void onResume() {
//        startGPSTracker();
//        stopNetworkDataUpdate();

    }

    @Override
    public void onPause() {
//        stopGPSTracker();
//        stopNetworkDataUpdate();
    }

    @Override
    public void onStop() {

    }

    @Subscribe
    @SuppressWarnings("unused")
    void onEvent(SignatureEvent event) {

        if (event.getSignature() != null && getSign) {
            view.setSignaturePol(event.getSignature());
            saveSignaturePolBackup(event.getSignature());
        }
        if (getSign) {
            getSign = false;
        }
    }

    @Override
    public void onDestroyView() {
        stopGPSTracker();
        stopNetworkDataUpdate();
        EventBus.getDefault().unregister(this);
    }

    //use for start GPSTracker
    private void startGPSTracker() {
        if (gpsTracker == null) {
            gpsTracker = GPSTracker.getInstance();
        }
        if (gpsTracker.canGetLocation())
            gpsTracker.startUsingGPS();
        else
            gpsTracker.showSettingsAlert();
        gpsTracker.setDataChangeListener(this);
    }

    //use for stop GPSTracker
    private void stopGPSTracker() {
        if (gpsTracker != null) {
            gpsTracker.unsetDataChangeListener(this);
        }
    }

    @Override
    public int getUserType() {
        return UserManager.getInstanse().getUserType();
    }

    @Override
    public void getImageListAdapterFromBackup() {
        imageListAdapter = new ImageListAdapter();
        String path1 = loadFilePath(docId, 1);
        String path2 = loadFilePath(docId, 2);
        String path3 = loadFilePath(docId, 3);
        String path4 = loadFilePath(docId, 4);
        if (!path1.equals("")) {
            imageListAdapter.addFilePath(path1);
            imageListAdapter.add(FileManager.loadBitmapFromPath(path1));
        }
        if (!path2.equals("")) {
            imageListAdapter.addFilePath(path2);
            imageListAdapter.add(FileManager.loadBitmapFromPath(path2));
        }
        if (!path3.equals("")) {
            imageListAdapter.addFilePath(path3);
            imageListAdapter.add(FileManager.loadBitmapFromPath(path3));
        }
        if (!path4.equals("")) {
            imageListAdapter.addFilePath(path4);
            imageListAdapter.add(FileManager.loadBitmapFromPath(path4));
        }
        view.setAdapter(imageListAdapter);
    }

    @Override
    public void getStreet() {
        view.setStreet(Utils.getAdress(context, gpsTracker.getLatitude(), gpsTracker.getLongitude()));
    }

    @Override
    public void getSignaturePol() {
        getSign = true;
    }

    @Override
    public void getWitness(int index) {
        witnessIndex = index;
    }

    @Override
    public Bitmap getBitmap(ImageView imageView) {
        return Converter.convertImageViewToBitmap(imageView);
    }

    @Subscribe
    @SuppressWarnings("unused")
    void OnEvent(WitnessSendEvent event) {
        Witness witness = event.getWitness();
        if (witness == null)
            witnessIndex = -1;
        if (event.isForAll() && witnessIndex == -1) {
            if(witness != null){
                if (view.canSetWitness(1)) {
                    saveWitnessBackup(1, witness);
                    view.setWitnessAddress( witness.getAddress(), 1);
                    view.setWitnessName(witness.getName(), 1);
                    view.setWitnessContact(witness.getContact(), 1);
                    view.setWitnessPlea(witness.getPlea(), 1);
                    view.setWitnessSignature(witness.getSignature(), 1);
                } else if (view.canSetWitness(2)) {
                    if(!(view.getWitnessName(1).equals(witness.getName()) && view.getWitnessName(1).equals(witness.getAddress()))){
                        saveWitnessBackup(2, witness);
                        view.setWitnessAddress(witness.getAddress(), 2);
                        view.setWitnessName(witness.getName(), 2);
                        view.setWitnessContact(witness.getContact(), 2);
                        view.setWitnessPlea(witness.getPlea(), 2);
                        view.setWitnessSignature(witness.getSignature(), 2);
                    }
                }
            }
        }
        if (witnessIndex != -1) {
            if(witness != null){
                saveWitnessBackup(witnessIndex, witness);
                view.setWitnessName(witness.getName(), witnessIndex);
                view.setWitnessAddress(witness.getAddress(), witnessIndex);
                view.setWitnessContact(witness.getContact(), witnessIndex);
                view.setWitnessPlea(witness.getPlea(), witnessIndex);
                view.setWitnessSignature(witness.getSignature(), witnessIndex);
            }
            witnessIndex = -1;
        }


    }

    @Override
    public void saveManufactureBackup(String manufacture) {
        PrefDefaultValue.saveManufacture(context, docId, manufacture);
    }

    @Override
    public void saveModelBackup(String model) {
        PrefDefaultValue.saveModel(context, docId, model);
    }

    @Override
    public void saveCarIdBackup(String carId) {
        PrefDefaultValue.saveCarId(context, docId, carId);

    }

    @Override
    public void saveColorBackup(String color) {
        PrefDefaultValue.saveColor(context, docId, color);
    }

    @Override
    public void saveWithoutEvacuationBackup(int checked) {
        PrefDefaultValue.saveWithoutEvacuation(context, docId, checked);
    }

    @Override
    public void saveRoadLawPointBackup(String roadLawPoint) {
        PrefDefaultValue.saveRoadLawPoint(context, docId, roadLawPoint);
    }

    @Override
    public void saveClauseBackup(String clause) {
        PrefDefaultValue.saveClause(context, docId, clause);
    }

    @Override
    public void saveRevisionResultBackup(String revisionResult) {
        PrefDefaultValue.saveRevisionResult(context, docId, revisionResult);
    }

    private void saveWitnessBackup(int index, Witness witness) {
        PrefDefaultValue.saveWitness(context, docId, index, witness.getName(), witness.getAddress(), witness.getSignature(), witness.getContact(), witness.getPlea());
    }

    @Override
    public void saveSignaturePolBackup(Bitmap signature) {
        PrefDefaultValue.saveSignaturePol(context, docId, signature);
    }

    @Override
    public void saveOrganizationBackup(String organization) {
        PrefDefaultValue.saveOrganization(context, docId, organization);
    }

    @Override
    public void saveWreckerBackup(String wrecker) {
        PrefDefaultValue.saveWrecker(context, docId, wrecker);
    }

    @Override
    public void savePoliceDepartmentBackup(String policeDepartment) {
        PrefDefaultValue.savePoliceDepartment(context, docId, policeDepartment);
    }

    @Override
    public void savePolicemanBackup(String policeman) {
        PrefDefaultValue.savePoliceman(context, docId, policeman);
    }

    @Override
    public void saveParkingBackup(String parking) {
        PrefDefaultValue.saveParking(context, docId, parking);
    }

    @Override
    public void saveStreetBackup(String street) {
        PrefDefaultValue.saveStreet(context, docId, street);
    }


    private String loadFilePath(int docId, int index) {
        return PrefDefaultValue.loadFilePath(context, docId, index);
    }

    @Override
    public void loadManufactureBackup() {
        view.setManufacture(PrefDefaultValue.loadManufacture(context, docId));
    }

    @Override
    public void loadModelBackup() {
        view.setModel(PrefDefaultValue.loadModel(context, docId));
    }

    @Override
    public void loadCarIdBackup() {
        view.setCarId(PrefDefaultValue.loadCarId(context, docId));
    }

    @Override
    public void loadColorBackup() {
        view.setColor(PrefDefaultValue.loadColor(context, docId));
    }

    @Override
    public void loadWithoutEvacuationBackup() {
        view.setWithoutEvacuation(PrefDefaultValue.loadWithoutEvacuation(context, docId) == 1);
    }

    @Override
    public void loadRoadLawPointBackup() {
        view.setRoadLawPoint(PrefDefaultValue.loadRoadLawPoint(context, docId));
    }

    @Override
    public void loadClauseBackup() {
        view.setClause(PrefDefaultValue.loadClause(context, docId));
    }

    @Override
    public void loadStreetBackup() {
        view.setStreet(PrefDefaultValue.loadStreet(context, docId));
    }

    @Override
    public void loadListPoliceman(String policeDepartment) {
        view.setListPoliceman(NetworkDataManager.getInstance().getPolicemanListAsString(policeDepartment));
    }

    @Override
    public void loadListModel(String manufacture) {
        view.setListModels(NetworkDataManager.getInstance().getModelListAsString(manufacture));
    }

    @Override
    public void loadListWrecker(String organization) {
        view.setListWrecker(NetworkDataManager.getInstance().getWreckerListAsStirng(organization));
    }

    @Override
    public void clearImages() {
        imageListAdapter.clear();
        PrefDefaultValue.saveFilePath(context, docId, 1, "");
        PrefDefaultValue.saveFilePath(context, docId, 2, "");
        PrefDefaultValue.saveFilePath(context, docId, 3, "");
        PrefDefaultValue.saveFilePath(context, docId, 4, "");
        view.setAdapter(imageListAdapter);
    }

    @Override
    public boolean imageIsEmpty(ImageView imageView) {
        return Utils.DrawableIsEmptyInImageView(imageView);
    }

    @Override
    public Uri getPhotoUri() {
        File photoFile = null;
        try {
            photoFile = FileManager.createImageFile(context);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (photoFile != null) {
            imageListAdapter.addFilePath(photoFile.getAbsolutePath());
            return Uri.fromFile(photoFile);
        }
        return null;
    }

    @Override
    public void onAddPhoto() {
        Converter.compressImage(imageListAdapter.getCurrentPath(), Bitmap.CompressFormat.JPEG, 70, 1536);
        if (gpsTracker.setGpsToFile(imageListAdapter.getCurrentPath())) {
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inSampleSize = 8;
            Bitmap bitmap = BitmapFactory.decodeFile(imageListAdapter.getCurrentPath(), opt);
            imageListAdapter.add(bitmap);
            PrefDefaultValue.saveFilePath(context, docId, imageListAdapter.getItemCount(), imageListAdapter.getCurrentPath());
        } else {
            imageListAdapter.deleteCurrentPath();
        }
    }

    @Override
    public void onPhotoCancel() {
        imageListAdapter.deleteCurrentPath();
    }

    @Override
    public void sendEvacuation() {
        view.setLoading(true);
        registerInProgress = true;
        Thread thread = new Thread(){
            @Override
            public void run() {
                RetrofitService createDataRetrofit = Api.createRetrofitService();


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
                String code = Converter.encodeToBase64(pre + view.getCode() + post);

                String witness1Signature = Converter.encodeBitmapToBase64String(view.getWitnessSignature(1), Bitmap.CompressFormat.PNG);
                String witness2Signature = Converter.encodeBitmapToBase64String(view.getWitnessSignature(2), Bitmap.CompressFormat.PNG);
                String policemanSinature = Converter.encodeBitmapToBase64String(view.getSignaturePol(), Bitmap.CompressFormat.PNG);
                String serial;

                try {
                    Class<?> c = Class.forName("android.os.SystemProperties");
                    Method get = c.getMethod("get", String.class);
                    serial = (String) get.invoke(c, "ro.serialno");
                } catch (Exception ignored) {
                    serial = Build.SERIAL;
                }
                createDataRetrofit.executeCreateEvacuation(
                        Encode.getBasicAuthTemplate(
                                UserManager.getInstanse().getmLogin(),
                                UserManager.getInstanse().getmPassword()
                        ),
                        new CreateEvacuationRequestEnvelope(
                                view.getManufacture(),
                                view.getModel(),
                                view.getCarId(),
                                view.getColor(),
                                photo1, photo2, photo3, photo4,
                                view.getStreet(), view.getClause(),
                                view.getPoliceDepartment(), view.getPoliceman(),
                                view.getWrecker(),
                                view.getOrganization(),
                                1,
                                UserManager.getInstanse().getUserType(), Build.MANUFACTURER + " " + Build.DEVICE + " " + serial, code,
                                view.getWitnessName(1), view.getWitnessAddress(1), view.getWitnessContact(1), witness1Signature, view.getWitnessPlea(1),
                                view.getWitnessName(2), view.getWitnessAddress(2), view.getWitnessContact(2), witness2Signature, view.getWitnessPlea(2),
                                policemanSinature, view.getRevisionResult(), view.getWithoutEvacuation(), view.getParking(),
                                view.getRoadLawPoint()
                        )
                ).enqueue(new Callback<CreateEvacuationResponseEnvelope>() {
                    @Override
                    public void onResponse(Call<CreateEvacuationResponseEnvelope> call, Response<CreateEvacuationResponseEnvelope> response) {
                        registerInProgress = false;
                        if (response.code() == 200) {
                            if (response.body().getClass() == CreateEvacuationResponseEnvelope.class) {
                                CreateEvacuationResponseEnvelope responseEnvelope = response.body();
                                if (responseEnvelope != null) {
                                    if (responseEnvelope.getData().getCode() == 1) {
                                        view.showMessageDialog("Зарегистрированы данные из протокола №" + (docId+1));
                                        PrefDefaultValue.clear(context, docId);
                                        try {
                                            File tmpFile = FileManager.createPdfFile(context);
                                            FileOutputStream fos = new FileOutputStream(tmpFile);

                                            fos.write(Base64.decode(responseEnvelope.getData().getDescription(), Base64.DEFAULT));
                                            fos.close();

                                            Uri path = Uri.fromFile(tmpFile);
                                            view.showPdfFile(path);

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        imageListAdapter.clear();
                                        PrefDefaultValue.clear(context, docId);
                                        view.initialize();

                                    } else {
                                        view.showMessageDialog("Ошибка. Код ошибки: " + responseEnvelope.getData().getCode() + " Описание ошибки: " + responseEnvelope.getData().getDescription());
                                    }
                                } else {
                                    view.showMessageDialog("Неожиданно пустой ответ от сервера. Повторите попытку.");
                                }
                            }
                        } else {
                            view.showMessageDialog("Wrong authorization. Response code: " + response.code() + " " + response.message());
                        }
                        view.setLoading(false);

                    }

                    @Override
                    public void onFailure(Call<CreateEvacuationResponseEnvelope> call, Throwable t) {
                        registerInProgress = false;
                        view.setLoading(false);
                        view.showMessageDialog("Network fail. " + t.getMessage());
                    }
                });
            }
        };

        thread.start();

    }

    @Override
    public void configureUserData() {
        switch (UserManager.getInstanse().getUserType()) {
            case 1:
                ArrayList<String> listPoliceDepartment = new ArrayList<>();
                listPoliceDepartment.add(UserManager.getInstanse().getOrganization());
                view.setListPoliceDepartment(listPoliceDepartment);
                view.setPoliceDepartment(UserManager.getInstanse().getOrganization());
                ArrayList<String> policemanList = new ArrayList<>();
                policemanList.add(UserManager.getInstanse().getmFullName());
                view.setListPoliceman(policemanList);
                view.setPoliceman(UserManager.getInstanse().getmFullName());
                break;
            case 2:
                ArrayList<String> listOrganization = new ArrayList<>();
                listOrganization.add(UserManager.getInstanse().getOrganization());
                view.setListOrganization(listOrganization);
                view.setOrganization(UserManager.getInstanse().getOrganization());
                ArrayList<String> wreckerList = new ArrayList<>();
                wreckerList.add(UserManager.getInstanse().getmFullName());
                view.setListWrecker(wreckerList);
                view.setWrecker(UserManager.getInstanse().getmFullName());
                break;
        }
    }

    @Override
    public void clearBackup() {
        PrefDefaultValue.clear(context, docId);
        initializeListsFromServer();
    }

    @Override
    public void initializeListsFromServer() {
        view.setLoading(true);
        NetworkDataManager.getInstance().getDefaultData();
        NetworkDataManager.getInstance().getRoadLawPointFromServer();
    }

    @Override
    public void startNetworkDataUpdate() {
        NetworkDataManager.getInstance().setListener(this);
    }

    @Override
    public void stopNetworkDataUpdate() {
        NetworkDataManager.getInstance().unregister(this);
    }


    @Override
    public void loadRevisionResultBackup() {
        view.setRevisionResult(PrefDefaultValue.loadRevisionResult(context, docId));
    }

    @Override
    public void loadWitnessBackup(int index) {
        Witness witness = PrefDefaultValue.loadWitness(context, docId, index);
        view.setWitnessName(witness.getName(), index);
        view.setWitnessAddress(witness.getAddress(), index);
        view.setWitnessPlea(witness.getPlea(), index);
        view.setWitnessContact(witness.getContact(), index);
        view.setWitnessSignature(witness.getSignature(), index);
    }

    @Override
    public void loadSignaturePolBackup() {
        view.setSignaturePol(PrefDefaultValue.loadSignaturePol(context, docId));
    }

    @Override
    public void loadOrganizationBackup() {
        view.setOrganization(PrefDefaultValue.loadOrganization(context, docId));
    }

    @Override
    public void loadWreckerBackup() {
        view.setWrecker(PrefDefaultValue.loadWrecker(context, docId));
    }

    @Override
    public void loadPoliceDepartmentBackup() {
        view.setPoliceDepartment(PrefDefaultValue.loadPoliceDepartment(context, docId));
    }

    @Override
    public void loadPolicemanBackup() {
        view.setPoliceman(PrefDefaultValue.loadPoliceman(context, docId));
    }

    @Override
    public void loadParkingBackup() {
        view.setParking(PrefDefaultValue.loadParking(context, docId));
    }

    @Override
    public void onLocationDataChange(String locationAction, Location location) {

    }

    @Override
    public void onDefaultDataUpdate(NetworkDataManager netDataManager) {
        if(!registerInProgress)
            view.setLoading(false);
        view.setListManufacture(netDataManager.getManufactureListAsString());
        if (UserManager.getInstanse().getUserType() != 1)
            view.setListPoliceDepartment(netDataManager.getPoliceDepartmentListAsString());
        view.setListColor(netDataManager.getColorListAsString());
        view.setListParkings(netDataManager.getParkingListAsString());
        if (UserManager.getInstanse().getUserType() != 2)
            view.setListOrganization(netDataManager.getOrganizationListAsString());
        view.setListClause(netDataManager.getClauseListAsString());
        loadColorBackup();
        loadCarIdBackup();
        loadStreetBackup();
        loadRevisionResultBackup();
        loadWitnessBackup(1);
        loadWitnessBackup(2);
        loadSignaturePolBackup();
        loadWithoutEvacuationBackup();
        getImageListAdapterFromBackup();
    }

    @Override
    public void onRanksUpdate(NetworkDataManager netDataManager) {
        if(!registerInProgress)
            view.setLoading(false);
    }

    @Override
    public void onPositionsUpdate(NetworkDataManager netDataManager) {
        if(!registerInProgress)
            view.setLoading(false);
    }

    @Override
    public void onPoliceDepartmentUpdate(NetworkDataManager netDataManager) {
        if(!registerInProgress)
            view.setLoading(false);
    }

    @Override
    public void onRoadLowPointUpdate(NetworkDataManager netDataManager) {
        if(!registerInProgress)
            view.setLoading(false);
        ArrayList<String> roadLawPoints = new ArrayList<>();
        roadLawPoints.add("");
        roadLawPoints.addAll(netDataManager.getRoadLawPointListAsString());
        view.setListRoadLawPoints(roadLawPoints);
        loadRoadLawPointBackup();
    }
}
