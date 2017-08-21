package com.belspec.app.ui.detection.signature_dialog;


import android.graphics.Bitmap;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;

public class SignatureDialogPresenter implements SignatureDialogContract.Presenter{
    private SignatureDialogContract.View mView;

    public SignatureDialogPresenter(SignatureDialogContract.View view){
       this.mView = view;
    }
    @Override
    public void onCreateDialog(Bundle savedInstanceState) {

    }

    @Override
    public void onClose() {
        EventBus.getDefault().post(new SignatureEvent(null));
    }

    @Override
    public void sendResult(Bitmap bm) {
        EventBus.getDefault().post(new SignatureEvent(bm));
    }
}
