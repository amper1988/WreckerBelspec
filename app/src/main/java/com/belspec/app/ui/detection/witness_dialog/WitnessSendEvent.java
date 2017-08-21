package com.belspec.app.ui.detection.witness_dialog;

import com.belspec.app.ui.detection.Witness;

public class WitnessSendEvent {
    private Witness witness;
    private boolean forAll;

    public WitnessSendEvent(Witness witness, boolean forAll){
        this.witness = witness;
        this.forAll = forAll;
    }
    public Witness getWitness() {
        return witness;
    }

    public void setWitness(Witness witness) {
        this.witness = witness;
    }

    public boolean isForAll() {
        return forAll;
    }

    public void setForAll(boolean forAll) {
        this.forAll = forAll;
    }
}
