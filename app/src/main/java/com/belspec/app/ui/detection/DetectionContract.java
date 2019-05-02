package com.belspec.app.ui.detection;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.belspec.app.adapters.ImageListAdapter;

import java.util.List;

abstract class DetectionContract {
    static final int REQUEST_CODE_PHOTO = 1;
    static final int REQUEST_SETTINGS = 111;

    interface View{
        void initialize();
        int getDocId();
        void setAdapter(ImageListAdapter adapter);
        void setLoading(boolean bool);
        void showMessage(String message);
        boolean canSetWitness(int index);
        void showPdfFile(Uri uri);
        void showMessageDialog(String message);

        void showGPSAlert();

        String getManufacture();

        void setManufacture(String manufacture);

        String getModel();

        void setModel(String model);

        String getCarId();

        void setCarId(String carId);

        String getColor();

        void setColor(String color);

        void setWitnessName(String name, int index);
        String getWitnessName(int index);
        void setWitnessAddress(String address,int index);
        String getWitnessAddress(int index);
        void setWitnessContact(String contact,int index);
        String getWitnessContact(int index);
        void setWitnessSignature(Bitmap signature,int index);
        Bitmap getWitnessSignature(int index);
        void setWitnessPlea(String plea,int index);
        String getWitnessPlea(int index);

        void showRequireDistance(double distance);
        void showCallButton();
        void disableCall();

        String getOrganization();

        void setOrganization(String organization);

        String getPoliceDepartment();

        void setPoliceDepartment(String policeDepartment);

        String getPoliceman();

        void setPoliceman(String policeman);

        Bitmap getSignaturePol();

        void setSignaturePol(Bitmap signaturePol);

        String getRoadLawPoint();

        void setRoadLawPoint(String roadLawPoint);

        String getRevisionResult();

        void setRevisionResult(String revisionResult);

        String getParking();

        void setParking(String parking);

        String getStreet();

        void setStreet(String street);

        boolean getWithoutEvacuation();

        void setWithoutEvacuation(boolean bool);

        String getClause();

        void setClause(String clause);

        String getWrecker();

        void setWrecker(String wrecker);

        String getCode();
        void setCode(String code);

        void setListPoliceDepartment(List<String> policeDepartments);
        void setListPoliceman(List<String> policemans);
        void setListManufacture(List<String> manufactures);
        void setListModels(List<String> models);
        void setListRoadLawPoints(List<String> roadLawPoints);
        void setListParkings(List<String> parkings);
        void setListColor(List<String> colors);
        void setListOrganization(List<String> organizations);
        void setListWrecker(List<String> listWrecker);
        void setListClause(List<String> listClause);

    }
    interface Presenter{
        //life cycle methods
        void onViewCreated(android.view.View view, @Nullable Bundle savedInstanceState);
        void onResume();
        void onPause();
        void onStop();
        void onDestroyView();
        int getUserType();
        void getImageListAdapterFromBackup();
        void getStreet();
        void getSignaturePol();
        void getWitness(int index);
        Bitmap getBitmap(ImageView imageView);
        void clearImages();
        boolean imageIsEmpty(ImageView imageView);
        Uri getPhotoUri();
        void onAddPhoto();
        void onPhotoCancel();
        void sendEvacuation();

        void configureUserData();

        void saveManufactureBackup(String manufacture);
        void saveModelBackup(String model);
        void saveCarIdBackup(String carId);
        void saveColorBackup(String color);
        void saveWithoutEvacuationBackup(int checked);
        void saveRoadLawPointBackup(String roadLawPoint);
        void saveClauseBackup(String clause);
        void saveRevisionResultBackup(String revisionResult);
        void saveSignaturePolBackup(Bitmap signature);
        void saveOrganizationBackup(String organization);
        void saveWreckerBackup(String wrecker);
        void savePoliceDepartmentBackup(String policeDepartment);
        void savePolicemanBackup(String policeman);
        void saveParkingBackup(String parking);
        void saveStreetBackup(String street);
        void loadManufactureBackup();
        void loadModelBackup();
        void loadCarIdBackup();
        void loadColorBackup();
        void loadWithoutEvacuationBackup();
        void loadRoadLawPointBackup();
        void loadClauseBackup();
        void loadRevisionResultBackup();
        void loadWitnessBackup(int index);
        void loadSignaturePolBackup();
        void loadRequiresId();
        void loadOrganizationBackup();
        void loadWreckerBackup();
        void loadPoliceDepartmentBackup();
        void loadPolicemanBackup();
        void loadParkingBackup();
        void loadStreetBackup();
        void clearBackup();
        void createCall(String address, String carNumber);
        void getCall();

        void loadListPoliceman(String policeDepartment);
        void loadListModel(String manufacture);
        void loadListWrecker(String organization);
        void initializeListsFromServer();
        void startNetworkDataUpdate();
        void stopNetworkDataUpdate();






    }
}
