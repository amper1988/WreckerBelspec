package com.belspec.app.ui.detection;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.belspec.app.utils.Converter;

import java.io.File;

class PrefDefaultValue {
    private static final String DOC1= "DOC1";
    private static final String DOC2 = "DOC2";
    private static final String DOC3 = "DOC3";
    private static final String DOC4 = "DOC4";
    private static final String CLAUSE = "CLAUSE_POS";
    private static final String ORGANIZATION = "ORGANIZATION_POS";
    private static final String WRECKER = "WRECKER_POS";
    private static final String POLICEMAN = "POLICEMAN_POS";
    private static final String POLICEDEPARTMENT = "POLICEDEPARTMENT_POS";
    private static final String PARKING = "PARKING_POS";
    private static final String FILE_PATH = "PATH";
    private static final String ROAD_LAW_POINT = "ROAD_LAW_POINT";
    private static final String PLEA = "PLEA";
    private static final String REVISION_RESULT = "REVISION_RESULT";
    private static final String MANUFACTURE = "MANUFACTURE";
    private static final String MODEL = "MODEL";
    private static final String CAR_ID = "CAR_ID";
    private static final String COLOR = "COLOR";
    private static final String WITNESS_NAME = "WITNENESS_NAME";
    private static final String WITNESS_ADDRESS = "WITNESS_ADDRESS";
    private static final String WITNESS_SIGNATURE = "WITNESS_SIGNATURE";
    private static final String WITNESS_CONTACT = "WITNESS_CONTACT";
    private static final String POLICEMAN_SIGNATURE = "POLICEMAN_SIGNATURE";
    private static final String STREET = "STREET";
    private static final String WITHOUT_EVACUATION = "WITHOUT_EVACUATION";

    private static SharedPreferences getPrefName(Context context, int docId){
        switch (docId){
            case 0:
                return context.getSharedPreferences(DOC1, Context.MODE_PRIVATE);
            case 1:
                return context.getSharedPreferences(DOC2, Context.MODE_PRIVATE);
            case 2:
                return context.getSharedPreferences(DOC3, Context.MODE_PRIVATE);
            case 3:
                return context.getSharedPreferences(DOC4, Context.MODE_PRIVATE);
        }
        return context.getSharedPreferences("NoName", Context.MODE_PRIVATE);
    }

    static boolean saveClause(Context context, int docId, String clause){
        SharedPreferences sPref = getPrefName(context, docId);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(CLAUSE, clause);
        ed.apply();
        return true;
    }

    static boolean saveOrganization(Context context, int docId, String organization){
        SharedPreferences sPref = getPrefName(context, docId);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(ORGANIZATION, organization);
        ed.apply();
        return true;
    }

    static boolean saveParking(Context context, int docId, String parking){
        SharedPreferences sPref = getPrefName(context, docId);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(PARKING, parking);
        ed.apply();
        return true;
    }

    static boolean savePoliceDepartment(Context context, int docId, String policeDepartment){
        SharedPreferences sPref = getPrefName(context, docId);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(POLICEDEPARTMENT, policeDepartment);
        ed.apply();
        return true;
    }

    static boolean savePoliceman(Context context, int docId, String policeman){
        SharedPreferences sPref = getPrefName(context, docId);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(POLICEMAN, policeman);
        ed.apply();
        return true;
    }

    static boolean saveWrecker(Context context, int docId, String wrecker){
        SharedPreferences sPref = getPrefName(context, docId);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(WRECKER, wrecker);
        ed.apply();
        return true;
    }

    static boolean saveStreet(Context context, int docId, String street){
        SharedPreferences sPref = getPrefName(context, docId);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(STREET, street);
        ed.apply();
        return true;
    }

    static boolean saveFilePath(Context context, int docId, int index, String filePath){
        SharedPreferences sPref = getPrefName(context, docId);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(FILE_PATH+index, filePath);
        ed.apply();
        return true;
    }

    static boolean saveRoadLawPoint(Context context, int docId, String roadLawPoint){
        SharedPreferences sPref = getPrefName(context, docId);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(ROAD_LAW_POINT, roadLawPoint);
        ed.apply();
        return true;
    }

    static boolean saveRevisionResult(Context context, int docId, String revisionResult){
        SharedPreferences sPref = getPrefName(context, docId);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(REVISION_RESULT, revisionResult);
        ed.apply();
        return true;
    }

    static boolean saveManufacture(Context context, int docId, String manufacture){
        SharedPreferences sPref = getPrefName(context, docId);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(MANUFACTURE, manufacture);
        ed.apply();
        return true;
    }

    static boolean saveModel(Context context, int docId, String model){
        SharedPreferences sPref = getPrefName(context, docId);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(MODEL, model);
        ed.apply();
        return true;
    }

    static boolean saveWitness(Context context, int docId, int witnessId, String lastName, String address, Bitmap signatureWitness, String contact, String plea){
        SharedPreferences sPref = getPrefName(context, docId);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(WITNESS_NAME+witnessId, lastName);
        ed.putString(WITNESS_ADDRESS+witnessId, address);
        ed.putString(WITNESS_SIGNATURE+witnessId, Converter.encodeBitmapToBase64String(signatureWitness, Bitmap.CompressFormat.PNG));
        ed.putString(WITNESS_CONTACT+witnessId, contact);
        ed.putString(PLEA+witnessId, plea);
        ed.apply();
        return true;
    }

    static boolean saveSignaturePol(Context context, int docId, Bitmap signature){
        SharedPreferences sPref = getPrefName(context, docId);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(POLICEMAN_SIGNATURE, Converter.encodeBitmapToBase64String(signature, Bitmap.CompressFormat.PNG));
        ed.apply();
        return true;

    }

    static boolean saveColor(Context context, int docId, String color){
        SharedPreferences sPref = getPrefName(context, docId);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(COLOR, color);
        ed.apply();
        return true;
    }

    static boolean saveCarId(Context context, int docId, String carId){
        SharedPreferences sPref = getPrefName(context, docId);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(CAR_ID, carId);
        ed.apply();
        return true;
    }

    static boolean saveWithoutEvacuation(Context context, int docId, int checked){
        SharedPreferences sPref = getPrefName(context, docId);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(WITHOUT_EVACUATION, checked);
        ed.apply();
        return true;
    }

    static int loadWithoutEvacuation(Context context, int docId){
        SharedPreferences sPref = getPrefName(context, docId);
        return sPref.getInt(WITHOUT_EVACUATION, 0);
    }

    static String loadClause(Context context, int docId){
        SharedPreferences sPref = getPrefName(context, docId);
        return sPref.getString(CLAUSE, "");
    }

    static String loadOrganization(Context context, int docId){
        SharedPreferences sPref = getPrefName(context, docId);
        return sPref.getString(ORGANIZATION, "");
    }

    static String loadParking(Context context, int docId){
        SharedPreferences sPref = getPrefName(context, docId);
        return sPref.getString(PARKING, "");
    }

    static String loadPoliceDepartment(Context context, int docId){
        SharedPreferences sPref = getPrefName(context, docId);
        return sPref.getString(POLICEDEPARTMENT, "");
    }

    static String loadPoliceman(Context context, int docId){
        SharedPreferences sPref = getPrefName(context, docId);
        return sPref.getString(POLICEMAN, "");
    }

    static String loadWrecker(Context context, int docId){
        SharedPreferences sPref = getPrefName(context, docId);
        return sPref.getString(WRECKER, "");
    }

    static String loadStreet(Context context, int docId){
        SharedPreferences sPref = getPrefName(context, docId);
        return sPref.getString(STREET, "");
    }

    static String loadFilePath(Context context, int docId, int index){
        SharedPreferences sPref = getPrefName(context, docId);
        String path = sPref.getString(FILE_PATH+index, "");
        File file = new File(path);
        if(file.exists()){
            return path;
        }
        return "";

    }

//    static Bitmap loadBitmapFromPath(String path){
//        BitmapFactory.Options opt = new BitmapFactory.Options();
//        opt.inSampleSize = 8;
//        return BitmapFactory.decodeFile(path, opt);
//    }

    static String loadManufacture(Context context, int docId){
        SharedPreferences sPref = getPrefName(context, docId);
        return sPref.getString(MANUFACTURE, "");
    }

    static String loadModel(Context context, int docId){
        SharedPreferences sPref = getPrefName(context, docId);
        return sPref.getString(MODEL, "");
    }

    static Witness loadWitness(Context context, int docId, int witnessId){
        SharedPreferences sPref = getPrefName(context, docId);
        return  new Witness(
                sPref.getString(WITNESS_NAME+witnessId, ""),
                sPref.getString(WITNESS_ADDRESS+witnessId,""),
                Converter.getBitmapFromBase64Stirng(context,sPref.getString(WITNESS_SIGNATURE+witnessId, "")),
                sPref.getString(PLEA+witnessId,""),
                sPref.getString(WITNESS_CONTACT+witnessId, ""));

    }

    static Bitmap loadSignaturePol(Context context, int docId){
        SharedPreferences sPref = getPrefName(context, docId);
        return Converter.getBitmapFromBase64Stirng(context, sPref.getString(POLICEMAN_SIGNATURE, ""));
    }

    static String loadRevisionResult(Context context, int docId){
        SharedPreferences sPref = getPrefName(context, docId);
        return sPref.getString(REVISION_RESULT, "");
    }

    static String loadRoadLawPoint(Context context, int docId){
        SharedPreferences sPref = getPrefName(context, docId);
        return sPref.getString(ROAD_LAW_POINT, "");
    }

    static String loadColor(Context context, int docId) {
        SharedPreferences sPref = getPrefName(context, docId);
        return sPref.getString(COLOR, "");
    }

    static String loadCarId(Context context, int docId){
        SharedPreferences sPref = getPrefName(context, docId);
        return sPref.getString(CAR_ID, "");
    }

    static boolean clear(Context context, int docId){
        saveFilePath(context, docId, 1, "");
        saveFilePath(context, docId, 2, "");
        saveFilePath(context, docId, 3, "");
        saveFilePath(context, docId, 4, "");
        saveManufacture(context, docId, "");
        saveModel(context, docId, "");
        saveCarId(context, docId, "");
        saveColor(context, docId, "");
        saveWitness(context, docId, 1, "", "", null, "", "");
        saveWitness(context, docId, 2, "", "", null, "", "");
        saveRevisionResult(context, docId, "");
        saveSignaturePol(context, docId, null);
        saveRoadLawPoint(context, docId, "");
        return true;
    }


}
