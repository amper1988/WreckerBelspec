package com.belspec.app.ui;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.belspec.app.R;
import com.belspec.app.interfaces.MyCallback;
import com.belspec.app.interfaces.NetworkDataUpdate;
import com.belspec.app.interfaces.ResponseListener;
import com.belspec.app.retrofit.Api;
import com.belspec.app.retrofit.RetrofitService;
import com.belspec.app.retrofit.model.test.request.TestRequestEnvelope;
import com.belspec.app.retrofit.model.test.response.TestResponseEnvelope;
import com.belspec.app.utils.Encode;
import com.belspec.app.utils.NetworkDataManager;
import com.belspec.app.utils.UserManager;
import com.belspec.app.utils.Utils;

import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ResponseListener, TextView.OnEditorActionListener {

    Button btnLogin;
    EditText edtName;
    EditText edtPwd;
    TextView txvStatus;
    ImageView imvLoading;
    RelativeLayout rllContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initComponents();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        initComponents();
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
        setLoading(false);
    }

    private void initViews() {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        edtName = (EditText) findViewById(R.id.edtName);
        edtPwd = (EditText) findViewById(R.id.edtPwd);
        txvStatus = (TextView) findViewById(R.id.response);
        imvLoading = (ImageView) findViewById(R.id.imvLoading);
        rllContent = (RelativeLayout) findViewById(R.id.rllContent);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                Utils.hideKeyboard(this);
                setLoading(true);
                RetrofitService retrofit = Api.createRetrofitService();
                MyCallback<TestResponseEnvelope> call = new MyCallback<>();
                call.addResponseListener(this);

                retrofit.executeTestOperation(Encode.getBasicAuthTemplate(edtName.getText().toString(), edtPwd.getText().toString()), new TestRequestEnvelope())
                        .enqueue(call);
                break;
        }
    }

    @Override
    public void AuthorizationOK(Response response) {
        UserManager user = UserManager.getInstanse();
        TestResponseEnvelope responseEnvelope = (TestResponseEnvelope) response.body();
        user.setUserData(edtName.getText().toString(), edtPwd.getText().toString(), responseEnvelope.getTestData().getFullName(), responseEnvelope.getTestData().getUserType(), true, responseEnvelope.getTestData().getOrganization());
        txvStatus.setText(response.code() + " " + response.message());
        Intent intent = new Intent(this, ControlActivity.class);
        startActivity(intent);
        this.finish();

    }

    @Override
    public void AuthorizationBad(Response response) {
        UserManager.getInstanse().logout();
        setLoading(false);
        txvStatus.setText(response.code() + " " + response.message());
    }

    @Override
    public void AuthorizationFail(Throwable t) {
        UserManager.getInstanse().logout();
        setLoading(false);
        txvStatus.setText(t.getLocalizedMessage());
    }

    private void setLoading(Boolean bool) {
        AnimationDrawable animation = (AnimationDrawable) imvLoading.getBackground();
        if (bool) {
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
}
