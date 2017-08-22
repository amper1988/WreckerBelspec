package com.belspec.app.ui.detection.signature_dialog;


import android.graphics.Bitmap;

import org.greenrobot.eventbus.EventBus;
 class SignatureDialogPresenter implements SignatureDialogContract.Presenter{



    @Override
    public void onClose() {
        EventBus.getDefault().post(new SignatureEvent(null));
    }

    @Override
    public void sendResult(Bitmap bm) {
        EventBus.getDefault().post(new SignatureEvent(bm));
    }
}
