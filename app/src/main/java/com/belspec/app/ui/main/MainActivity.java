package com.belspec.app.ui.main;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.belspec.app.utils.UserManager;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener, MainContract.View {

    @BindView(R.id.btnLogin) Button btnLogin;
    @BindView(R.id.edtName) EditText edtName;
    @BindView(R.id.edtPwd) EditText edtPwd;
    @BindView(R.id.response) TextView txvStatus;
    @BindView(R.id.imvLoading) ImageView imvLoading;
    @BindView(R.id.rllContent) RelativeLayout rllContent;
    MainContract.Presenter presenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);
        presenter = new MainPresenter(this);
        presenter.setApplicationContext(getApplicationContext());
        ButterKnife.bind(this);
        initComponents();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        presenter.startGPSTracker(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.stopGPSTracker();
    }


    private void initComponents() {
        btnLogin.setOnClickListener(this);
        edtName.setOnClickListener(this);
        edtPwd.setOnClickListener(this);
        edtName.setText(UserManager.getInstanse().getmLogin());
        edtPwd.setImeActionLabel("DONE", EditorInfo.IME_ACTION_DONE);
        edtName.setOnEditorActionListener(this);
        edtName.setImeActionLabel("NEXT", EditorInfo.IME_ACTION_NEXT);
        edtPwd.setOnEditorActionListener(this);
        edtPwd.setText(UserManager.getInstanse().getmPassword());
        imvLoading.setBackgroundResource(R.drawable.pb_loading);
        imvLoading.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                presenter.login(edtName.getText().toString(), edtPwd.getText().toString());
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
    public void setLoading(boolean loading) {
        AnimationDrawable animation = (AnimationDrawable) imvLoading.getBackground();
        if (loading) {
            imvLoading.setVisibility(View.VISIBLE);
            animation.start();
            rllContent.setVisibility(View.GONE);
        } else {
            imvLoading.setVisibility(View.GONE);
            animation.stop();
            rllContent.setVisibility(View.VISIBLE);
        }
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

}
