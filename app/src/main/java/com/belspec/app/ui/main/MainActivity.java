package com.belspec.app.ui.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.belspec.app.R;
import com.belspec.app.ui.control.ControlActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener, MainContract.View {

    private final int REQUEST_SETTINGS = 111;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.edtName)
    EditText edtName;
    @BindView(R.id.edtPwd)
    EditText edtPwd;
    @BindView(R.id.response)
    TextView txvStatus;
    @BindView(R.id.imvLoading)
    ImageView imvLoading;
    @BindView(R.id.rllContent)
    RelativeLayout rllContent;
    @BindView(R.id.txvLoadingData)
    TextView txvLoadingData;
    @BindView(R.id.version)
    TextView txvVersion;
    MainContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);
        ButterKnife.bind(this);
        presenter = new MainPresenter(this);
        presenter.onCreate(savedInstanceState);
        initComponents();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        presenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }


    private void initComponents() {
        btnLogin.setOnClickListener(this);
        edtName.setOnClickListener(this);
        edtPwd.setOnClickListener(this);
        txvVersion.setText(getString(R.string.version_place_holder,  getVersionName()));
        edtPwd.setImeActionLabel("DONE", EditorInfo.IME_ACTION_DONE);
        edtName.setOnEditorActionListener(this);
        edtName.setImeActionLabel("NEXT", EditorInfo.IME_ACTION_NEXT);
        edtPwd.setOnEditorActionListener(this);
        imvLoading.setBackgroundResource(R.drawable.pb_loading);
        imvLoading.setVisibility(View.GONE);
        txvLoadingData.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                presenter.login(getVersionName());
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_NEXT) {
            edtPwd.requestFocus();
            return true;
        }
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            onClick(btnLogin);
            return true;
        }
        return false;
    }

    @Override
    public void setLoading(boolean loading, String status) {
        AnimationDrawable animation = (AnimationDrawable) imvLoading.getBackground();
        txvLoadingData.setText(status);
        if (loading) {
            imvLoading.setVisibility(View.VISIBLE);
            txvLoadingData.setVisibility(View.VISIBLE);
            animation.start();
            rllContent.setVisibility(View.GONE);
        } else {
            imvLoading.setVisibility(View.GONE);
            txvLoadingData.setVisibility(View.GONE);
            animation.stop();
            rllContent.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void showMessage(String message) {
        txvStatus.setText(message);
    }

    @Override
    public void afterLogin() {
        edtPwd.setEnabled(false);
        edtName.setEnabled(false);
        btnLogin.setEnabled(false);
        Intent intent = new Intent(this, ControlActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public String getName() {
        return edtName.getText().toString();
    }

    @Override
    public void setName(String name) {
        edtName.setText(name);
    }

    @Override
    public String getPassword() {
        return edtPwd.getText().toString();
    }

    @Override
    public void setPassword(String password) {
        edtPwd.setText(password);
    }

    @Override
    public void installApkFromUri(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void showGPSAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.MyMaterialTheme));

        // Setting Dialog Title
        alertDialog.setTitle("Настойте GPS");

        // Setting Dialog Message
        alertDialog.setMessage("Определение GPS-координат отключено. Хотите перейти в настройки?");

        // On pressing the Settings button.
        alertDialog.setPositiveButton("Настройки", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, REQUEST_SETTINGS);
            }
        });
        // On pressing the cancel button
        alertDialog.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SETTINGS) {
            presenter.onResume();
        }
    }

    private String getVersionName() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
