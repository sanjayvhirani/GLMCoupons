package com.infinium.glmcoupons;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.infinium.glmcoupons.client.MyLoopJPost;
import com.infinium.glmcoupons.client.NetworkUrls;
import com.infinium.glmcoupons.utils.Common;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    // UI references.
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        edtEmail = (EditText) findViewById(R.id.email);
        edtPassword = (EditText) findViewById(R.id.password);

        btnLogin = (Button) findViewById(R.id.email_sign_in_button);
        btnLogin.setOnClickListener(this);
        btnRegister = (Button) findViewById(R.id.register_button);
        btnRegister.setOnClickListener(this);
        findViewById(R.id.tv_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.openURL(LoginActivity.this, "http://infiniumglobal.in/");
            }
        });
    }

    public void attemptLogin() {
        edtEmail.setError(null);
        edtPassword.setError(null);
        // Store values at the time of the login attempt.
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        boolean cancel = false;
        View focusView = null;
        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Password is required");
            focusView = edtPassword;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Username is required");
            focusView = edtEmail;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            if (Common.isNetworkAvailable(this)) {
                MyLoopJPost myLoopJGet = new MyLoopJPost(this, "Please wait", onLoopJLoginCallComplete, NetworkUrls.loginUrl + "UserName=" + email + "&Password=" + password, new RequestParams());
            } else
                Common.showNETWORDDisabledAlert(this);
        }
    }

    MyLoopJPost.OnLoopJPostCallComplete onLoopJLoginCallComplete = new MyLoopJPost.OnLoopJPostCallComplete() {
        @Override
        public void response(String result) {
            try {
                JSONObject resultJson = new JSONObject(result);
                JSONObject dataObject = resultJson.optJSONObject("dataModel");
                if (dataObject != null) {
                    Common.setBooleanPrefrences(LoginActivity.this, getString(R.string.pref_Logedin), true, getString(R.string.app_name));
                    Common.setStringPrefrences(LoginActivity.this, getString(R.string.pref_userId), dataObject.getString("UserID"), getString(R.string.app_name));
                    Common.setStringPrefrences(LoginActivity.this, getString(R.string.pref_userName), dataObject.getString("UserName"), getString(R.string.app_name));
                    finish();
                } else {
                    Common.showAlertDialog(LoginActivity.this, "", "Login failed", true, null);
                }
            } catch (JSONException e) {
                Common.showAlertDialog(LoginActivity.this, "", "Login failed", true, null);
                e.printStackTrace();
            }

        }
    };

    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            attemptLogin();
        } else if (v == btnRegister) {
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }

}

