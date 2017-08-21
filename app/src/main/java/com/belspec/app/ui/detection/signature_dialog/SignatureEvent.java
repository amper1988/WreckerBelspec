package com.belspec.app.ui.detection.signature_dialog;

import android.graphics.Bitmap;

public class SignatureEvent {
    private Bitmap signature;

    SignatureEvent(Bitmap bm){
        this.signature = bm;
    }
    public Bitmap getSignature() {
        return signature;
    }

    public void setSignature(Bitmap signature) {
        this.signature = signature;
    }
}
