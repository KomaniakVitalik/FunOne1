package com.www.funone.activities;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.www.funone.R;

public class SplashActivity extends BaseActivity {

    public static final int HOLD_TIME_MILLS = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        holdActivity();
    }

    private void holdActivity() {
        new CountDownTimer(HOLD_TIME_MILLS, HOLD_TIME_MILLS) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        }.start();
    }
}
