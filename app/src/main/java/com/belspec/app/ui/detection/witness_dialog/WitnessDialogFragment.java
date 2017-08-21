package com.belspec.app.ui.detection.witness_dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.Toast;

import com.belspec.app.R;
import com.belspec.app.views.DrawingView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WitnessDialogFragment extends DialogFragment implements WitnessDialogContract.View {
    View mView;
    @BindView(R.id.tilLastName) TextInputLayout tilLastName;
    @BindView(R.id.tilContact) TextInputLayout tilContact;
    @BindView(R.id.tilAddress) TextInputLayout tilAddress;
    @BindView(R.id.tilPlea) TextInputLayout tilPlea;
    @BindView(R.id.edtPlea) EditText edtPlea;
    @BindView(R.id.edtLastName) EditText edtLastName;
    @BindView(R.id.edtAddress) EditText edtAddress;
    @BindView(R.id.edtContact) EditText edtContact;
    @BindView(R.id.chbPlea) CheckBox chbPlea;
    @BindView(R.id.chbAddToAllDetection) CheckBox chbAddToAllDetections;
    @BindView(R.id.viewDrawer) DrawingView dvSignature;
    @BindView(R.id.btnClear) Button btnClear;
    @BindView(R.id.btnOk) Button btnOK;
    @BindView(R.id.btnClose) ImageButton btnClose;
    @BindView(R.id.tab_host) TabHost tabHost;
    private WitnessDialogPresenter presenter;
    public final static String NAME_TAG = "NAME_TAG";
    public final static String ADDRESS_TAG = "ADDRESS_TAG";
    public final static String CONTACT_TAG = "CONTACT_TAG";
    public final static String PLEA_TAG = "PLEA_TAG";
    public final static String SIGNATURE_TAG = "SIGNATURE_TAG";


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mView = inflater.inflate(R.layout.witness_fragment, null);
        ButterKnife.bind(this, mView);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Данные понятого").setContent(R.id.witness_data));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Подпись понятого").setContent(R.id.signature));
        Bundle args = getArguments();
        if(!args.isEmpty()){
            edtLastName.setText((String)args.get(NAME_TAG));
            edtContact.setText((String)args.get(CONTACT_TAG));
            edtAddress.setText((String)args.get(ADDRESS_TAG));
            edtPlea.setText((String)args.get(PLEA_TAG));
            dvSignature.setBitmap((Bitmap)args.get(SIGNATURE_TAG));
        }
        presenter = new WitnessDialogPresenter(this);
        presenter.onCreateDialog(savedInstanceState);
        tilLastName.setErrorEnabled(false);
        tilContact.setErrorEnabled(false);
        tilAddress.setErrorEnabled(false);
        tilPlea.setErrorEnabled(false);
        chbPlea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chbPlea.isChecked()){
                    tilPlea.setVisibility(View.VISIBLE);
                }else{
                    tilPlea.setVisibility(View.INVISIBLE);
                }
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dvSignature.reset();
            }
        });

        btnOK = (Button)mView.findViewById(R.id.btnOk);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (confirmData()) {
                    presenter.sendWitness();
                    dismiss();
                }else{
                    if(edtLastName.getText().toString().equals("")){
                        tilLastName.setErrorEnabled(true);
                        tilLastName.setError("Введите ФИО");
                        Toast.makeText(getContext(),"Введите ФИО", Toast.LENGTH_SHORT).show();
                    }
                    if(edtAddress.getText().toString().equals("")){
                        tilAddress.setErrorEnabled(true);
                        tilAddress.setError("Введите адрес");
                        Toast.makeText(getContext(),"Введите адрес", Toast.LENGTH_SHORT).show();
                    }
                    if(chbPlea.isChecked()&&edtPlea.getText().toString().equals("")){
                        tilPlea.setErrorEnabled(true);
                        tilPlea.setError("Введите замечания или снимите галочку Замечания");
                        Toast.makeText(getContext(),"Введите замечания или снимите галочку Замечания", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onCancel();
                dismiss();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(mView);
        return builder.create();


    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        presenter.onCancel();
    }

    private boolean confirmData(){
        if(edtLastName.getText().toString().equals("")){
            return false;
        }
        if(edtAddress.getText().toString().equals("")){
            return false;
        }
        if(chbPlea.isChecked()&&edtPlea.getText().toString().equals("")){
            return false;
        }
        return true;
    }

    @Override
    public void setName(String name) {
        edtLastName.setText(name);
    }

    @Override
    public void setAddress(String address) {
        edtAddress.setText(address);
    }

    @Override
    public void setContact(String contact) {
        edtContact.setText(contact);
    }

    @Override
    public void setSignature(Bitmap signature) {
        dvSignature.setBitmap(signature);
    }

    @Override
    public void setPlea(String plea) {
        if(plea != null){
            chbPlea.setChecked(true);
            edtPlea.setText(plea);
        }
    }

    @Override
    public boolean getForAll() {
        return chbAddToAllDetections.isChecked();
    }

    @Override
    public String getName() {
        return edtLastName.getText().toString();
    }

    @Override
    public String getAddress() {
        return edtAddress.getText().toString();
    }

    @Override
    public String getContact() {
        return edtContact.getText().toString();
    }

    @Override
    public String getPlea() {
        return edtPlea.getText().toString();
    }

    @Override
    public Bitmap getSignature() {
        return dvSignature.getmBitmap();
    }
}
