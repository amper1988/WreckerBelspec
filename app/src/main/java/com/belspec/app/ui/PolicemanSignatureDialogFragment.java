package com.belspec.app.ui;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.belspec.app.R;
import com.belspec.app.views.DrawingView;

import java.io.ByteArrayOutputStream;
import java.util.zip.Inflater;

public class PolicemanSignatureDialogFragment extends DialogFragment{
    DrawingView dvSignature;
    Button btnClear;
    Button btnOk;
    View mView;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mView = inflater.inflate(R.layout.policeman_signature_fragment, null);
        dvSignature = (DrawingView)mView.findViewById(R.id.viewDrawer);
        btnClear = (Button)mView.findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dvSignature.reset();
            }
        });

        btnOk = (Button)mView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                dvSignature.getmBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("signature", byteArray);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                dismiss();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(mView);
        return builder.create();
    }
}
