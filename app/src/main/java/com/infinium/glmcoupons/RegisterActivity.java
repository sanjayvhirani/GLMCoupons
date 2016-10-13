package com.infinium.glmcoupons;

import android.app.Activity;
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
public class RegisterActivity extends Activity implements View.OnClickListener {

    // UI references.

    private EditText edtUsername;
    private EditText edtEmail;
    private EditText edtMobile;
    private EditText edtPassword;
    private Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Set up the login form.
        edtMobile = (EditText) findViewById(R.id.mobile);
        edtUsername = (EditText) findViewById(R.id.username);
        edtEmail = (EditText) findViewById(R.id.email);
        edtPassword = (EditText) findViewById(R.id.password);

        btnRegister = (Button) findViewById(R.id.email_sign_in_button);
        btnRegister.setOnClickListener(this);
    }

    public void attemptRegister() {
        edtEmail.setError(null);
        edtPassword.setError(null);
        // Store values at the time of the login attempt.
        String mobile = edtMobile.getText().toString();
        String username = edtUsername.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;
        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password)) {
            edtPassword.setError("Password is required");
            focusView = edtPassword;
            cancel = true;
        }
        if (TextUtils.isEmpty(username)) {
            edtUsername.setError("Username is required");
            focusView = edtUsername;
            cancel = true;
        }
        if (TextUtils.isEmpty(mobile)) {
            edtMobile.setError("Mobile is required");
            focusView = edtMobile;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            if (Common.isNetworkAvailable(this)) {
                RequestParams requestParams = new RequestParams();

                MyLoopJPost myLoopJPost = new MyLoopJPost(this, "Please wait", onLoopJRegisterCallComplete, NetworkUrls.registerUrl + "userName?" + username +
                        "&userEmail=" + email + "&userMobile" + mobile + "&userPassword" + password, requestParams);
            } else
                Common.showNETWORDDisabledAlert(this);
        }
    }

    MyLoopJPost.OnLoopJPostCallComplete onLoopJRegisterCallComplete = new MyLoopJPost.OnLoopJPostCallComplete() {
        @Override
        public void response(String result) {
            try {
                JSONObject resultJson = new JSONObject(result);

                if (resultJson != null) {
                    if (resultJson.getString("result").equals("0")) {
                        Common.showAlertDialog(RegisterActivity.this, "", "Registration failed", true, null);
                    } else {
                        if (resultJson.getString("datamodal").equals("500")) {
                            Common.showAlertDialog(RegisterActivity.this, "", "Error: " + resultJson.getString("error"), true, null);
                        } else if (resultJson.getString("datamodal").equals("409")) {
                            Common.showAlertDialog(RegisterActivity.this, "", "Email or phone number already exists", true, null);
                            return;
                        } else {
//                            Common.setStringPrefrences(RegisterActivity.this, getString(R.string.pref_userName), edtUsername.getText().toString(), getString(R.string.app_name));
                            edtMobile.setText("");
                            edtUsername.setText("");
                            edtEmail.setText("");
                            edtPassword.setText("");
                            Common.showAlertDialog(RegisterActivity.this, "", "Registration successful", true, null);
                            finish();
                        }
                    }
                }
            } catch (JSONException e) {
                Common.showAlertDialog(RegisterActivity.this, "", "Registration failed", true, null);
                e.printStackTrace();
            }

        }
    };

    @Override
    public void onClick(View v) {
        if (v == btnRegister) {
            attemptRegister();
        }
    }

}

