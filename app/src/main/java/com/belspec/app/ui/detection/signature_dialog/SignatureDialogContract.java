package com.belspec.app.ui.detection.signature_dialog;


import android.graphics.Bitmap;
import android.os.Bundle;

public interface SignatureDialogContract {
    interface View{
        void setSignature(Bitmap signature);
    }
    interface Presenter{
        void onCreateDialog(Bundle savedInstanceState);
        void onClose();
        void sendResult(Bitmap bm);
    }
}
