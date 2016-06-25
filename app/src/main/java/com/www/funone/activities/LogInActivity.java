package com.www.funone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.www.funone.R;

public class LogInActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        findViewById(R.id.btn_go_social).setOnClickListener(this);
        findViewById(R.id.btn_go_main).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_go_social:
                startActivity(new Intent(this, SocialLoginActivity.class));
                break;
            case R.id.btn_go_main:
                startActivity(new Intent(this, MainActivity.class));
                this.finish();
                break;
        }
    }
}
