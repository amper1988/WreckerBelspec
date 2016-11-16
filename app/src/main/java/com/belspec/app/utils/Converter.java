package com.belspec.app.utils;


import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Converter {
    public static String encodeFileToBase64String(String path)
    {
        File file = new File(path);
        if (file.exists()) {

            FileInputStream imageInFile = null;
            try {
                imageInFile = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            byte imageData[] = new byte[(int) file.length()];
            try {
                imageInFile.read(imageData);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String imageDataString = Base64.encodeToString(imageData, Base64.DEFAULT);

            try {
                imageInFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return imageDataString;
        }
        return "";
    }
    public static Bitmap convertImageViewToBitmap(ImageView image){
        try{
            BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
            return drawable.getBitmap();
        }
        catch (NullPointerException e){
            return null;
        }

    }

    public static String encodeImageViewToBase64String(ImageView image, Bitmap.CompressFormat compressFormat){
        return encodeBitmapToBase64String(convertImageViewToBitmap(image), compressFormat);
    }

    public static String encodeBitmapToBase64String(Bitmap bm, Bitmap.CompressFormat compressFormat){
        Bitmap immagex=bm;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(compressFormat, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);
        return imageEncoded;
    }

    public static String encodeToBase64(String sourse){
       return Base64.encodeToString(sourse.getBytes(), Base64.DEFAULT);
    }

    public static Bitmap getBitmapFromBase64Stirng(Context context, String base64String){
        if (base64String != "" && base64String != null) {
             byte[] base64 = Base64.decode(base64String, Base64.DEFAULT);
            Bitmap bm = BitmapFactory.decodeByteArray(base64, 0, base64.length);

            return bm;
        }
        AssetManager assetManager = context.getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open("emptyBackground.jpg");

        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bm = BitmapFactory.decodeStream(istr);
        return bm;
    }

    public static boolean compressImage(String path, Bitmap.CompressFormat format, int quality, int maxImageSize){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bm = BitmapFactory.decodeFile(path);
        float ratio = Math.min(
                (float) maxImageSize / bm.getWidth(),
                (float) maxImageSize / bm.getHeight());
        int width = Math.round((float) ratio * bm.getWidth());
        int height = Math.round((float) ratio * bm.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(bm, width,
                height, true);
        newBitmap.compress(format, quality, baos);

        try {
            FileOutputStream fos = new FileOutputStream(new File(path));
            fos.write(baos.toByteArray());
            fos.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
