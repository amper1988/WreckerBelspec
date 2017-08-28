package com.belspec.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FileManager {
    public static File createImageFile(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }

    public static File createPdfFile(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String pdfFileName = "PDF_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File pdfFile = File.createTempFile(
                pdfFileName,  /* prefix */
                ".pdf",         /* suffix */
                storageDir      /* directory */
        );
        return pdfFile;
    }

    public static File createApkFile(Context context) throws IOException{
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String apkFileName = "APK_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        return File.createTempFile(
                apkFileName,  /* prefix */
                ".apk",         /* suffix */
                storageDir      /* directory */
        );
    }

    public static Bitmap loadBitmapFromPath(String path){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inSampleSize = 8;
        return BitmapFactory.decodeFile(path, opt);
    }
}
