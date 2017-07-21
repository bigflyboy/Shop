package com.visionin.shop.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.visionin.shop.Beans.LoginBean;
import com.visionin.shop.R;
import com.visionin.shop.http.API_ENUM;
import com.visionin.shop.http.CallbackForRequest;
import com.visionin.shop.utils.Config;
import com.visionin.shop.utils.NetWorkUtils;
import com.visionin.shop.utils.SharedPreferencesUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.email)
    AutoCompleteTextView mEmailView;
    @BindView(R.id.password)
    EditText mPasswordView;
    @BindView(R.id.email_sign_in_button)
    Button mButton;
    @BindView(R.id.textview)
    TextView mTextview;

    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.email_sign_in_button)
    public void attemptLogin(View view) {


        mEmailView.setError(null);
        mPasswordView.setError(null);

        final String email = mEmailView.getText().toString().trim();
        final String password = mPasswordView.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            request(API_ENUM.LOGIN, new CallbackForRequest<LoginBean>() {
                @Override
                public void doSuccess(LoginBean bean) {
                    SharedPreferencesUtils.setParam(getApplicationContext(), Config.TOKEN, bean.getModel().getToken());

                    startActivity(new Intent(LoginActivity.this, BigScreenActivity.class));


                    finish();
                    String token = (String)SharedPreferencesUtils.getParam(getApplicationContext(), Config.TOKEN, "");
                    Toast.makeText(getApplicationContext(), token,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void doError(Object object) {

                }

                @Override
                public Map<String, String> getParams() {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("member_username", "xiaoxiaopolang");
                    map.put("member_password", "xiaoxiaopolang");
                    return map;
                }

                @Override
                public API_ENUM getApiEnum() {
                    return API_ENUM.LOGIN;
                }
            });
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
