package com.belspec.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.belspec.app.model.Witness;

import java.io.File;

public class SavingManager {
    private static final String doc1 = "Doc1";
    private static final String doc2 = "Doc2";
    private static final String doc3 = "Doc3";
    private static final String doc4 = "Doc4";


    private static SharedPreferences getPrefName(Context context, int docId){
        switch (docId){
            case 0:
                return context.getSharedPreferences(doc1, Context.MODE_PRIVATE);
            case 1:
                return context.getSharedPreferences(doc2, Context.MODE_PRIVATE);
            case 2:
                return context.getSharedPreferences(doc3, Context.MODE_PRIVATE);
            case 3:
                return context.getSharedPreferences(doc4, Context.MODE_PRIVATE);
        }
        return context.getSharedPreferences("NoName", Context.MODE_PRIVATE);
    }
    public static boolean saveFilePath(Context context, String filePath, int docId, int index){

        SharedPreferences sPref = getPrefName(context, docId);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("Path"+index, filePath);
        ed.apply();
        return true;
    }
    public static boolean saveRoadLawPoint(Context context, int docId, String roadLawPoint){
        SharedPreferences sPref = getPrefName(context, docId);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("RoadLawPoint", roadLawPoint);
        ed.apply();
        return true;
    }

    public static boolean savePlea1(Context context, int docId, String plea){
        SharedPreferences sPref = getPrefName(context, docId);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("Plea1", plea);
        ed.apply();
        return true;
    }

    public static boolean savePlea2(Context context, int docId, String plea){
        SharedPreferences sPref = getPrefName(context, docId);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("Plea2", plea);
        ed.apply();
        return true;
    }

    public static boolean saveRevisionResult(Context context, int docId, String revisionResult){
        SharedPreferences sPref = getPrefName(context, docId);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("RevisionResult", revisionResult);
        ed.apply();
        return true;
    }

    public static boolean saveManufacture(Context context, int docId, String manufacture){
        SharedPreferences sPref = getPrefName(context, docId);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("Manufacture", manufacture);
        ed.apply();
        return true;
    }

    public static boolean saveModel(Context context, int docId, String model){
        SharedPreferences sPref = getPrefName(context, docId);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("Model", model);
        ed.apply();
        return true;
    }

    public static boolean saveWitness(Context context, int docId, String lastName, String address, Bitmap signatureWitness1, String contact, int witnessId){
        SharedPreferences sPref = getPrefName(context, docId);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("WitnessName"+witnessId, lastName);
        ed.putString("WitnessAddress"+witnessId, address);
        ed.putString("WitnessSignature"+witnessId, Converter.encodeBitmapToBase64String(signatureWitness1, Bitmap.CompressFormat.PNG));
        ed.putString("WintessContact"+witnessId, contact);
        ed.apply();
        return true;
    }

    public static boolean saveSignaturePol(Context context, int docId, Bitmap signature){
        SharedPreferences sPref = getPrefName(context, docId);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("PolicemanSignature", Converter.encodeBitmapToBase64String(signature, Bitmap.CompressFormat.PNG));
        ed.apply();
        return true;

    }

    public static boolean saveColor(Context context, int docId, String color){
        SharedPreferences sPref = getPrefName(context, docId);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("Color", color);
        ed.apply();
        return true;
    }

    public static boolean saveCarId(Context context, int docId, String carId){
        SharedPreferences sPref = getPrefName(context, docId);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("CarId", carId);
        ed.apply();
        return true;
    }
    public static String loadFilePath(Context context, int docId, int index){
        SharedPreferences sPref = getPrefName(context, docId);
        String path = sPref.getString("Path"+index, "");
        File file = new File(path);
        if(file.exists()){
            return path;
        }
        return "";

    }

    public static Bitmap loadBitmapFromPath(String path){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inSampleSize = 8;
        return BitmapFactory.decodeFile(path, opt);
    }

    public static String loadPlea1(Context context, int docId){
        SharedPreferences sPref = getPrefName(context, docId);
        return sPref.getString("Plea1", "");
    }

    public static String loadPlea2(Context context, int docId){
        SharedPreferences sPref = getPrefName(context, docId);
        return sPref.getString("Plea2", "");
    }

    public static String loadManufacture(Context context, int docId){
        SharedPreferences sPref = getPrefName(context, docId);
        return sPref.getString("Manufacture", "");
    }

    public static String loadModel(Context context, int docId){
        SharedPreferences sPref = getPrefName(context, docId);
        return sPref.getString("Model", "");
    }

    public static Witness loadWitness(Context context, int docId, int witnessId){
        SharedPreferences sPref = getPrefName(context, docId);
        Witness witness = new Witness(
                sPref.getString("WitnessName"+witnessId, ""),
                sPref.getString("WitnessAddress"+witnessId,""),
                Converter.getBitmapFromBase64Stirng(context,sPref.getString("WitnessSignature"+witnessId, "")),
                sPref.getString("Plea"+witnessId,""),
                sPref.getString("WitnessContact"+witnessId, ""));
        return witness;
    }

    public static Bitmap loadSignaturePol(Context context, int docId){
        SharedPreferences sPref = getPrefName(context, docId);
        return Converter.getBitmapFromBase64Stirng(context, sPref.getString("PolicemanSignature", ""));
    }

    public static String loadRevisionResult(Context context, int docId){
        SharedPreferences sPref = getPrefName(context, docId);
        return sPref.getString("RevisionResult", "");
    }

    public static String loadRoadLawPoint(Context context, int docId){
        SharedPreferences sPref = getPrefName(context, docId);
        return sPref.getString("RoadLawPoint", "");
    }

    public static String loadColor(Context context, int docId) {
        SharedPreferences sPref = getPrefName(context, docId);
        return sPref.getString("Color", "");
    }

    public static String loadCarId(Context context, int docId){
        SharedPreferences sPref = getPrefName(context, docId);
        return sPref.getString("CarId", "");
    }

    public static void clear(Context context, int docId){
        saveFilePath(context, "", docId, 1);
        saveFilePath(context, "", docId, 2);
        saveFilePath(context, "", docId, 3);
        saveFilePath(context, "", docId, 4);
        saveManufacture(context, docId, "");
        saveModel(context, docId, "");
        saveCarId(context, docId, "");
        saveColor(context, docId, "");
        saveWitness(context, docId, "", "", null, "", 1);
        saveWitness(context, docId, "", "", null, "", 2);
        savePlea1(context, docId, "");
        savePlea2(context, docId, "");
        saveRevisionResult(context, docId, "");
        saveSignaturePol(context, docId, null);
        saveRoadLawPoint(context, docId, "");


    }
}
