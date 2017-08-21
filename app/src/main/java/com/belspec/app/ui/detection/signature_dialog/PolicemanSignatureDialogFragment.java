package com.belspec.app.ui.detection.signature_dialog;


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.belspec.app.R;
import com.belspec.app.views.DrawingView;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PolicemanSignatureDialogFragment extends DialogFragment implements SignatureDialogContract.View{
    @BindView(R.id.viewDrawer) DrawingView dvSignature;
    @BindView(R.id.btnClear) Button btnClear;
    @BindView(R.id.btnOk) Button btnOk;
    @BindView(R.id.ibClose) ImageButton ibClose;
    public final static String SIGNATURE = "SIGNATURE";
    SignatureDialogContract.Presenter presenter;
    View mView;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mView = inflater.inflate(R.layout.policeman_signature_fragment, null);
        ButterKnife.bind(this, mView);
        presenter = new SignatureDialogPresenter(this);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dvSignature.reset();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                dvSignature.getmBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
//                intent.putExtra(RESULT, byteArray);
//                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                presenter.sendResult(dvSignature.getmBitmap());
                dismiss();
            }
        });

        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClose();
                dismiss();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(mView);
        return builder.create();
    }

    @Override
    public void setSignature(Bitmap signature) {
        dvSignature.setBitmap(signature);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        presenter.onClose();
    }
}
