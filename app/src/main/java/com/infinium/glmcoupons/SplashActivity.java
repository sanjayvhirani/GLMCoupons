package com.infinium.glmcoupons;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.infinium.glmcoupons.activity.SelectCategoryActivity;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        executeSplash();
    }

    private void executeSplash() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent mIntent = new Intent(SplashActivity.this, SelectCategoryActivity.class);
                startActivity(mIntent);
                finish();
            }
        }, 3000);
    }
}
