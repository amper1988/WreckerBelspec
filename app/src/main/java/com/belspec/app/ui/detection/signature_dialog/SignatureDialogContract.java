package com.belspec.app.ui.detection.signature_dialog;


import android.graphics.Bitmap;
import android.os.Bundle;

interface SignatureDialogContract {
    interface View{
        void setSignature(Bitmap signature);
    }
    interface Presenter{
        void onClose();
        void sendResult(Bitmap bm);
    }
}
