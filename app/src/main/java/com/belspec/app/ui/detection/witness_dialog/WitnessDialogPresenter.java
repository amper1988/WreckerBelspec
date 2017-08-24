package com.belspec.app.ui.detection.witness_dialog;


import android.os.Bundle;
import android.widget.ImageView;

import com.belspec.app.ui.detection.Witness;
import com.belspec.app.utils.Utils;

import org.greenrobot.eventbus.EventBus;

class WitnessDialogPresenter implements WitnessDialogContract.Presenter {
    private WitnessDialogContract.View mView;
    WitnessDialogPresenter(WitnessDialogContract.View view){
        mView = view;

    }
    @Override
    public void onCreateDialog(Bundle savedInstanceState) {

    }

    @Override
    public void sendWitness() {
        Witness witness = new Witness(mView.getName(), mView.getAddress(), mView.getSignature(), mView.getPlea(), mView.getContact());
        EventBus.getDefault().post(new WitnessSendEvent(witness, mView.getForAll()));
    }

    @Override
    public void onCancel() {
        EventBus.getDefault().post(new WitnessSendEvent(null, false));
    }
}
