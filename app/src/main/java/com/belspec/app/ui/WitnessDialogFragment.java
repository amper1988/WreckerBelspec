package com.belspec.app.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.belspec.app.R;
import com.belspec.app.views.DrawingView;

import java.io.ByteArrayOutputStream;

public class WitnessDialogFragment extends DialogFragment {
    View mView;
    TextInputLayout tilLastName;
    TextInputLayout tilContact;
    TextInputLayout tilAddress;
    EditText edtLastName;
    EditText edtAddress;
    EditText edtContact;
    DrawingView dvSignature;
    Button btnClear;
    Button btnOK;
    int buttonID;
    String lastName = new String();
    String address = new String();
    String contact = new String();
    Bitmap bm;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mView = inflater.inflate(R.layout.witness_fragment, null);
        tilLastName = (TextInputLayout)mView.findViewById(R.id.tilLastName);
        tilLastName.setErrorEnabled(false);
        tilContact = (TextInputLayout)mView.findViewById(R.id.tilContact);
        tilContact.setErrorEnabled(false);
        tilAddress = (TextInputLayout) mView.findViewById(R.id.tilAddress);
        tilAddress.setErrorEnabled(false);
        edtLastName = (EditText) mView.findViewById(R.id.edtLastName);
        edtAddress = (EditText) mView.findViewById(R.id.edtAddress);
        edtContact = (EditText) mView.findViewById(R.id.edtContact);
        btnClear = (Button) mView.findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dvSignature.reset();
            }
        });
        dvSignature = (DrawingView) mView.findViewById(R.id.viewDrawer);
        if(bm!=null){
            dvSignature.setBitmap(bm);
        }
        edtLastName.setText(lastName);
        edtAddress.setText(address);
        edtContact.setText(contact);

        btnOK = (Button)mView.findViewById(R.id.btnOk);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (confirmData()) {
                    Intent intent = new Intent();
                    intent.putExtra("buttonID", buttonID);
                    intent.putExtra("lastName", edtLastName.getText().toString());
                    intent.putExtra("address", edtAddress.getText().toString());
                    intent.putExtra("contact", edtContact.getText().toString());
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();

                    dvSignature.getmBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("signature", byteArray);
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);

                    close();
                }else{
                    if(edtLastName.getText().toString().equals("")){
                        tilLastName.setErrorEnabled(true);
                        tilLastName.setError("Input last name");
                    }
                    if(edtContact.getText().toString().equals("")){
                        tilContact.setErrorEnabled(true);
                        tilContact.setError("Input contact");
                    }
                    if(edtAddress.getText().toString().equals("")){
                        tilAddress.setErrorEnabled(true);
                        tilAddress.setError("Input address");
                    }
                }
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(mView);
        return builder.create();
    }

    private boolean confirmData(){
        if(edtLastName.getText().toString().equals("")){
            return false;
        }
        if(edtContact.getText().toString().equals("")){
            return false;
        }
        if(edtAddress.getText().toString().equals("")){
            return false;
        }
        return true;
    }

    private void close(){
        this.dismiss();
    }
}
