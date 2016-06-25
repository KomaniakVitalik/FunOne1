package com.www.funone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.widget.LoginButton;
import com.www.funone.R;

public class SocialLoginActivity extends BaseActivity implements View.OnClickListener {

    public static final String FONT_LUCIDA = "lucida-grande-bold.ttf";

    private LoginButton mFaceLogInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socila_login);
        setUpToolBar();
        setTitleTypeFace();
        initButtons();
    }

    /**
     * Initializes Toolbar. Enables title and back button
     */
    private void setUpToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResString(R.string.log_in));
    }

    /**
     * Initializes Button clicks
     */
    private void initButtons() {
        findViewById(R.id.google_plus_log_in_button).setOnClickListener(this);
        findViewById(R.id.rel_facebook_btn_wrapper).setOnClickListener(this);
        findViewById(R.id.rel_terms_of_services).setOnClickListener(this);
        findViewById(R.id.vk_login_btn).setOnClickListener(this);
        mFaceLogInButton = (LoginButton) findViewById(R.id.facebook_login_button);
    }

    /**
     * Applies LucidaGrande-Bold font to title TextViews
     */
    private void setTitleTypeFace() {
        TextView tvFun = (TextView) findViewById(R.id.tv_fun_one);
        TextView tvJoin = (TextView) findViewById(R.id.tv_join_have_fun);
        applyCustomFont(tvFun, FONT_LUCIDA);
        applyCustomFont(tvJoin, FONT_LUCIDA);
        spanWord(tvFun, 4, tvFun.getText().length(), getCompatColor(R.color.yellow));
    }

    /**
     * Spans part of word
     *
     * @param view  - TextView to spanWord
     * @param start - start of desired word
     * @param end   - end of desired word
     * @param color - color to apply
     */
    private void spanWord(TextView view, int start, int end, int color) {
        Spannable str = (Spannable) view.getText();
        str.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    /**
     * Starts Main Activity after successful log in
     */
    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rel_facebook_btn_wrapper:
                startMainActivity();
                break;
            case R.id.google_plus_log_in_button:
                startMainActivity();
                break;
            case R.id.vk_login_btn:
                startMainActivity();
                break;
            case R.id.rel_terms_of_services:
                Toast.makeText(SocialLoginActivity.this, getResString(R.string.terms_privacy_policy)
                        , Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
