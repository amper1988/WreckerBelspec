package com.belspec.app.ui.owner_data;

public class OwnerDataSendEvent {
    int docId;

    OwnerDataSendEvent(int docId){
        this.docId = docId;
    }
    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }
}
