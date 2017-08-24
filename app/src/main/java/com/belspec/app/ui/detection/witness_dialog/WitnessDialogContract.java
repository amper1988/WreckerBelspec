package com.belspec.app.ui.detection.witness_dialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

interface WitnessDialogContract {
    interface View{
        Context getContext();
        void setName(String name);
        void setAddress(String address);
        void setContact(String contact);
        void setSignature(Bitmap signature);
        void setPlea(String plea);
        boolean getForAll();
        String getName();
        String getAddress();
        String getContact();
        String getPlea();
        Bitmap getSignature();

    }
    interface Presenter{
        void onCreateDialog(Bundle savedInstanceState);
        void sendWitness();
        void onCancel();
    }
}
